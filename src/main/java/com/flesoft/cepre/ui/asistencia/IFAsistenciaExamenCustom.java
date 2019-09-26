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
import com.flesoft.cepre.dao.PabelloneDao;
import com.flesoft.cepre.model.AulaEstudiante;
import com.flesoft.cepre.model.AulaExamen;
import com.flesoft.cepre.model.Examen;
import com.flesoft.cepre.model.PabellonE;
import com.flesoft.cepre.ui.Main;
import com.flesoft.cepre.ui.util.DateUtils;
import com.flesoft.cepre.ui.util.MessageBox;
import com.flesoft.cepre.ui.util.ProgressGlassPane;
import com.flesoft.cepre.ui.util.Silk;
import com.flesoft.cepre.util.AppContext;
import com.flesoft.cepre.util.Config;
import com.revolsys.famfamfam.silk.SilkIconLoader;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import org.oxbow.swingbits.dialog.task.TaskDialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author edrev
 */
public class IFAsistenciaExamenCustom extends javax.swing.JInternalFrame implements ActionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(IFAsistenciaExamenCustom.class);

    private Main parentFrame;
    private CaptureThread m_capture;
    private ReaderCollection m_collection;
    private Reader m_reader;
    private Fmd[] m_fmds;
    private Fmd enrollmentFMD;

    public List<AulaEstudiante> m_listOfRecords = new ArrayList<>();
    public List<Fmd> m_fmdList = new ArrayList<>();
    public Fmd[] m_fmdArray = null;  // array de FMDs
    private final String m_strPrompt1 = "Verificacion iniciada\n    coloque el dedo indice derecho sobre el lector\n\n";
    private Examen examen;
    private SwingWorker<Void, Void> backgroundProcess;
    private SwingWorker<Void, Void> backgroundMatch;
    private ProgressGlassPane indicador;

    public IFAsistenciaExamenCustom(Examen examen) {
        initComponents();
        lblImagenHuella.add(jLabel3);
        setGlassPane(indicador = new ProgressGlassPane());
//        getRootPane().setDefaultButton(btnBuscar);
//        System.out.println("c:" + Config.CICLO);
//        System.out.println("p:" + Config.PABELLONE);
//        System.out.println("s:" + Config.SEDE);
        this.examen = examen;
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
        cargarDatosExamen();
        cargarHuellas();
        doModal();
        actualizaContador();

//        BasicInternalFrameUI bi = (BasicInternalFrameUI) this.getUI();
//        bi.setNorthPane(null);
        //Iniciar();
    }

    private void cargarDatosExamen() {
        int papellon_id = Config.PABELLONE;//pabellone examen
//        Date hoy = new Date();
//        ExamenDao examenDao = AppContext.getInstance().getBean(ExamenDao.class);
        PabelloneDao pdao = AppContext.getInstance().getBean(PabelloneDao.class);
        PabellonE pabellon = pdao.buscarPorId(papellon_id);
//        Examen examen2 = examenDao.buscarPorFecha(hoy);

//        if (examen2 == null) {
//            TaskDialogs.warning(null, "Importante!", "Ningun examen programado para hoy");
//            return;
//        }
        LOGGER.info("Examen: " + getExamen());
        //this.setExamen(examen2);
        // this.setTitle("Control de asistencia: " + getExamen().getNombre() + " " + getExamen().getFecha());
        lblTitulo.setText("PABELLON: " + pabellon.getDenominacion());
        lblExamen.setText(getExamen().getNombre() + " - " + DateUtils.localDate(getExamen().getFecha()));
    }

    private void cargarHuellas() {
        backgroundProcess = new SwingWorker<Void, Void>() {

            @Override
            protected Void doInBackground() throws SQLException, UareUException {
                indicador.activate("Cargando huellas");
                AulaEstudianteDao aeDao = AppContext.getInstance().getBean(AulaEstudianteDao.class);
                List<AulaEstudiante> huellas = aeDao.listarConHuella();
                m_listOfRecords = huellas;

                for (AulaEstudiante huella : huellas) {
                    if (huella.getHuella() != null) {
                        //Blob blob = huella.getHuella();
                        //int blobLength = (int) blob.length();
                        byte[] bBytes = huella.getHuella();
                        Fmd fmd = UareUGlobal.GetImporter().ImportFmd(bBytes, com.digitalpersona.uareu.Fmd.Format.DP_REG_FEATURES, com.digitalpersona.uareu.Fmd.Format.DP_REG_FEATURES);
                        m_fmdList.add(fmd);
                    }
                }
                //LOGGER.info("Se cargo: " + huellas.size() + " Huellas");
                lblStatus.setText("Se cargaron: " + huellas.size() + " Huellas");
                m_fmdArray = new Fmd[m_fmdList.size()];
                m_fmdList.toArray(m_fmdArray);
                return null;
            }

            @Override
            protected void done() {
                try {
                    indicador.deactivate();
                    get();
                } catch (InterruptedException | ExecutionException e) {
                    indicador.deactivate();
                    e.printStackTrace();
                    TaskDialogs.showException(e, "ERROR", "Se produjo un error obteniendo los registros.");
                } catch (Exception ex) {
                    indicador.deactivate();
                    ex.printStackTrace();
                    TaskDialogs.showException(ex, "ERROR", "Se produjo un error obteniendo los registros.");
                }
            }
        };
        backgroundProcess.execute();
    }

    private void buscando() {
        jPanel2.setBackground(new java.awt.Color(240, 240, 240));
        lblMsg.setText("Buscando...");
        lblMsg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/48/Magnify-45px.gif"))); // NOI18N
        //lblMsg.setIcon(null);
        lblStatus.setText("Buscando...");
        lblStatus.setIcon(new ImageIcon(getClass().getResource("/images/16/Magnify-1s-19px.gif"))); // NOI18N
    }

    private void encontrado() {
        lblMsg.setIcon(null);
        lblStatus.setText("Listo.");
        lblStatus.setIcon(new ImageIcon(getClass().getResource("/images/16/icons8-light-on-16.png"))); // NOI18N
        //lblMsg.setText(".");
    }

    private void ok(String msg) {
        lblMsg.setIcon(new ImageIcon(getClass().getResource("/images/24/checkmark.png")));
        lblMsg.setText(msg);
    }

    private void warning(String msg) {
        jPanel2.setBackground(new java.awt.Color(248, 215, 218));
        lblMsg.setIcon(new ImageIcon(getClass().getResource("/images/24/warning.png")));
        lblMsg.setText(msg);
    }

    private void error(String msg) {
        jPanel2.setBackground(new java.awt.Color(225, 3, 25));
        lblMsg.setIcon(new ImageIcon(getClass().getResource("/images/24/close.png")));
        lblMsg.setText(msg);
    }

//    private void hideJlabel(JLabel label) {
//        int delay = 3000; //milliseconds
//        ActionListener taskPerformer = (ActionEvent evt) -> {
//            label.setText(":");
//            label.setIcon(null);
//        };
//        Timer timer = new Timer(delay, taskPerformer);
//        timer.setRepeats(false);
//        timer.start();
//    }
    @Override
    public void actionPerformed(ActionEvent e) {

        backgroundMatch = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws SQLException, UareUException {
                buscando();
                if (e.getActionCommand().equals(CaptureThread.ACT_CAPTURE)) {
                    //process result
                    CaptureThread.CaptureEvent evt = (CaptureThread.CaptureEvent) e;
                    if (evt.capture_result.image != null) {
                        if (ProcessCaptureResult(evt)) {
                            //restart capture thread
                            WaitForCaptureThread();
                            StartCaptureThread();
                        } else {
                            //destroy dialog
                            //m_dlgParent.setVisible(false);
                        }
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    encontrado();
                    get();
                } catch (InterruptedException | ExecutionException e) {
                    encontrado();
                    e.printStackTrace();
                    TaskDialogs.showException(e, "ERROR", "Se produjo un error obteniendo los registros.");
                } catch (Exception ex) {
                    encontrado();
                    ex.printStackTrace();
                    TaskDialogs.showException(ex, "ERROR", "Se produjo un error obteniendo los registros.");
                }
            }
        };
        backgroundMatch.execute();

    }

    private boolean ProcessCaptureResult(CaptureThread.CaptureEvent evt) {
        boolean bCanceled = false;

//        if (this.m_listOfRecords.size() == 0) {
//            MessageBox.Warning("this.m_listOfRecords.size() == 0");
//            return !bCanceled;
//        }
        if (null != evt.capture_result) {
            if (null != evt.capture_result.image && Reader.CaptureQuality.GOOD == evt.capture_result.quality) {
                //extract features
                Engine engine = UareUGlobal.GetEngine();

                try {
                    //m_imagePanel.showImage(evt.capture_result.image);
                    DibujarHuella(evt.capture_result.image);
                    //muestra para comparar
                    Fmd fmd = engine.CreateFmd(evt.capture_result.image, Fmd.Format.DP_VER_FEATURES);
                    m_fmds[0] = fmd;
                    //Lets perform 1:n comparison
                    //Perform identification
                    int target_falsematch_rate = Engine.PROBABILITY_ONE / 100000; //target ratio es 0.00001
                    Engine.Candidate[] matches = engine.Identify(m_fmds[0], 0, m_fmdArray, target_falsematch_rate, 1);
                    if (matches.length == 1) {
                        AulaEstudiante encontrado = this.m_listOfRecords.get(matches[0].fmd_index);
                        //JOptionPane.showMessageDialog(null, "encontrado: " + encontrado.getId());
                        LOGGER.info("encontrado: " + encontrado.getId());
                        //comprobar que el estudiante
                        long estudiante_id = encontrado.getEstudiante().getId();
                        long examen_id = getExamen().getId();
                        EstudianteDao estudianteDao = AppContext.getInstance().getBean(EstudianteDao.class);
                        AulaExamen aulaExamen = estudianteDao.buscarAulaExamen(estudiante_id, examen_id);
                        if (aulaExamen == null) {
//                            JOptionPane.showMessageDialog(this,
//                                    "NO SE ASIGNO NINGUN AULA PARA EL ESTUDIANTE\n"
//                                    + encontrado.getEstudiante().getCompleto(), "AVISO!", JOptionPane.ERROR_MESSAGE);
                            TaskDialogs.warning(null, "AVISO!", "NO SE ASIGNO NINGUN AULA PARA EL ESTUDIANTE\n" + encontrado.getEstudiante().getCompleto());
                        } else {
                            lanzarDialogEstudiante(encontrado, evt.capture_result.image);
                            lblMsg.setText(".");
                        }

                    } else {
                        warning("No se reconoce la huella!");
                        // hideJlabel(lblMsg);
                    }

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

    private void StartCaptureThread() {
        m_capture = new CaptureThread(m_reader, false, Fid.Format.ANSI_381_2004, Reader.ImageProcessing.IMG_PROC_DEFAULT);
        m_capture.start(this);
    }

    private void StopCaptureThread() {
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
        lblContador.setText(contador);
    }

    public void EstadoHuellas() {
        //EnviarTexto("Muestra de Huellas Necesarias para Guardar la Huella. Template "+ Reclutador.getFeaturesNeeded());
    }

    public void EnviarTexto(String msg) {
        //txtArea.append(string + "\n");
        getParentFrame().enviarTexto(msg);

    }

    public void EnviarTextoDesconectado(String msg) {
        //txtArea.append(string + "\n");
        getParentFrame().enviarTextoDesconectado(msg);
        getParentFrame().getLblEstado().setIcon(SilkIconLoader.getIcon(Silk.CANCEL));
    }

    public void EnviarTextoConectado(String msg) {
        //txtArea.append(string + "\n");
        getParentFrame().enviarTextoConectado(msg);
        getParentFrame().getLblEstado().setIcon(SilkIconLoader.getIcon(Silk.LIGHTNING));
    }

    private void lanzarDialogEstudiante(AulaEstudiante huellai, Fid img) {
        DLGRegistraAsistenciaExamen dialog = new DLGRegistraAsistenciaExamen(getExamen());
        dialog.setHuellaEstudiante(huellai);
        dialog.setImageHuella(img);
        // dialog.setParentFrame(this);
        dialog.setVisible(true);
    }

    /**
     * public void buscarEstudiante() { try {
     * System.out.println("##Buscando##"); // String dni =
     * txtDNI.getText().trim();
     *
     * EstudianteDao estudianteDao =
     * AppContext.getInstance().getBean(EstudianteDao.class); Estudiante
     * estudiante = estudianteDao.buscarPorDni(dni); System.out.println("est:" +
     * estudiante); if (estudiante == null) {
     * JOptionPane.showMessageDialog(this, "No se encuentra ningun estudiante
     * con el dni: " + dni, "AVISO", JOptionPane.WARNING_MESSAGE); return; }
     * //comprobar que aun no se registro //boolean existe =
     * estudianteDao.existeHuellaCiclo(estudiante.getId());
     *
     * AulaEstudiante aulaEstudiante =
     * estudianteDao.buscarAula(estudiante.getId()); if (aulaEstudiante == null)
     * { JOptionPane.showMessageDialog(this, estudiante.getCompleto() + " No
     * esta asignado a ningun aula ", "Aviso", JOptionPane.WARNING_MESSAGE);
     * return; } boolean existe = aulaEstudiante.getHuella() != null; if
     * (existe) { int dialogResult = JOptionPane.showConfirmDialog(this, "Ya se
     * registro huella para el estudiante: " + estudiante.getCompleto() + "\n" +
     * "DESEA ACTUALIZAR LA HUELLA?", "Aviso", JOptionPane.YES_NO_OPTION); if
     * (dialogResult == JOptionPane.YES_OPTION) { Aula aula =
     * aulaEstudiante.getAula(); Pabellon pabellon = aula.getPabellon();
     *
     * String turno = aulaEstudiante.getTurno().equals("m") ? "Ma\u00F1ana" :
     * "Tarde"; Sede sede = estudiante.getSede(); LOGGER.info("estudiante: " +
     * estudiante); LOGGER.info("aulaEstudiante: " + aulaEstudiante);
     * LOGGER.info("sede: " + sede); LOGGER.info("aula: " + aula);
     * LOGGER.info("pabellon: " + pabellon); LOGGER.info("Turno: " + turno);
     * lanzarDialogManual(aulaEstudiante, m_reader); } else { return; }
     *
     * // JOptionPane.showMessageDialog(this, "Ya se registro huella para el
     * estudiante: " + estudiante.getCompleto(), "Aviso",
     * JOptionPane.WARNING_MESSAGE); // return; } else {
     * lanzarDialogManual(aulaEstudiante, m_reader); }
     *
     * //Si no encuentra alguna huella correspondiente al nombre lo indica con
     * un mensaje } catch (NullPointerException npe) { //
     * JOptionPane.showMessageDialog(this, npe, "AVISO",
     * JOptionPane.INFORMATION_MESSAGE); npe.printStackTrace(); new
     * ErrorDialog("Error", "Excepcion no controlada", npe);
     *
     * } catch (Exception e) { //JOptionPane.showMessageDialog(this, e, "AVISO",
     * JOptionPane.INFORMATION_MESSAGE); e.printStackTrace(); new
     * ErrorDialog("Error", "Excepcion no controlada", e);
     *
     * }
     * System.out.println(" fin ##Buscando##"); }
     *
     * private void lanzarDialogManual(AulaEstudiante aulaEstudiante, Reader
     * m_reader) {
     *
     * DLGRegistraManual.Run(aulaEstudiante, m_reader);
     *
     * // DLGRegistraHuella dialog = new DLGRegistraHuella(); //
     * dialog.setAulaEstudiante(aulaEstudiante); //
     * dialog.setParentFrame(getParentFrame()); // dialog.setVisible(true); }
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblImagenHuella = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblStatus = new javax.swing.JLabel();
        lblExamen = new javax.swing.JLabel();
        lblContador = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblMsg = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/24/checkmark.png"))); // NOI18N
        jLabel3.setText("jLabel3");

        setResizable(true);
        setTitle("REGISTRAR ASISTENCIA EXAMEN");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CONTROL DE ASISTENCIA EXAMEN", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        lblImagenHuella.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Huella");

        jLabel2.setBackground(new java.awt.Color(51, 51, 255));
        jLabel2.setText("Coloque el dedo indice en el lector de huellas.");

        jPanel2.setBackground(new java.awt.Color(212, 237, 218));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(195, 230, 203)));

        lblStatus.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/icons8-light-on-16.png"))); // NOI18N
        lblStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblStatus)
                .addGap(0, 19, Short.MAX_VALUE))
        );

        lblExamen.setBackground(java.awt.SystemColor.activeCaptionBorder);
        lblExamen.setFont(new java.awt.Font("Segoe UI Light", 1, 24)); // NOI18N
        lblExamen.setText("::");
        lblExamen.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblContador.setFont(new java.awt.Font("Segoe UI Semibold", 0, 75)); // NOI18N
        lblContador.setForeground(new java.awt.Color(51, 51, 51));
        lblContador.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContador.setText("999");
        lblContador.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 1, true));

        jLabel4.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("   CONTADOR   ");

        lblMsg.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        lblMsg.setText(".");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblImagenHuella, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblExamen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMsg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblContador, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblExamen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblMsg, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                        .addGap(35, 35, 35)
                        .addComponent(lblContador, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblImagenHuella, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        lblTitulo.setBackground(new java.awt.Color(102, 102, 255));
        lblTitulo.setFont(new java.awt.Font("Segoe UI Light", 1, 30)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("..");
        lblTitulo.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        StopCaptureThread();
        stop();
        LOGGER.info("###cerrando frame asistencia EXAMEN###");
    }//GEN-LAST:event_formInternalFrameClosed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        //Iniciar();
        //start();
    }//GEN-LAST:event_formInternalFrameOpened

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblContador;
    private javax.swing.JLabel lblExamen;
    private javax.swing.JLabel lblImagenHuella;
    private javax.swing.JLabel lblMsg;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the parentFrame
     */
    public Main getParentFrame() {
        return parentFrame;
    }

    /**
     * @param parentFrame the parentFrame to set
     */
    public void setParentFrame(Main parentFrame) {
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
}
