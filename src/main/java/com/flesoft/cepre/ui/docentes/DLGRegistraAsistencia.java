/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.docentes;

import com.flesoft.cepre.dao.AsistenciaDocenteDao;
import com.flesoft.cepre.dao.AsistenciaPersonalDao;
import com.flesoft.cepre.dao.CargoDao;
import com.flesoft.cepre.dao.PabellonDao;
import com.flesoft.cepre.model.AsistenciaDocente;
import com.flesoft.cepre.model.AsistenciaPersonal;
import com.flesoft.cepre.model.Cargo;
import com.flesoft.cepre.model.Examen;
import com.flesoft.cepre.model.Pabellon;
import com.flesoft.cepre.model.Personal;
import com.flesoft.cepre.model.Sede;
import com.flesoft.cepre.ui.Main;
import com.flesoft.cepre.util.AppContext;
import com.flesoft.cepre.util.Config;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
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
public class DLGRegistraAsistencia extends javax.swing.JDialog {

    private static final Log logger = LogFactory.getLog(DLGRegistraAsistencia.class);
    private Main parentFrame;
    private Personal personal;
    //private Examen examen;

//
//    private DPFPCapture Lector = DPFPGlobal.getCaptureFactory().createCapture();
//    private DPFPEnrollment Reclutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();
//    private DPFPVerification Verificador = DPFPGlobal.getVerificationFactory().createVerification();
//    private DPFPTemplate template;
//    public static String TEMPLATE_PROPERTY = "template";
//    public DPFPFeatureSet featuresinscripcion;
//    public DPFPFeatureSet featuresverificacion;
    public DLGRegistraAsistencia(Personal personal) {
        super(new java.awt.Frame(), true);
        this.personal = personal;
        // this.examen = examen;

        initComponents();
        setLocationRelativeTo(null);
        //cargarCombos();
        mostrarDatos();
        getRootPane().setDefaultButton(btnRegistrar);
        //btnRegistrar.requestFocus();
    }
//77175326
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnRegistrar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblImagenFoto = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblDni = new javax.swing.JLabel();
        lblApellidos = new javax.swing.JLabel();
        lblNombres = new javax.swing.JLabel();
        lblImagenHuella = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblHora = new javax.swing.JLabel();

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
        btnRegistrar.setText("Registrar asistencia");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.SystemColor.activeCaptionBorder));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("FOTOGRAFIA");

        lblImagenFoto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel8.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel8.setText("Nombres:");

        jLabel6.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel6.setText("Apellidos:");

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel4.setText("DNI:");

        lblDni.setFont(new java.awt.Font("Segoe UI Light", 1, 30)); // NOI18N
        lblDni.setText(".");

        lblApellidos.setFont(new java.awt.Font("Segoe UI Light", 1, 30)); // NOI18N
        lblApellidos.setText(".");

        lblNombres.setFont(new java.awt.Font("Segoe UI Light", 1, 36)); // NOI18N
        lblNombres.setText(".");

        lblImagenHuella.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("HUELLA DACTILAR");

        jLabel10.setFont(new java.awt.Font("Segoe UI Light", 0, 34)); // NOI18N
        jLabel10.setText("Hora:");

        jPanel3.setBackground(new java.awt.Color(184, 236, 166));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(195, 230, 203)));

        lblTitulo.setBackground(new java.awt.Color(0, 0, 204));
        lblTitulo.setFont(new java.awt.Font("Segoe UI Emoji", 0, 42)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText(":::::S::::::");
        lblTitulo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(184, 236, 166));

        lblHora.setBackground(new java.awt.Color(51, 51, 51));
        lblHora.setFont(new java.awt.Font("Segoe UI Light", 1, 36)); // NOI18N
        lblHora.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHora.setText("..");
        lblHora.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblHora, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblHora, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblImagenFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(22, 22, 22)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblApellidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(lblDni, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(20, 20, 20)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblNombres, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(71, 71, 71)
                                .addComponent(jLabel2)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(jLabel3))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblImagenHuella, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(lblDni))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(lblApellidos))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNombres)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblImagenFoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblImagenHuella, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addGap(337, 337, 337)
                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(btnRegistrar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    public void limpiarDatos() {
        lblDni.setText("");
        //lblCompleto.setText("");

        lblApellidos.setText("");
        lblNombres.setText("");
    }

    private void mostrarDatos() {
        Personal docente_ = getPersonal();

        lblDni.setText(docente_.getPersona().getDni());

        lblApellidos.setText(docente_.getPersona().getApellidos());
        lblNombres.setText(docente_.getPersona().getNombres());
        String dir_huellas = Config.DIR_HUELLAS;
        String imagenHuella = dir_huellas + "/personal/" + docente_.getPersona().getDni() + ".gif";
        System.out.println("huella url: " + imagenHuella);
        DibujarHuella(imagenHuella);

        AsistenciaDocenteDao aeDao = AppContext.getInstance().getBean(AsistenciaDocenteDao.class);
        Date fecha = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        lblHora.setText(dateFormat.format(fecha));
        AsistenciaDocente existe = aeDao.existeAsistencia(getPersonal().getId()); //foto

        if (existe != null) {
            lblTitulo.setText("SALIDA");
        } else {
            lblTitulo.setText("INGRESO");
        }

        // ImageIcon iconLogo = new ImageIcon("fotos/" + estudiante.getFotografia());
        //lblImagenFoto.setIcon(iconLogo);
    }

    public void guardarHuella() {
        AsistenciaDocenteDao aeDao = AppContext.getInstance().getBean(AsistenciaDocenteDao.class);

        Date _hoy = new Date();
        //AsistenciaPersonal existe = aeDao.existeAsistencia(getPersonal().getId(), getExamen().getId());
        AsistenciaDocente existe = aeDao.existeAsistencia(getPersonal().getId()); //foto
        int papellon_id = Config.PABELLON;//pabellone clases
        PabellonDao pdao = AppContext.getInstance().getBean(PabellonDao.class);
        Pabellon pabellon = pdao.buscarPorId(papellon_id);

        if (existe == null) {

            AsistenciaDocente ap = new AsistenciaDocente();
            ap.setFecha(_hoy);
            ap.setEntrada(_hoy);
            ap.setPabellon(pabellon);
            ap.setPersonal(getPersonal());

            aeDao.guardar(ap);

        } else if (existe != null && existe.getSalida() == null) {
            long diff = _hoy.getTime() - existe.getEntrada().getTime();
            long diffSeconds = diff / 1000 % 60;
            System.out.println("diffSeconds->" + diffSeconds);
            if (diffSeconds < 10) {
                JOptionPane.showMessageDialog(this, "LAS HORAS DE ENTRADA Y SALIDA TIENEN \nMENOS DE 10 SEGUNDOS DE DIFERENCIA!", "AVISO", JOptionPane.ERROR_MESSAGE);
                return;
            }

            existe.setSalida(_hoy);
            aeDao.guardar(existe);
        }
        JOptionPane.showMessageDialog(this, "Se registro la ASISTENCIA correctamente!", "AVISO", JOptionPane.INFORMATION_MESSAGE);
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

    private void accionCerrarVentana() {

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
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblApellidos;
    private javax.swing.JLabel lblDni;
    private javax.swing.JLabel lblHora;
    private javax.swing.JLabel lblImagenFoto;
    private javax.swing.JLabel lblImagenHuella;
    private javax.swing.JLabel lblNombres;
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
     * @return the personal
     */
    public Personal getPersonal() {
        return personal;
    }

    /**
     * @param docente the personal to set
     */
    public void setPersonal(Personal docente) {
        this.personal = docente;
    }

}
