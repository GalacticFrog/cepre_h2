/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.asistencia;

import com.digitalpersona.uareu.Fid;
import com.flesoft.cepre.dao.AsistenciaClaseDao;
import com.flesoft.cepre.dao.EstudianteDao;
import com.flesoft.cepre.model.AsistenciaClase;
import com.flesoft.cepre.model.Aula;
import com.flesoft.cepre.model.AulaEstudiante;
import com.flesoft.cepre.model.Estudiante;
import com.flesoft.cepre.model.Huella;
import com.flesoft.cepre.model.Pabellon;
import com.flesoft.cepre.model.Sede;
import com.flesoft.cepre.ui.util.ErrorDialog;
import com.flesoft.cepre.util.AppContext;
import com.flesoft.cepre.util.Config;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Date;
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
public class DLGRegistraAsistenciaClase extends javax.swing.JDialog {

    private static final Log logger = LogFactory.getLog(DLGRegistraAsistenciaClase.class);

    private AulaEstudiante HuellaEstudiante;
    private Fid imageHuella;

    private AulaEstudiante aulaEstudiante_s;//para guardar asistencia

    public DLGRegistraAsistenciaClase() {
        super(new java.awt.Frame(), true);
        initComponents();
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Dimension screenSize = toolkit.getScreenSize();
        final int x = (screenSize.width - getWidth()) / 2;
        final int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
        getRootPane().setDefaultButton(btnRegistrar);
        btnRegistrar.requestFocus();

    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
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
        jLabel9 = new javax.swing.JLabel();
        lblTurno = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblImagenHuella = new javax.swing.JLabel();
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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "DATOS DEL ESTUDIANTE", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI Light", 1, 24)); // NOI18N
        jLabel7.setText("Nombres:");

        lblCompleto.setFont(new java.awt.Font("Segoe UI Light", 1, 40)); // NOI18N
        lblCompleto.setText(".");

        lblDni.setFont(new java.awt.Font("Segoe UI Light", 1, 30)); // NOI18N
        lblDni.setText(".");

        jLabel6.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel6.setText("Pabellon:");

        lblPabellon.setFont(new java.awt.Font("Segoe UI Light", 1, 20)); // NOI18N
        lblPabellon.setText(".");

        lblAula.setFont(new java.awt.Font("Segoe UI Light", 1, 36)); // NOI18N
        lblAula.setText(".");

        jLabel8.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel8.setText("Aula:");

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel4.setText("DNI:");

        lblImagenFoto.setBackground(new java.awt.Color(255, 255, 255));
        lblImagenFoto.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("FOTOGRAFIA");

        jLabel9.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel9.setText("Turno:");

        lblTurno.setFont(new java.awt.Font("Segoe UI Light", 1, 36)); // NOI18N
        lblTurno.setText(".");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCompleto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblImagenFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTurno, javax.swing.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
                            .addComponent(lblAula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPabellon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblImagenFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(lblDni))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(lblPabellon))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblAula)
                            .addComponent(jLabel8))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(lblTurno))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "HUELLA DACTILAR", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        lblImagenHuella.setBackground(new java.awt.Color(255, 255, 255));
        lblImagenHuella.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblImagenHuella, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImagenHuella, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnRegistrar.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        btnRegistrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/32/Checked-32.png"))); // NOI18N
        btnRegistrar.setText("Registrar asistencia");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addGap(266, 266, 266)
                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        lblTurno.setText("");
        lblPabellon.setText("");
        lblAula.setText("");
    }

    private void mostrarDatos() {
        //String ciclo_id = Config.CICLO;
        long estudiante_id = getHuellaEstudiante().getEstudiante().getId();
        try {
            EstudianteDao estudianteDao = AppContext.getInstance().getBean(EstudianteDao.class);
            AulaEstudiante aulaEstudiante = estudianteDao.buscarAula(estudiante_id);

            Estudiante estudiante = aulaEstudiante.getEstudiante();
            Aula aula = aulaEstudiante.getAula();
            Pabellon pabellon = aula.getPabellon();
            Sede sede = aulaEstudiante.getAula().getPabellon().getSede();
            String turno = aulaEstudiante.getTurno().equals("m") ? "Ma\u00F1ana" : "Tarde";

            lblDni.setText(estudiante.getDni());
            lblCompleto.setText(estudiante.getCompleto());
            lblTurno.setText(turno);
            lblPabellon.setText(pabellon.getDenominacion());
            lblAula.setText(aula.getDenominacion());
            //foto
            String dir_fotos = Config.DIR_FOTOS;
            String foto_url = dir_fotos + "/" + estudiante.getFotografia();
            System.out.println(foto_url);
            lblImagenFoto.setIcon(new ImageIcon(foto_url));
            setAulaEstudiante_s(aulaEstudiante);
            //huella
            AulaEstudiante huellaImg = getHuellaEstudiante();
            String dir_huellas = Config.DIR_HUELLAS;
            String imagenHuella = dir_huellas + "/estudiantes/" + aulaEstudiante.getId() + "_" + huellaImg.getEstudiante().getDni() + ".gif";
            DibujarHuella(imagenHuella);

        } catch (Exception e) {
            new ErrorDialog("Ecepcion no controlada", "error", e);
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
        //DibujarHuella(getImageHuella());
    }//GEN-LAST:event_formWindowOpened

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        guardarAsistencia();
        accionCerrarVentana();
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void guardarAsistencia() {
        logger.info("guardando asistencia");
        //String ciclo_id = Config.CICLO;
        try {

            AulaEstudiante ae = getAulaEstudiante_s();
            System.out.println("ae:::::::" + ae);
            AsistenciaClase asistenciaClase = new AsistenciaClase();

            asistenciaClase.setFecha(new Date());
            asistenciaClase.setAulaEstudiante(ae);

            AsistenciaClaseDao asistenciaClaseDao = AppContext.getInstance().getBean(AsistenciaClaseDao.class);
            boolean existe = asistenciaClaseDao.existeHuellaCiclo(ae.getId());
            if (existe) {
                JOptionPane.showMessageDialog(this, "La ASISTENCIA del estudiante : " + ae.getEstudiante().getCompleto() + " Ya fue registrada", "AVISO", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            asistenciaClaseDao.guardar(asistenciaClase);

            JOptionPane.showMessageDialog(this, "La ASISTENCIA se guardo correctamente", "AVISO", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            new ErrorDialog("Excepcion no controlada", "ERROR", e);
        }

    }

    private void accionCerrarVentana() {
        setVisible(false);
        dispose();
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblAula;
    private javax.swing.JLabel lblCompleto;
    private javax.swing.JLabel lblDni;
    private javax.swing.JLabel lblImagenFoto;
    private javax.swing.JLabel lblImagenHuella;
    private javax.swing.JLabel lblPabellon;
    private javax.swing.JLabel lblTurno;
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
     * @return the aulaEstudiante_s
     */
    public AulaEstudiante getAulaEstudiante_s() {
        return aulaEstudiante_s;
    }

    /**
     * @param aulaEstudiante_s the aulaEstudiante_s to set
     */
    public void setAulaEstudiante_s(AulaEstudiante aulaEstudiante_s) {
        this.aulaEstudiante_s = aulaEstudiante_s;
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

}
