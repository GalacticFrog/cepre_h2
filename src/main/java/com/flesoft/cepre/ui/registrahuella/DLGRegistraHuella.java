/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.registrahuella;

import com.digitalpersona.uareu.*;
import com.flesoft.cepre.UareU.CaptureThread;
import com.flesoft.cepre.dao.AulaEstudianteDao;
import com.flesoft.cepre.model.Aula;
import com.flesoft.cepre.model.AulaEstudiante;
import com.flesoft.cepre.model.Estudiante;
import com.flesoft.cepre.model.Huella;
import com.flesoft.cepre.model.Pabellon;
import com.flesoft.cepre.model.Sede;
import com.flesoft.cepre.ui.Main;
import com.flesoft.cepre.ui.util.MessageBox;
import com.flesoft.cepre.util.AppContext;
import com.flesoft.cepre.util.Config;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author edrev
 */
public class DLGRegistraHuella extends javax.swing.JDialog implements ActionListener {

    public class EnrollmentThread extends Thread implements Engine.EnrollmentCallback {

        public static final String ACT_PROMPT = "enrollment_prompt";
        public static final String ACT_CAPTURE = "enrollment_capture";
        public static final String ACT_FEATURES = "enrollment_features";
        public static final String ACT_DONE = "enrollment_done";
        public static final String ACT_CANCELED = "enrollment_canceled";
        public static final String ACT_SAVE = "save";

        public class EnrollmentEvent extends ActionEvent {

            private static final long serialVersionUID = 102;

            public Reader.CaptureResult capture_result;
            public Reader.Status reader_status;
            public UareUException exception;
            public Fmd enrollment_fmd;

            public EnrollmentEvent(Object source, String action, Fmd fmd, Reader.CaptureResult cr, Reader.Status st, UareUException ex) {
                super(source, ActionEvent.ACTION_PERFORMED, action);
                capture_result = cr;
                reader_status = st;
                exception = ex;
                enrollment_fmd = fmd;
            }
        }

        private final Reader m_reader;
        private CaptureThread m_capture;
        private ActionListener m_listener;
        private boolean m_bCancel;

        protected EnrollmentThread(Reader reader, ActionListener listener) {
            m_reader = reader;
            m_listener = listener;
        }

        @Override
        public Engine.PreEnrollmentFmd GetFmd(Fmd.Format format) {
            Engine.PreEnrollmentFmd prefmd = null;

            while (null == prefmd && !m_bCancel) {
                //start capture thread
                m_capture = new CaptureThread(m_reader, false, Fid.Format.ANSI_381_2004, Reader.ImageProcessing.IMG_PROC_DEFAULT);
                m_capture.start(null);

                //prompt for finger
                SendToListener(ACT_PROMPT, null, null, null, null);

                //wait till done
                m_capture.join(0);

                //check result
                CaptureThread.CaptureEvent evt = m_capture.getLastCaptureEvent();
                if (null != evt.capture_result) {
                    if (Reader.CaptureQuality.CANCELED == evt.capture_result.quality) {
                        //capture canceled, return null
                        break;
                    } else if (null != evt.capture_result.image && Reader.CaptureQuality.GOOD == evt.capture_result.quality) {
                        //Send image
                        SendToListener(ACT_CAPTURE, null, evt.capture_result, null, null);

                        //acquire engine
                        Engine engine = UareUGlobal.GetEngine();

                        try {
                            //extract features

                            Fmd fmd = engine.CreateFmd(evt.capture_result.image, Fmd.Format.DP_PRE_REG_FEATURES);

                            //return prefmd
                            prefmd = new Engine.PreEnrollmentFmd();
                            prefmd.fmd = fmd;
                            prefmd.view_index = 0;

                            //send success
                            SendToListener(ACT_FEATURES, null, null, null, null);
                        } catch (UareUException e) {
                            //send extraction error
                            SendToListener(ACT_FEATURES, null, null, null, e);
                        }
                    } else {
                        //send quality result
                        SendToListener(ACT_CAPTURE, null, evt.capture_result, evt.reader_status, evt.exception);
                    }
                } else {
                    //send capture error
                    SendToListener(ACT_CAPTURE, null, evt.capture_result, evt.reader_status, evt.exception);
                }
            }

            return prefmd;
        }

        public void cancel() {
            m_bCancel = true;
            if (null != m_capture) {
                m_capture.cancel();
            }
        }

        private void SendToListener(String action, Fmd fmd, Reader.CaptureResult cr, Reader.Status st, UareUException ex) {
            if (null == m_listener || null == action || action.equals("")) {
                return;
            }

            final EnrollmentEvent evt = new EnrollmentEvent(this, action, fmd, cr, st, ex);

            //invoke listener on EDT thread
            try {
                javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
                    public void run() {
                        m_listener.actionPerformed(evt);
                    }
                });
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            //acquire engine
            Engine engine = UareUGlobal.GetEngine();

            try {
                m_bCancel = false;
                while (!m_bCancel) {
                    //run enrollment
                    Fmd fmd = engine.CreateEnrollmentFmd(Fmd.Format.DP_REG_FEATURES, this);

                    //send result
                    if (null != fmd) {

                        SendToListener(ACT_DONE, fmd, null, null, null);
                    } else {
                        SendToListener(ACT_CANCELED, null, null, null, null);
                        break;
                    }
                }
            } catch (UareUException e) {
                JOptionPane.showMessageDialog(null, "Exception during creation of data and import");
                SendToListener(ACT_DONE, null, null, null, e);
            }
        }
    }
    private static final Log logger = LogFactory.getLog(DLGRegistraHuella.class);
    private Main parentFrame;
    private AulaEstudiante aulaEstudiante;
    public com.digitalpersona.uareu.Fmd enrollmentFMD;
    private EnrollmentThread m_enrollment;
    private Reader m_reader;
    private boolean m_bJustStarted;

    private ReaderCollection m_collection;

    private ArrayList<Fid> muestras = new ArrayList<>();
//
//    private DPFPCapture Lector = DPFPGlobal.getCaptureFactory().createCapture();
//    private DPFPEnrollment Reclutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();
//    private DPFPVerification Verificador = DPFPGlobal.getVerificationFactory().createVerification();
//    private DPFPTemplate template;
//    public static String TEMPLATE_PROPERTY = "template";
//    public DPFPFeatureSet featuresinscripcion;
//    public DPFPFeatureSet featuresverificacion;

    public DLGRegistraHuella(AulaEstudiante aulaEstudiante) {
        super(new java.awt.Frame(), true);
        this.aulaEstudiante = aulaEstudiante;
        try {
            this.m_collection = UareUGlobal.GetReaderCollection();
            m_collection.GetReaders();
        } catch (UareUException e1) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, "Error getting collection");
            return;
        }
        if (m_collection.size() == 0) {
            MessageBox.Warning("Lector no conectado.");
            return;
        }

        m_reader = m_collection.get(0);

        if (null == m_reader) {
            MessageBox.Warning("Lector no conectado...");
            dispose();
        }
        m_bJustStarted = true;
        m_enrollment = new EnrollmentThread(m_reader, this);

        initComponents();
        setLocationRelativeTo(null);

        mostrarDatos();
        getRootPane().setDefaultButton(btnRegistrar);
        //btnRegistrar.requestFocus();
    }
//77175326
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnRegistrar = new javax.swing.JButton();
        m_text = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblImagenFoto = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblDni = new javax.swing.JLabel();
        lblPabellon = new javax.swing.JLabel();
        lblAula = new javax.swing.JLabel();
        lblSede = new javax.swing.JLabel();
        lblImagenHuella = new javax.swing.JLabel();
        lblMuestras = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblCompleto = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblTurno = new javax.swing.JLabel();

        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        btnRegistrar.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/32/Checked-32.png"))); // NOI18N
        btnRegistrar.setText("Guardar huella");
        btnRegistrar.setEnabled(false);
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        m_text.setText("jLabel1");

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.SystemColor.activeCaptionBorder));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("FOTOGRAFIA");

        lblImagenFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/photo.jpg"))); // NOI18N
        lblImagenFoto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel9.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel9.setText("Turno:");

        jLabel8.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel8.setText("Aula:");

        jLabel6.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel6.setText("Pabellon:");

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel4.setText("DNI:");

        lblDni.setFont(new java.awt.Font("Segoe UI Light", 1, 30)); // NOI18N
        lblDni.setText(".");

        lblPabellon.setFont(new java.awt.Font("Segoe UI Light", 1, 20)); // NOI18N
        lblPabellon.setText(".");

        lblAula.setFont(new java.awt.Font("Segoe UI Light", 1, 36)); // NOI18N
        lblAula.setText(".");

        lblSede.setFont(new java.awt.Font("Segoe UI Light", 1, 36)); // NOI18N
        lblSede.setText(".");

        lblImagenHuella.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        lblMuestras.setFont(new java.awt.Font("Tahoma", 1, 150)); // NOI18N
        lblMuestras.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMuestras.setText("4");
        lblMuestras.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("MUESTRAS NECESARIAS");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("HUELLA DACTILAR");

        lblCompleto.setFont(new java.awt.Font("Segoe UI Light", 1, 40)); // NOI18N
        lblCompleto.setText(".");

        jLabel7.setFont(new java.awt.Font("Segoe UI Light", 1, 24)); // NOI18N
        jLabel7.setText("Nombres y apellidos:");

        jLabel10.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel10.setText("Sede:");

        lblTurno.setFont(new java.awt.Font("Segoe UI Light", 1, 36)); // NOI18N
        lblTurno.setText(".");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(89, 89, 89)
                                .addComponent(jLabel2))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(lblImagenFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(22, 22, 22)
                                        .addComponent(lblPabellon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(5, 5, 5))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(27, 27, 27)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblSede, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(lblAula, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                                                .addComponent(lblDni, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lblTurno, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(55, 55, 55))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(lblImagenHuella, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(32, 32, 32))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(lblCompleto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMuestras, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(lblImagenHuella, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(lblDni))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(lblPabellon))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblAula)
                                    .addComponent(jLabel8))
                                .addGap(21, 21, 21)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(lblTurno))))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel10)
                                    .addComponent(lblSede))
                                .addGap(35, 35, 35)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblMuestras, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addComponent(lblImagenFoto))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(m_text, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                .addGap(56, 56, 56)
                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(435, 435, 435))
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(m_text)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnRegistrar)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    @Override
    public void actionPerformed(ActionEvent e) {
        EnrollmentThread.EnrollmentEvent evt = (EnrollmentThread.EnrollmentEvent) e;
        if (e.getActionCommand().equals(EnrollmentThread.ACT_PROMPT)) {
            if (m_bJustStarted) {
                m_text.setText("Proceso de captura iniciado\n");
                m_text.setText("Coloque su dedo en el lector\n");
//                String muestras = String.valueOf(Reclutador.getFeaturesNeeded());
//                lblMuestras.setText(muestras);
            } else {
                m_text.setText(":: Coloque su dedo en el lector\n");
            }
            m_bJustStarted = false;
        } else if (e.getActionCommand().equals(EnrollmentThread.ACT_CAPTURE)) {

            if (null != evt.capture_result) {
                if (evt.capture_result.image != null) {
                    DibujarHuella(evt.capture_result.image);
                    muestras.add(evt.capture_result.image);
                    EstadoHuellas();
                    //m_imagePanel.showImage(evt.capture_result.image);
                }
            }
            if (null != evt.capture_result) {
                //MessageBox.BadQuality(evt.capture_result.quality);
            } else if (null != evt.exception) {
                MessageBox.DpError("Capture", evt.exception);
            } else if (null != evt.reader_status) {
                MessageBox.BadStatus(evt.reader_status);
            }
            m_bJustStarted = false;
        } else if (e.getActionCommand().equals(EnrollmentThread.ACT_FEATURES)) {
            if (null == evt.exception) {
                m_text.setText("   Huella capturada, guardar\n\n");
            } else {
                MessageBox.DpError("Feature extraction", evt.exception);
            }
            m_bJustStarted = false;
        } else if (e.getActionCommand().equals(EnrollmentThread.ACT_DONE)) {
            if (null == evt.exception) {
                String str = String.format("Muestra creada, tamano: %d\n\n\n Guarde la huella.", evt.enrollment_fmd.getData().length);
                enrollmentFMD = evt.enrollment_fmd;
                m_enrollment.cancel();
                this.btnRegistrar.setEnabled(true);
                m_text.setText(str);
                lblMuestras.setText("0");
            } else {
                MessageBox.DpError("Enrollment template creation", evt.exception);
            }
            m_bJustStarted = true;
        } else if (e.getActionCommand().equals(EnrollmentThread.ACT_CANCELED)) {
            //canceled, destroy dialog
            // m_dlgParent.setVisible(false);
        }

        //cancel enrollment if any exception or bad reader status
        if (null != evt.exception) {
            // m_dlgParent.setVisible(false);
            this.setVisible(false);
        } else if (null != evt.reader_status && Reader.ReaderStatus.READY != evt.reader_status.status && Reader.ReaderStatus.NEED_CALIBRATION != evt.reader_status.status) {
            //m_dlgParent.setVisible(false);
            this.setVisible(false);
        }

    }

    private void doModal() {
        //open reader
        try {
            m_reader.Open(Reader.Priority.COOPERATIVE);
        } catch (UareUException e) {
            MessageBox.DpError("Reader.Open()", e);
        }

        //start enrollment thread
        m_enrollment.start();

    }

    public static Fmd Run(AulaEstudiante aulaEstudiante) {
        //JDialog dlg = new JDialog((JDialog) null, "Enrollment", true);
        DLGRegistraHuella enrollment = new DLGRegistraHuella(aulaEstudiante);
        //enrollment.setAulaEstudiante(aulaEstudiante);
        enrollment.doModal();
        enrollment.setVisible(true);
        return enrollment.enrollmentFMD;
    }

    /**
     * inicializa el lector para la captura
     */
    private void stop() {
        //open reader
        try {
            //m_reader.
            m_reader.Close();
        } catch (UareUException e) {
            MessageBox.DpError("Reader.Close()", e);
        }

    }

    public void DibujarHuella(Fid image_h) {
        Fid.Fiv view = image_h.getViews()[0];
        BufferedImage image = new BufferedImage(view.getWidth(), view.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        image.getRaster().setDataElements(0, 0, view.getWidth(), view.getHeight(), view.getImageData());

        lblImagenHuella.setIcon(new ImageIcon(image.getScaledInstance(lblImagenHuella.getWidth(), lblImagenHuella.getHeight(), Image.SCALE_DEFAULT)));
        repaint();
    }

    public void DibujarHuella(String imagenHuella) {

        try {
            File pathToFile = new File(imagenHuella);
            Image image = ImageIO.read(pathToFile);
            lblImagenHuella.setIcon(new ImageIcon(
                    image.getScaledInstance(lblImagenHuella.getWidth(), lblImagenHuella.getHeight(), Image.SCALE_DEFAULT)));
            repaint();
        } catch (IOException ex) {
            logger.info("No se puede cargar la imagen de la huella");
            //ex.printStackTrace();
        }

    }

    public void EstadoHuellas() {
        //EnviarTexto("Muestra de Huellas Necesarias para Guardar Template " + Reclutador.getFeaturesNeeded());
        int nmuestras = 4 - muestras.size();
        String muestras_ = String.valueOf(nmuestras);

        lblMuestras.setText(muestras_);
//        btnRegistrar.setEnabled(Reclutador.getFeaturesNeeded() == 0);
//        logger.info("muestras: " + Reclutador.getFeaturesNeeded());

    }

    public void limpiarDatos() {
        lblDni.setText("");
        //lblCompleto.setText("");
        lblSede.setText("");
        lblPabellon.setText("");
        lblAula.setText("");
    }

    private void mostrarDatos() {
        AulaEstudiante _aulaEstudiante = getAulaEstudiante();
        Estudiante estudiante = _aulaEstudiante.getEstudiante();

        Aula aula = _aulaEstudiante.getAula();
        Pabellon pabellon = aula.getPabellon();
        Sede sede = aulaEstudiante.getAula().getPabellon().getSede();
        String turno = _aulaEstudiante.getTurno().equals("m") ? "Ma\u00F1ana" : "Tarde";

        lblDni.setText(estudiante.getDni());
        lblCompleto.setText(estudiante.getCompleto());
        lblTurno.setText(turno);
        lblPabellon.setText(pabellon.getDenominacion());
        lblAula.setText(aula.getDenominacion());
        lblSede.setText(sede.getNombreSede());
        //foto
        String dir_fotos = Config.DIR_FOTOS;
        ImageIcon iconLogo = new ImageIcon(dir_fotos + "/" + estudiante.getFotografia());
        lblImagenFoto.setIcon(iconLogo);

        String dir_huellas = Config.DIR_HUELLAS;
        String ruta = dir_huellas + "/estudiantes/" + _aulaEstudiante.getId() + "_" + _aulaEstudiante.getEstudiante().getDni() + ".gif";
        File f = new File(ruta);
        //String imagenHuella = (_aulaEstudiante.getHuella() == null) ? "img/SIN_HUELLA.jpg" : "huellas/estudiantes/" + getAulaEstudiante().getId() + "_" + getAulaEstudiante().getEstudiante().getDni() + ".gif";
        //DibujarHuella(imagenHuella);
        if (_aulaEstudiante.getHuella() != null) {
            if (f.exists() && !f.isDirectory()) {
//            ImageIcon huella = new ImageIcon(ruta);
//            lblImagenHuella.setIcon(huella);
                DibujarHuella(ruta);
            }
        }
    }

    public void guardarHuella() {
        //Obtiene los datos del template de la huella actual
        AulaEstudiante aulaEstudiante_ = getAulaEstudiante();
        byte[] data = this.enrollmentFMD.getData();
        ByteArrayInputStream datosHuella = new ByteArrayInputStream(data);
        Integer tamanioHuella = data.length;
        try {
            AulaEstudianteDao aeDao = AppContext.getInstance().getBean(AulaEstudianteDao.class);
//            Huella huella = new Huella();
//            huella.setEstudiante(aulaEstudiante_.getEstudiante());
            //dialog muestras

            aeDao.insertar(aulaEstudiante_, data, tamanioHuella);
            //String dir_huellas = Config.DIR_HUELLAS;
            String ruta = "/estudiantes/" + aulaEstudiante_.getId() + "_" + aulaEstudiante_.getEstudiante().getDni();
            DLGImagenHuella dlgMuestras = new DLGImagenHuella(ruta, muestras);
            dlgMuestras.setVisible(true);
            //TaskDialogs.inform(this, "Guardado correctamente", "Se registr\u00f3 la huella correctamente.");
            JOptionPane.showMessageDialog(this,
                    "Se registr\u00f3 la huella correctamente.",
                    "INFORMACION",
                    JOptionPane.INFORMATION_MESSAGE);
            //JOptionPane.showMessageDialog(this, "La huella se grabo correctamente", "AVISO", JOptionPane.INFORMATION_MESSAGE);
            limpiarDatos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e, "AVISO", JOptionPane.INFORMATION_MESSAGE);
            e.printStackTrace();
        }

    }

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog

        accionCerrarVentana();
    }//GEN-LAST:event_closeDialog

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

    }//GEN-LAST:event_formWindowOpened

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        // TODO add your handling code here:l
        guardarHuella();
        //guardarImagenHuella();
        accionCerrarVentana();
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void guardarImagenHuella(Huella huella) throws IOException {
        String dir_huellas = Config.DIR_HUELLAS;
        String imagenHuella = dir_huellas + "/" + huella.getId() + "_" + huella.getId() + "_" + huella.getDni();

//        DPFPSample muestra = muestras.get(2);
//        Image src = CrearImagenHuella(muestra);
//        BufferedImage img = ImageUtils.toBufferedImage(src);
//        ImageUtils.save(imagenHuella, img, "jpg");  // png okay, j2se 1.4+
//        ImageUtils.save(imagenHuella, img, "gif");
    }

    private void accionCerrarVentana() {
        //StopCaptureThread();
        stop();
        setVisible(false);
        dispose();

    }

    @Override
    protected JRootPane createRootPane() {
        JRootPane rootPane = new JRootPane();
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        Action actionListener = new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
                accionCerrarVentana();
            }
        };
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(stroke, "ESCAPE");
        rootPane.getActionMap().put("ESCAPE", actionListener);

        return rootPane;
    }
    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                RegistraHuella dialog = new RegistraHuella(new java.awt.Frame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblAula;
    private javax.swing.JLabel lblCompleto;
    private javax.swing.JLabel lblDni;
    private javax.swing.JLabel lblImagenFoto;
    private javax.swing.JLabel lblImagenHuella;
    private javax.swing.JLabel lblMuestras;
    private javax.swing.JLabel lblPabellon;
    private javax.swing.JLabel lblSede;
    private javax.swing.JLabel lblTurno;
    private javax.swing.JLabel m_text;
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
     * @return the docente
     */
    public AulaEstudiante getAulaEstudiante() {
        return aulaEstudiante;
    }

    /**
     * @param aulaEstudiante
     *
     */
    public void setaulaEstudiante(AulaEstudiante aulaEstudiante) {
        this.aulaEstudiante = aulaEstudiante;
    }
}
