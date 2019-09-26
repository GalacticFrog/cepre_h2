/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.asistencia;

import com.digitalpersona.uareu.Engine;
import com.digitalpersona.uareu.Fid;
import com.digitalpersona.uareu.Fmd;
import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.ReaderCollection;
import com.digitalpersona.uareu.UareUException;
import com.digitalpersona.uareu.UareUGlobal;
import com.flesoft.cepre.UareU.CaptureThread;
import com.flesoft.cepre.dao.AsistenciaExamenDao;
import com.flesoft.cepre.dao.AulaEstudianteDao;
import com.flesoft.cepre.dao.EstudianteDao;
import com.flesoft.cepre.dao.ExamenDao;
import com.flesoft.cepre.model.AsistenciaExamen;
import com.flesoft.cepre.model.Aula;
import com.flesoft.cepre.model.AulaEstudiante;
import com.flesoft.cepre.model.AulaExamen;
import com.flesoft.cepre.model.Estudiante;
import com.flesoft.cepre.model.Examen;
import com.flesoft.cepre.model.Pabellon;
import com.flesoft.cepre.model.Sede;
import com.flesoft.cepre.ui.Main;
import com.flesoft.cepre.ui.util.ErrorDialog;
import com.flesoft.cepre.ui.util.FileUtil;
import com.flesoft.cepre.ui.util.FiltroNumeros;
import com.flesoft.cepre.ui.util.ImageUtils;
import com.flesoft.cepre.ui.util.MessageBox;
import com.flesoft.cepre.ui.util.ProgressGlassPane;

import com.flesoft.cepre.util.AppContext;
import com.flesoft.cepre.util.Config;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import org.oxbow.swingbits.dialog.task.TaskDialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author edrev
 */
public class IFIRegistro extends javax.swing.JInternalFrame implements ActionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(IFIRegistro.class);
    private Examen examen;
    private IFIdentification parentFrame;
    private CaptureThread m_capture;
    private ReaderCollection m_collection;
    private Reader m_reader;
    private Fmd[] m_fmds;
    private Fid imageHuella;

    public List<Fmd> m_fmdList = new ArrayList<>();
    public Fmd[] m_fmdArray = null;  // array de FMDs

    private List<AulaEstudiante> users;
    private BufferedImage m_image;
    private AulaExamen aulaExamen_s;//para guardar asistencia
    private double high;
    private AulaEstudiante match;
    private ProgressGlassPane indicador;
    private SwingWorker<Void, Void> backgroundProcess;
    private SwingWorker<Void, Void> backgroundMatch;

    public IFIRegistro() {
        initComponents();
        getRootPane().setDefaultButton(btnBuscar);
        txtDNI.setDocument(new FiltroNumeros(txtDNI, 8));
        lblImagenHuella.add(jLabel3);
        //setGlassPane(indicador = new ProgressGlassPane());
        ExamenDao eDao = AppContext.getInstance().getBean(ExamenDao.class);
        this.examen = eDao.buscarPorId(1);
        //setGlassPane(indicador = new ProgressGlassPane());
//        getRootPane().setDefaultButton(btnBuscar);
//        System.out.println("c:" + Config.CICLO);
//        System.out.println("p:" + Config.PABELLONE);
//        System.out.println("s:" + Config.SEDE);

        try {
            this.m_collection = UareUGlobal.GetReaderCollection();
            m_collection.GetReaders();
        } catch (UareUException e1) {
            JOptionPane.showMessageDialog(null, "Error getting collection");
            return;
        }
        if (m_collection.isEmpty()) {
            MessageBox.Warning("Ningun Lector detectado");
            return;
        }
        m_reader = m_collection.get(0);
        if (null == m_reader) {
            MessageBox.Warning("No se pudo inicializar Lector");
        }
        m_fmds = new Fmd[2]; //dos FMDs para aplicar la comparacion

        doModal();

//        BasicInternalFrameUI bi = (BasicInternalFrameUI) this.getUI();
//        bi.setNorthPane(null);
        //Iniciar();
    }

//    @Override
//    public void run() {
//        System.out.println("Thread started");
//        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//
//        this.setVisible(true);
//    }
    @Override
    public void actionPerformed(ActionEvent e) {
        backgroundMatch = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws SQLException, UareUException {

                if (e.getActionCommand().equals(CaptureThread.ACT_CAPTURE)) {
                    //process result
                    CaptureThread.CaptureEvent evt = (CaptureThread.CaptureEvent) e;
                    if (evt.capture_result.image != null) {
                        try {
                            if (ProcessCaptureResult(evt)) {
                                //restart capture thread
                                WaitForCaptureThread();
                                StartCaptureThread();
                            } else {
                                //destroy dialog
                                //m_dlgParent.setVisible(false);
                            }
                        } catch (IOException ex) {
                            java.util.logging.Logger.getLogger(IFIRegistro.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                try {

                    get();
                } catch (InterruptedException | ExecutionException e) {

                    e.printStackTrace();
                    TaskDialogs.showException(e, "ERROR", "Se produjo un error obteniendo los registros.");
                } catch (Exception ex) {

                    ex.printStackTrace();
                    TaskDialogs.showException(ex, "ERROR", "Se produjo un error obteniendo los registros.");
                }
            }
        };
        backgroundMatch.execute();

    }

    private boolean ProcessCaptureResult(CaptureThread.CaptureEvent evt) throws IOException {
        boolean bCanceled = false;
        if (null != evt.capture_result) {
            if (null != evt.capture_result.image && Reader.CaptureQuality.GOOD == evt.capture_result.quality) {
                //extract features

                Engine engine = UareUGlobal.GetEngine();

                try {

                    Fmd fmd = engine.CreateFmd(evt.capture_result.image, Fmd.Format.DP_VER_FEATURES);
                    m_fmds[0] = fmd;
                    //m_imagePanel.showImage(evt.capture_result.image);
                    DibujarHuella(evt.capture_result.image);
                    btnRegistrar.setEnabled(true);
                    this.imageHuella = evt.capture_result.image;

                } catch (UareUException e) {
                    MessageBox.DpError("Engine.CreateFmd()", e);
                }

            } else {
                //the loop continues
                // m_text.append(m_strPrompt2);
            }
        } else if (Reader.CaptureQuality.CANCELED == evt.capture_result.quality) {
            //capture or streaming was canceled, just quit
            bCanceled = true;
        } else if (null != evt.exception) {
            //exception during capture
            MessageBox.DpError("Capture", evt.exception);
            bCanceled = true;
        } else if (null != evt.reader_status) {
            //reader failure
            MessageBox.BadStatus(evt.reader_status);
            bCanceled = true;
        } else {
            //bad quality
            MessageBox.BadQuality(evt.capture_result.quality);
        }

        return !bCanceled;
    }

    private void guardarAsistencia() {

        try {
            Date hoy = new Date();
            AulaExamen ae = getAulaExamen_s();
            AsistenciaExamen asistenciaExamen = new AsistenciaExamen();

            asistenciaExamen.setFecha(new Date());
            asistenciaExamen.setAulaExamen(ae);
            asistenciaExamen.setExamen(getExamen());

            AsistenciaExamenDao asistenciaExamenDao = AppContext.getInstance().getBean(AsistenciaExamenDao.class);
            boolean existe = asistenciaExamenDao.existeAsistencia(hoy, ae.getId(), getExamen().getId());
            if (existe) {
                //warning("LA ASISTENCIA DEL ESTUDIANTE YA FUE REGISTRADA.");
                JOptionPane.showMessageDialog(this, "La ASISTENCIA del estudiante : " + ae.getEstudiante().getCompleto() + " Ya fue registrada", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            asistenciaExamenDao.guardar(asistenciaExamen);
            // ok("La ASISTENCIA se guardo correctamente");
            //getParentFrame().actualizaContador();
            JOptionPane.showMessageDialog(this, "La ASISTENCIA se guardo correctamente", "AVISO", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            new ErrorDialog("Excepcion no controlada", "ERROR", e);
        }
    }

    private void guardarImagenHuella() {

        Fid image_h = getImageHuella();
        Fid.Fiv view = image_h.getViews()[0];
        BufferedImage img = new BufferedImage(view.getWidth(), view.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        img.getRaster().setDataElements(0, 0, view.getWidth(), view.getHeight(), view.getImageData());
        //ImageUtils.save(this.ruta, img, "jpg");  // png okay, j2se 1.4+
        String dir_huellas = Config.DIR_HUELLAS;

        String _ruta = dir_huellas + "/estudiantes/examen/" + getAulaExamen_s().getEstudiante().getDni();
        String _ruta2 = "huellas/estudiantes/examen/" + getAulaExamen_s().getEstudiante().getDni();
        boolean isdir = FileUtil.isDirRewritable(dir_huellas);//el path es un directoro y es escribible?
        if (isdir) {
            try {

                ImageUtils.save(_ruta, img, "gif");

            } catch (Exception ex1) {

            }
        } else {
            try {

                ImageUtils.save(_ruta2, img, "gif");

            } catch (Exception ex2) {

            }
        }

        // accionCerrarVentana();
    }

    private void StartCaptureThread() {
        m_capture = new CaptureThread(m_reader, false, Fid.Format.ANSI_381_2004, Reader.ImageProcessing.IMG_PROC_DEFAULT);
        m_capture.start(this);
    }

    public void StopCaptureThread() {
        if (null != m_capture) {
            m_capture.cancel();
        }
    }

    private void WaitForCaptureThread() {
        if (null != m_capture) {
            m_capture.join(1000);
        }
    }

    private void stop() {
        //open reader
        try {
            //m_reader.
            m_reader.Close();
        } catch (UareUException e) {
            MessageBox.DpError("Reader.Close()", e);
        }

    }

    private void doModal() {
        //open reader
        try {
            m_reader.Open(Reader.Priority.COOPERATIVE);
            System.out.println("Reader status: " + m_reader.GetStatus());
        } catch (UareUException e) {
            MessageBox.DpError("Reader.Open()", e);
        }

        //start capture thread
        StartCaptureThread();

        //put initial prompt on the screen
//        m_text.append(m_strPrompt1);
//
//        //bring up modal dialog
//        m_dlgParent = dlgParent;
//        m_dlgParent.setContentPane(this);
//        m_dlgParent.pack();
//        m_dlgParent.setLocationRelativeTo(null);
//        m_dlgParent.toFront();
//        m_dlgParent.setVisible(true);
//        m_dlgParent.dispose();
        //cancel capture
//        StopCaptureThread();
//
//        //wait for capture thread to finish
//        WaitForCaptureThread();
//
//        //close reader
//        try {
//            m_reader.Close();
//        } catch (UareUException e) {
//            MessageBox.DpError("Reader.Close()", e);
//        }
    }

    public void DibujarHuella(Fid image_h) {
        Fid.Fiv view = image_h.getViews()[0];
        BufferedImage image = new BufferedImage(view.getWidth(), view.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        image.getRaster().setDataElements(0, 0, view.getWidth(), view.getHeight(), view.getImageData());

        lblImagenHuella.setIcon(new ImageIcon(image.getScaledInstance(lblImagenHuella.getWidth(), lblImagenHuella.getHeight(), Image.SCALE_DEFAULT)));
        repaint();
    }

    public void actualizaContador() {
        long examen_id = getExamen().getId();
        AsistenciaExamenDao asistenciaExamenDao = AppContext.getInstance().getBean(AsistenciaExamenDao.class);
        long total_ = asistenciaExamenDao.registrosPorExamen(examen_id);
        String contador = String.valueOf(total_);

    }

    public void EstadoHuellas() {
        //EnviarTexto("Muestra de Huellas Necesarias para Guardar la Huella. Template "+ Reclutador.getFeaturesNeeded());
    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> {
            IFIRegistro v = new IFIRegistro();
            v.setVisible(true);
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtDNI = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        lblImagenHuella = new javax.swing.JLabel();
        lblDatos = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/24/checkmark.png"))); // NOI18N
        jLabel3.setText("jLabel3");

        setTitle("REGISTRAR ASISTENCIA EXAMEN");
        setMaximumSize(new java.awt.Dimension(514, 814));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                ifclosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                ifclosing(evt);
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "BUSCAR ALUMNO", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 1, 48)); // NOI18N
        jLabel4.setText("DNI:");

        txtDNI.setFont(new java.awt.Font("Segoe UI Light", 1, 45)); // NOI18N
        txtDNI.setText("74301550");
        txtDNI.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDNIFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDNIFocusLost(evt);
            }
        });

        btnBuscar.setFont(new java.awt.Font("Segoe UI Emoji", 0, 24)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/32/Search-32.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDNI)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscar)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDNI, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        lblImagenHuella.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        lblDatos.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblDatos.setText("datos..");

        btnRegistrar.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/32/Checked-32.png"))); // NOI18N
        btnRegistrar.setText("Aceptar");
        btnRegistrar.setEnabled(false);
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        jLabel1.setText("Nombres:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblImagenHuella, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(259, Short.MAX_VALUE)
                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(193, 193, 193))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(lblImagenHuella, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDatos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        try {
            if (getAulaExamen_s() == null) {
                JOptionPane.showMessageDialog(this, "Ingrese los datos del estudiante", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            guardarAsistencia();
            guardarImagenHuella();
            setAulaExamen_s(null);
            lblDatos.setText("");
            //accionCerrarVentana();
        } catch (Exception ex) {
            //Logger.getLogger(DLGRegistraAsistenciaExamen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void txtDNIFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDNIFocusGained
        // TODO add your handling code here:

        txtDNI.selectAll();
    }//GEN-LAST:event_txtDNIFocusGained

    private void txtDNIFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDNIFocusLost
        // TODO add your handling code here:

    }//GEN-LAST:event_txtDNIFocusLost

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscarEstudiante();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        StopCaptureThread();
        stop();
        LOGGER.info("###cerrando frame asistencia EXAMEN###");
    }//GEN-LAST:event_formWindowClosed

    private void ifclosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_ifclosed
        StopCaptureThread();
        stop();
        LOGGER.info("###cerrando frame asistencia EXAMEN###");
    }//GEN-LAST:event_ifclosed

    private void ifclosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_ifclosing
        StopCaptureThread();
        stop();
        LOGGER.info("###cerrando frame asistencia EXAMEN###");        // TODO add your handling code here:
    }//GEN-LAST:event_ifclosing

    public void buscarEstudiante() {
        try {
            System.out.println("##Buscando##");
            String dni = txtDNI.getText().trim();

            EstudianteDao estudianteDao = AppContext.getInstance().getBean(EstudianteDao.class);
            Estudiante estudiante = estudianteDao.buscarPorDni(dni);
            System.out.println("est:" + estudiante);

            if (estudiante == null) {
                JOptionPane.showMessageDialog(this, "No se encuentra ningun estudiante con el dni: " + dni, "AVISO", JOptionPane.WARNING_MESSAGE);
                return;
            }
            lblDatos.setText(estudiante.getCompleto());
            AulaExamen aulaExamen = estudianteDao.buscarAulaExamen(estudiante.getId(), getExamen().getId());
            if (aulaExamen == null) {
                JOptionPane.showMessageDialog(this, "NO SE ASIGNO NINGUN AULA PARA EL ESTUDIANTE", "AVISO!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            setAulaExamen_s(aulaExamen);
            //comprobar que aun no se registro
            //boolean existe = estudianteDao.existeHuellaCiclo(estudiante.getId());

            AulaEstudiante aulaEstudiante = estudianteDao.buscarAula(estudiante.getId());
            if (aulaEstudiante == null) {
                JOptionPane.showMessageDialog(this, estudiante.getCompleto() + " No esta asignado a ningun aula ", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean existe = !aulaEstudiante.getJsonTemplate().isEmpty();
            if (existe) {
                int dialogResult = JOptionPane.showConfirmDialog(this,
                        "Ya se registro huella para el estudiante: " + estudiante.getCompleto() + "\n"
                        + "DESEA ACTUALIZAR LA HUELLA?",
                        "Aviso",
                        JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    //lanzarDialogEstudiante(aulaEstudiante);
                } else {
                    return;
                }

//                JOptionPane.showMessageDialog(this, "Ya se registro huella para el estudiante: " + estudiante.getCompleto(), "Aviso", JOptionPane.WARNING_MESSAGE);
//                return;
            } else {
                //lanzarDialogEstudiante(aulaEstudiante);
            }

            //Si no encuentra alguna huella correspondiente al nombre lo indica con un mensaje
        } catch (NullPointerException npe) {
//            JOptionPane.showMessageDialog(this, npe, "AVISO", JOptionPane.INFORMATION_MESSAGE);
            npe.printStackTrace();
            new ErrorDialog("Error", "Excepcion no controlada", npe);

        } catch (Exception e) {
            //JOptionPane.showMessageDialog(this, e, "AVISO", JOptionPane.INFORMATION_MESSAGE);
            e.printStackTrace();
            new ErrorDialog("Error", "Excepcion no controlada", e);

        }
        System.out.println(" fin ##Buscando##");
    }

    private void accionCerrarVentana() {
        setVisible(false);
        dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblDatos;
    private javax.swing.JLabel lblImagenHuella;
    private javax.swing.JTextField txtDNI;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the parentFrame
     */
    public IFIdentification getParentFrame() {
        return parentFrame;
    }

    /**
     * @param parentFrame the parentFrame to set
     */
    public void setParentFrame(IFIdentification parentFrame) {
        this.parentFrame = parentFrame;
    }

    /**
     * @return the examen
     */
    public Examen getExamen() {
        return examen;
    }

    /**
     * @param examen the examen to set
     */
    public void setExamen(Examen examen) {
        this.examen = examen;
    }

    public AulaExamen getAulaExamen_s() {
        return aulaExamen_s;
    }

    /**
     * @param aulaExamen_s the aulaExamen_s to set
     */
    public void setAulaExamen_s(AulaExamen aulaExamen_s) {
        this.aulaExamen_s = aulaExamen_s;
    }

    /**
     * @return the imageHuella
     */
    public Fid getImageHuella() {
        return imageHuella;
    }

    /**
     * @param imageHuella the imageHuella to set
     */
    public void setImageHuella(Fid imageHuella) {
        this.imageHuella = imageHuella;
    }
}
