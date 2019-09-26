/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.docentes;

import com.flesoft.cepre.ui.personal.*;
import com.digitalpersona.uareu.Engine;
import com.digitalpersona.uareu.Fid;
import com.digitalpersona.uareu.Fmd;
import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.ReaderCollection;
import com.digitalpersona.uareu.UareUException;
import com.digitalpersona.uareu.UareUGlobal;
import com.flesoft.cepre.UareU.CaptureThread;
import com.flesoft.cepre.dao.DocenteDao;
import com.flesoft.cepre.dao.EstudianteDao;
import com.flesoft.cepre.dao.ExamenDao;
import com.flesoft.cepre.dao.HuellaDao;
import com.flesoft.cepre.dao.PabelloneDao;
import com.flesoft.cepre.model.AulaExamen;
import com.flesoft.cepre.model.Docente;
import com.flesoft.cepre.model.Examen;
import com.flesoft.cepre.model.Huella;
import com.flesoft.cepre.model.PabellonE;
import com.flesoft.cepre.ui.Main;
import com.flesoft.cepre.ui.util.MessageBox;
import com.flesoft.cepre.util.AppContext;
import com.flesoft.cepre.util.Config;
import com.flesoft.cepre.ui.util.Silk;
import com.revolsys.famfamfam.silk.SilkIconLoader;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
import org.oxbow.swingbits.dialog.task.TaskDialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author edrev
 */
public class IFAsistenciaDocente_BACK extends javax.swing.JInternalFrame implements ActionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(IFAsistenciaDocente_BACK.class);

    private Main parentFrame;
    private CaptureThread m_capture;
    private ReaderCollection m_collection;
    private Reader m_reader;
    private Fmd[] m_fmds;

    public List<Docente> m_listOfRecords = new ArrayList<>();
    public List<Fmd> m_fmdList = new ArrayList<>();
    public Fmd[] m_fmdArray = null;  // array de FMDs
    private final String m_strPrompt1 = "Verificacion iniciada\n    coloque el dedo indice derecho sobre el lector\n\n";
    // private Examen examen;

    public IFAsistenciaDocente_BACK() {
        initComponents();
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
        cargarHuellas();
        doModal();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
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
                        Docente encontrado = this.m_listOfRecords.get(matches[0].fmd_index);
                        //JOptionPane.showMessageDialog(null, "encontrado: " + encontrado.getId());
                        LOGGER.info("encontrado: " + encontrado.getId());
                        //comprobar que el estudiante

                        lanzarDialogDocente(encontrado, evt.capture_result.image);

                    } else {
                        JOptionPane.showMessageDialog(this, "No encontrado !!!", "AVISO", JOptionPane.ERROR_MESSAGE);
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

    private void cargarHuellas() {
        try {

            DocenteDao docenteDao = AppContext.getInstance().getBean(DocenteDao.class);

            List<Docente> huellas = docenteDao.listar();
            this.m_listOfRecords = huellas;

            for (Docente docente : huellas) {
                Blob blob = docente.getHuella();
                if (blob != null) {
                    int blobLength = (int) blob.length();
                    byte[] bBytes = blob.getBytes(1, blobLength);
                    Fmd fmd = UareUGlobal.GetImporter().ImportFmd(bBytes, com.digitalpersona.uareu.Fmd.Format.DP_REG_FEATURES, com.digitalpersona.uareu.Fmd.Format.DP_REG_FEATURES);
                    this.m_fmdList.add(fmd);
                }
            }
            LOGGER.info("Se cargo: " + m_fmdList.size() + " Huellas");
            m_fmdArray = new Fmd[this.m_fmdList.size()];
            this.m_fmdList.toArray(m_fmdArray);
        } catch (SQLException ex) {
            ex.printStackTrace();
            MessageBox.DpError("Failed to load FMDs from database.  Please check connection string in code.", null);
            return;
        } catch (UareUException e1) {

            JOptionPane.showMessageDialog(null, "Error importing fmd data.");
            return;
        } catch (HibernateException ex) {
            Throwable cause = ex.getCause();
            if (cause instanceof SQLException) {
                System.out.println(cause.getMessage());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error importing fmd data.");
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

    private void lanzarDialogDocente(Docente huellai, Fid img) {
        DLGRegistraAsistencia_BACK dialog = new DLGRegistraAsistencia_BACK(huellai);

        dialog.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblImagenHuella = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();

        setTitle("REGISTRAR ASISTENCIA");
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
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CONTROL DE ASISTENCIA DOCENTES", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        lblImagenHuella.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        lblStatus.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lblStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblEstado.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        jLabel1.setText("Huella");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImagenHuella, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(68, 68, 68)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 566, Short.MAX_VALUE)
                .addComponent(lblEstado, javax.swing.GroupLayout.DEFAULT_SIZE, 4, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(202, 202, 202)
                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 567, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblImagenHuella, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(32, 32, 32)
                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblImagenHuella;
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

}
