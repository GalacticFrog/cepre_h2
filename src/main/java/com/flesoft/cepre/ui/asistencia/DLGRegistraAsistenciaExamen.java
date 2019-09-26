/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.asistencia;

import com.digitalpersona.uareu.Fid;
import com.flesoft.cepre.dao.AsistenciaExamenDao;
import com.flesoft.cepre.dao.AulaEstudianteDao;
import com.flesoft.cepre.dao.EstudianteDao;
import com.flesoft.cepre.model.AsistenciaExamen;
import com.flesoft.cepre.model.AulaE;
import com.flesoft.cepre.model.AulaEstudiante;
import com.flesoft.cepre.model.AulaExamen;
import com.flesoft.cepre.model.Estudiante;
import com.flesoft.cepre.model.Examen;
import com.flesoft.cepre.model.PabellonE;
import com.flesoft.cepre.ui.util.ErrorDialog;
import com.flesoft.cepre.ui.util.FileUtil;
import com.flesoft.cepre.ui.util.ImageUtils;
import com.flesoft.cepre.util.AppContext;
import com.flesoft.cepre.util.Config;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class DLGRegistraAsistenciaExamen extends javax.swing.JDialog {

    private static final Log logger = LogFactory.getLog(DLGRegistraAsistenciaExamen.class);

    private AulaEstudiante HuellaEstudiante;
    private Fid imageHuella;
    private Examen examen;
    private AulaExamen aulaExamen_s;//para guardar asistencia
    private IFAsistenciaExamen parentFrame;

    public DLGRegistraAsistenciaExamen(Examen examen) {
        super(new java.awt.Frame(), true);
        this.examen = examen;
        System.out.println("examen:" + examen);
        initComponents();
        this.setTitle("Control de asistencia: " + examen.getNombre() + " " + examen.getFecha());
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(btnRegistrar);
        lblMensaje.setBackground(new java.awt.Color(255, 255, 255));
        btnRegistrar.requestFocus();

    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblCompleto = new javax.swing.JLabel();
        lblDni = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblPabellon = new javax.swing.JLabel();
        lblAula = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblImagenFoto = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblImagenHuella = new javax.swing.JLabel();
        lblMensaje = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblMsg = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JButton();

        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.SystemColor.activeCaptionBorder));

        jLabel7.setFont(new java.awt.Font("Segoe UI Light", 1, 24)); // NOI18N
        jLabel7.setText("Nombres:");

        lblCompleto.setFont(new java.awt.Font("Segoe UI Light", 1, 40)); // NOI18N
        lblCompleto.setText(".");

        lblDni.setFont(new java.awt.Font("Segoe UI Light", 1, 36)); // NOI18N
        lblDni.setText(".");

        jLabel6.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel6.setText("Pabellon:");

        lblPabellon.setFont(new java.awt.Font("Segoe UI Light", 1, 30)); // NOI18N
        lblPabellon.setText(".");

        lblAula.setFont(new java.awt.Font("Segoe UI Light", 1, 48)); // NOI18N
        lblAula.setText(".");

        jLabel8.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel8.setText("Aula:");

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel4.setText("DNI:");

        lblImagenFoto.setBackground(new java.awt.Color(255, 255, 255));
        lblImagenFoto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("FOTOGRAFIA");

        lblImagenHuella.setBackground(new java.awt.Color(255, 255, 255));
        lblImagenHuella.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        lblMensaje.setFont(new java.awt.Font("Segoe UI Light", 1, 26)); // NOI18N
        lblMensaje.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMensaje.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("HUELLA DACTILAR");

        jPanel1.setBackground(new java.awt.Color(212, 237, 218));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(195, 230, 203)));

        lblMsg.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        lblMsg.setText(":::::::::::");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMsg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMsg)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jLabel2))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblImagenFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblPabellon, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(lblDni, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6))
                            .addComponent(lblAula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblCompleto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(63, 63, 63))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblImagenHuella, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblImagenFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblDni)
                                    .addComponent(jLabel4))
                                .addGap(27, 27, 27)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblPabellon)
                                .addGap(48, 48, 48)
                                .addComponent(jLabel8)
                                .addGap(3, 3, 3)
                                .addComponent(lblAula)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(lblImagenHuella, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        btnRegistrar.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/32/Checked-32.png"))); // NOI18N
        btnRegistrar.setText("Aceptar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addGap(324, 324, 324)
                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public Image CrearImagenHuella() {
        //  return DPFPGlobal.getSampleConversionFactory().createImage(sample);
        return null;
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

    public void limpiarDatos() {
        lblDni.setText("");
        lblCompleto.setText("");
        // lblTurno.setText("");
        lblPabellon.setText("");
        lblAula.setText("");
    }

    private void mostrarDatos() {

        long estudiante_id = getHuellaEstudiante().getEstudiante().getId();
        long examen_id = getExamen().getId();
        try {
            EstudianteDao estudianteDao = AppContext.getInstance().getBean(EstudianteDao.class);
            AulaEstudianteDao aeDao = AppContext.getInstance().getBean(AulaEstudianteDao.class);
            AulaEstudiante ae = aeDao.buscarPorEstudianteId(estudiante_id);
            AulaExamen aulaExamen = estudianteDao.buscarAulaExamen(estudiante_id, examen_id);
            if (aulaExamen == null) {
                JOptionPane.showMessageDialog(this, "NO SE ASIGNO NINGUN AULA PARA EL ESTUDIANTE", "AVISO!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Estudiante estudiante = aulaExamen.getEstudiante();
            AulaE aulae = aulaExamen.getAulae();
            PabellonE pabellone = aulae.getPabellone();

            //String turno = aulaExamen.getTurno().equals("m") ? "Ma\u00F1ana" : "Tarde";
            lblDni.setText(estudiante.getDni());
            lblCompleto.setText(estudiante.getCompleto());
            //lblTurno.setText(turno);
            lblPabellon.setText(pabellone.getDenominacion());
            lblAula.setText(aulae.getDenominacion());
            //foto
            String dir_fotos = Config.DIR_FOTOS;
            lblImagenFoto.setIcon(new ImageIcon(dir_fotos + "/" + estudiante.getFotografia()));
            setAulaExamen_s(aulaExamen);
            //huella
            AulaEstudiante huellaImg = getHuellaEstudiante();
            //String imagenHuella = "huellas/" + huellaImg.getId() + "_1_" + huellaImg.getEstudiante().getId() + "_" + huellaImg.getEstudiante().getDni() + ".jpg";
            String dir_huellas = Config.DIR_HUELLAS;
            String imagenHuella = dir_huellas + "/estudiantes/" + ae.getId() + "_" + huellaImg.getEstudiante().getDni() + ".gif";
            System.out.println("huella url: " + imagenHuella);
            DibujarHuella(imagenHuella);
            int _pabellone = Config.PABELLONE;
            System.out.println(pabellone.getId() + " == " + _pabellone);
            if (pabellone.getId() != _pabellone) {
                //No pertenece al pabellon
                error("EL ESTUDIANTE NO PERTENECE AL PABELLON.");
                lblMensaje.setBackground(new java.awt.Color(225, 3, 25));
                lblMensaje.setText("<html><center>ALUMNO<br>NO<br>PERTENECE<br>AL<br>PABELLON</center></html>");
//                btnRegistrar.setEnabled(false);
                return;
            }

        } catch (Exception e) {
            new ErrorDialog("Excepcion no controlada", "ERROR", e);
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

        mostrarDatos();
        //registrar
        guardarAsistencia();
        guardarImagenHuella();
//        try {
//            guardarAsistencia();
//            guardarImagenHuella();
//        } catch (IOException ex) {
//            Logger.getLogger(DLGRegistraAsistenciaExamen.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception e) {
//            Logger.getLogger(DLGRegistraAsistenciaExamen.class.getName()).log(Level.SEVERE, null, e);
//        }
//DibujarHuella(getImageHuella());

    }//GEN-LAST:event_formWindowOpened

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        try {
//            guardarAsistencia();
//            guardarImagenHuella();
            accionCerrarVentana();
        } catch (Exception ex) {
            Logger.getLogger(DLGRegistraAsistenciaExamen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void guardarAsistencia() {
        logger.info("guardando asistencia");
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
                warning("LA ASISTENCIA DEL ESTUDIANTE YA FUE REGISTRADA.");
                //JOptionPane.showMessageDialog(this, "La ASISTENCIA del estudiante : " + ae.getEstudiante().getCompleto() + " Ya fue registrada", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            asistenciaExamenDao.guardar(asistenciaExamen);
            ok("La ASISTENCIA se guardo correctamente");
            //getParentFrame().actualizaContador();
            //JOptionPane.showMessageDialog(this, "La ASISTENCIA se guardo correctamente", "AVISO", JOptionPane.INFORMATION_MESSAGE);
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
        String _ruta = dir_huellas + "/estudiantes/examen/" + getExamen().getId() + "_" + getAulaExamen_s().getEstudiante().getDni();
        String _ruta2 = "huellas/estudiantes/examen/" + getExamen().getId() + "_" + getAulaExamen_s().getEstudiante().getDni();
        boolean isdir = FileUtil.isDirRewritable(dir_huellas);//el path es un directoro y es escribible?
        if (isdir) {
            try {
                logger.info("guardando huella ...");
                ImageUtils.save(_ruta, img, "gif");
            } catch (Exception ex1) {
                logger.error("(1)No se puede guardar la huella en la ruta: " + _ruta, ex1);
            }
        } else {
            try {
                logger.info("No es scribible el directorio, guardando huella en ..." + _ruta2);
                ImageUtils.save(_ruta2, img, "gif");
            } catch (Exception ex2) {
                logger.error("(2)No se puede guardar la huella en la ruta: " + _ruta2, ex2);
            }
        }

        // accionCerrarVentana();
    }

    private void accionCerrarVentana() {
        setVisible(false);
        dispose();
    }

    private void ok(String msg) {
        lblMsg.setIcon(new ImageIcon(getClass().getResource("/images/24/checkmark.png")));
        lblMsg.setText(msg);
    }

    private void warning(String msg) {
        jPanel1.setBackground(new java.awt.Color(248, 215, 218));
        lblMsg.setIcon(new ImageIcon(getClass().getResource("/images/24/warning.png")));
        lblMsg.setText(msg);
    }

    private void error(String msg) {

        jPanel1.setBackground(new java.awt.Color(225, 3, 25));
        lblMsg.setIcon(new ImageIcon(getClass().getResource("/images/24/close.png")));

        lblMsg.setText(msg);
    }

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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblAula;
    private javax.swing.JLabel lblCompleto;
    private javax.swing.JLabel lblDni;
    private javax.swing.JLabel lblImagenFoto;
    private javax.swing.JLabel lblImagenHuella;
    private javax.swing.JLabel lblMensaje;
    private javax.swing.JLabel lblMsg;
    private javax.swing.JLabel lblPabellon;
    // End of variables declaration//GEN-END:variables

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

    /**
     * @return the HuellaEstudiante
     */
    public AulaEstudiante getHuellaEstudiante() {
        return HuellaEstudiante;
    }

    /**
     * @param HuellaEstudiante the HuellaEstudiante to set
     */
    public void setHuellaEstudiante(AulaEstudiante HuellaEstudiante) {
        this.HuellaEstudiante = HuellaEstudiante;
    }

    /**
     * @return the aulaExamen_s
     */
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

    /**
     * @return the parentFrame
     */
    public IFAsistenciaExamen getParentFrame() {
        return parentFrame;
    }

    /**
     * @param parentFrame the parentFrame to set
     */
    public void setParentFrame(IFAsistenciaExamen parentFrame) {
        this.parentFrame = parentFrame;
    }

}
