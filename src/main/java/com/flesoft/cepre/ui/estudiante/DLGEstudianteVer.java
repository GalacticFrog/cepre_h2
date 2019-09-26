/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.estudiante;

import com.flesoft.cepre.model.AulaEstudiante;
import com.flesoft.cepre.model.Estudiante;
import com.flesoft.cepre.validator.Validator;
import com.flesoft.cepre.model.Usuario;
import com.flesoft.cepre.util.AppContext;
import com.flesoft.cepre.util.Config;
import demo.FrmContainer;
import com.flesoft.cepre.ui.util.ReportViewer;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author edrev
 */
public class DLGEstudianteVer extends javax.swing.JDialog {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DLGEstudianteVer.class);

    private AulaEstudiante estudiante;//usuario para mostrar en el form
    private IFIEstudiantes parentFrame;

    public DLGEstudianteVer(AulaEstudiante estudiante, IFIEstudiantes parentFrame) {
        super(new javax.swing.JFrame(), true);
        initComponents();
        setAulaEstudiante(estudiante);
        setParentFrame(parentFrame);
        mostrarEstudiante();
        setLocationRelativeTo(null);

    }

    private void mostrarEstudiante() {
        Estudiante estudiante_ = getAulaEstudiante().getEstudiante();
        System.out.println("estudiante:" + estudiante_ + " Huella:" + getAulaEstudiante().getHuella());
        lblNombres.setText(estudiante_.getNombres());
        lblApellidos.setText(estudiante_.getApellidos());
        lblDireccion.setText(estudiante_.getDireccion());
        lblTelefono.setText(estudiante_.getTelefono());
        lblCiclo.setText(Config.CICLO);
        lblDni.setText(estudiante_.getDni());
        lblEscuela.setText(estudiante_.getEscuela().getNombreEscuela());
        lblSede.setText(getAulaEstudiante().getAula().getPabellon().getSede().getNombreSede());
        lblPabellon.setText(getAulaEstudiante().getAula().getPabellon().getDenominacion());
        lblAula.setText(getAulaEstudiante().getAula().getDenominacion());
        String turno = getAulaEstudiante().getTurno().equals("m") ? "Ma\u00f1ana" : "Tarde";
        lblTurno.setText(turno);
        String dir_fotos = Config.DIR_FOTOS;
        lblHuella.setText(getAulaEstudiante().getHuella() == null ? "Sin registrar" : "Registrado");
        dibujarFoto(dir_fotos + "/" + estudiante_.getFotografia());
        String dir_huellas = Config.DIR_HUELLAS;
        String imagenHuella = (getAulaEstudiante().getHuella() == null) ? "img/SIN_HUELLA.jpg" : dir_huellas + "/estudiantes/" + getAulaEstudiante().getEstudiante().getDni() + ".gif";
        DibujarHuella(imagenHuella);
    }

    private void dibujarFoto(String ruta) {
        //BufferedImage img = null;
        try {
            BufferedImage img = ImageIO.read(new File(ruta));
            Image dimg = img.getScaledInstance(lblImagenFoto.getWidth(), lblImagenFoto.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon imageIcon = new ImageIcon(dimg);
            lblImagenFoto.setIcon(imageIcon);
        } catch (IOException e) {
            LOGGER.error("No se puede encontrar la foto: " + ruta, e);
            //e.printStackTrace();
        }

    }

    public void DibujarHuella(String ruta) {
        try {
            File pathToFile = new File(ruta);
            Image image = ImageIO.read(pathToFile);
            lblImagenHuella.setIcon(new ImageIcon(image.getScaledInstance(lblImagenHuella.getWidth(), lblImagenHuella.getHeight(), Image.SCALE_DEFAULT)));
            repaint();
        } catch (IOException ex) {
            LOGGER.error("No se puede cargar la imagen de la huella: " + ruta, ex);
            //ex.printStackTrace();
        }
    }

    private void imprimirDatos() {
        try {
            Estudiante estudiante_ = getAulaEstudiante().getEstudiante();
            String dir_fotos = Config.DIR_FOTOS;
            String dir_huellas = Config.DIR_HUELLAS;

            String imagenFoto = dir_fotos + "/" + getAulaEstudiante().getEstudiante().getFotografia();
            String imagenHuella = dir_huellas + "/estudiantes/" + getAulaEstudiante().getId() + "_" + getAulaEstudiante().getEstudiante().getDni() + ".gif";
            File f_foto = new File(imagenFoto);
            String imagenFoto2 = (f_foto.exists() && !f_foto.isDirectory()) ? imagenFoto : "img/photo.jpg";

            File f_huella = new File(imagenHuella);
            String imagenHuella2 = getAulaEstudiante().getHuella() == null
                    ? "img/SIN_HUELLA.jpg"
                    : ((f_foto.exists() && !f_huella.isDirectory())
                    ? imagenHuella
                    : "img/NO_IMAGE.jpg");

            //String imagenHuella2 = (f_foto.exists() && !f_huella.isDirectory()) ? imagenHuella : "";
            String turno = getAulaEstudiante().getTurno().equals("m") ? "Ma\u00f1ana" : "Tarde";
            String LOGO = "img/logo.jpg";

            HashMap param = new HashMap();
            param.put("urlLogo", LOGO);
            param.put("nombres", estudiante_.getNombres());
            param.put("apellidos", estudiante_.getApellidos());
            param.put("direccion", estudiante_.getDireccion());
            param.put("telefono", estudiante_.getTelefono());
            param.put("ciclo", Config.CICLO);
            param.put("escuela", estudiante_.getEscuela().getNombreEscuela());
            param.put("sede", getAulaEstudiante().getAula().getPabellon().getSede().getNombreSede());
            param.put("local", getAulaEstudiante().getAula().getPabellon().getDenominacion());
            param.put("aula", getAulaEstudiante().getAula().getDenominacion());
            param.put("turno", turno);
            param.put("dni", estudiante_.getDni());
            param.put("imghuella", imagenHuella2);
            param.put("imgfoto", imagenFoto2);
            System.out.println("param: " + param);
            JasperPrint jasperPrint = JasperFillManager.fillReport("reportes/Postulante.jasper", param, new JREmptyDataSource());
//jasperPrint.sett
            //JasperViewer.viewReport(jasperPrint, false);

            //viewer.removeSaveContributor();
            ReportViewer dialog = new ReportViewer(jasperPrint);
            dialog.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(FrmContainer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(FrmContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void imprimirAsistenciaExamens() {

        try {
            DriverManagerDataSource dataSource = AppContext.getInstance().getBean("externalDataSource", DriverManagerDataSource.class);
            Connection connection = dataSource.getConnection();
            Estudiante estudiante_ = getAulaEstudiante().getEstudiante();
            String dir_fotos = Config.DIR_FOTOS;
            String imagenFoto = dir_fotos + "/" + getAulaEstudiante().getEstudiante().getFotografia();
            String dir_huellas = Config.DIR_HUELLAS;
            String imagenHuella = dir_huellas + "/estudiantes/" + getAulaEstudiante().getId() + "_" + getAulaEstudiante().getEstudiante().getDni() + ".gif";

            File f_foto = new File(imagenFoto);
            String imagenFoto2 = (f_foto.exists() && !f_foto.isDirectory()) ? imagenFoto : "img/photo.jpg";

            File f_huella = new File(imagenHuella);
            String imagenHuella2 = getAulaEstudiante().getHuella() == null
                    ? "img/SIN_HUELLA.jpg"
                    : ((f_foto.exists() && !f_huella.isDirectory())
                    ? imagenHuella
                    : "img/NO_IMAGE.jpg");

            String turno = getAulaEstudiante().getTurno().equals("m") ? "Ma\u00f1ana" : "Tarde";
            //PabellonE pabellonee_ = pabelloneDao.buscarPorId(Config.PABELLONE);//el pabellon puede cambiar dependiendo del filtro
            String LOGO = "img/logo.jpg";

            HashMap param = new HashMap();
            param.put("urlLogo", LOGO);
            param.put("dir_huellas", dir_huellas);
            param.put("nombres", estudiante_.getNombres());
            param.put("apellidos", estudiante_.getApellidos());
            param.put("direccion", estudiante_.getDireccion());
            param.put("telefono", estudiante_.getTelefono());
            param.put("ciclo", Config.CICLO);
            param.put("escuela", estudiante_.getEscuela().getNombreEscuela());
            param.put("sede", getAulaEstudiante().getAula().getPabellon().getSede().getNombreSede());
            param.put("local", getAulaEstudiante().getAula().getPabellon().getDenominacion());
            param.put("aula", getAulaEstudiante().getAula().getDenominacion());
            param.put("turno", turno);
            param.put("dni", estudiante_.getDni());
            param.put("imghuella", imagenHuella2);
            param.put("imgfoto", imagenFoto2);
            param.put("estudiante_id", estudiante_.getId());

            System.out.println("param: " + param);
            JasperPrint jasperPrint = JasperFillManager.fillReport("reportes/reporteAExamen.jasper", param, connection);
            ReportViewer dialog = new ReportViewer(jasperPrint);
            dialog.setVisible(true);

        } catch (JRException ex) {
            Logger.getLogger(FrmContainer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DLGEstudianteVer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    private Usuario getUsuarioDLG() {
//        Persona persona_ = getPersona();
//        persona_.setApellidoMaterno(txtMaterno.getText());
//        persona_.setApellidoPaterno(txtMaterno.getText());
//        persona_.setNombres(txtNombres.getText());
//        persona_.setDireccion(txtTelefono.getText());
//        persona_.setTelefono(txtDni.getText());
//        persona_.setDni(txtDireccion.getText());
//
//        Usuario usuario_ = getUsuario();
//        usuario_.setUsuario(txtDireccion.getText());
////        String _txtpass1 = String.copyValueOf(txtPass1.getPassword());
//        usuario_.setPersona(persona_);
//
//        return usuario_;
//    }

    private void guardarUsuario() {
//        try {
//            UsuarioDao ud = AppContext.getInstance().getBean(UsuarioDao.class);
//            Usuario usuario_ = getUsuarioDLG();
//            validador = new UsuarioValidator();
//            String msg = validador.validate(usuario_);
//
//            String _txtpass1 = String.copyValueOf(txtPass1.getPassword());
//            String _txtpass2 = String.copyValueOf(txtPass2.getPassword());
//
//            if (!_txtpass1.isEmpty() || !_txtpass2.isEmpty()) {//Validar si escribio una contrasena
//                String salt = ShiroUtils.getSalt();
//                String password = ShiroUtils.encodePassphrase(_txtpass1, salt);
//                usuario_.setContrasena(password);
//                usuario_.setSalt(salt);
//                if (!_txtpass2.equals(_txtpass1)) {
//                    msg = msg + "Las dos contrase\u00F1as deben ser iguales.\n";
//                }
//            }
//            if (!"".equals(msg == null ? "" : msg)) {
//                TaskDialogs.warning(this, "Corrija los siguientes errores e intentelo nuevamente!", msg);
//                return;
//            }
//
//            ud.guardar(usuario_);
//            TaskDialogs.inform(this, "Usuario guardado correctamente", "Se editaron los datos del usuario.");
//            getParentFrame().cargarUsuarios();
//            accionCerrarVentana();
//        } catch (Exception ex) {
//            TaskDialogs.showException(ex, "ERROR", "NO SE PUDO GUARDAR EL USUARIO");
//            ex.printStackTrace();
//        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblImagenFoto = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblNombres = new javax.swing.JLabel();
        lblApellidos = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JLabel();
        lblSede = new javax.swing.JLabel();
        lblPabellon = new javax.swing.JLabel();
        lblAula = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        lblEscuela = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblCiclo = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lblTurno = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lblHuella = new javax.swing.JLabel();
        lblImagenHuella = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblDni = new javax.swing.JLabel();
        btnNuevo1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        btnNuevo2 = new javax.swing.JButton();
        btnNuevo3 = new javax.swing.JButton();
        btnNuevo4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        jLabel11.setText("jLabel11");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.SystemColor.activeCaptionBorder));

        lblImagenFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/photo.jpg"))); // NOI18N
        lblImagenFoto.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaptionBorder));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaptionBorder));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel6.setText("NOMBRES:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel7.setText("APELLIDOS:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel8.setText("DIRECCION:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel9.setText("TELEFONO:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel15.setText("SEDE:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel16.setText("LOCAL:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel17.setText("AULA:");

        lblNombres.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblNombres.setText("...");

        lblApellidos.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        lblApellidos.setText("...");

        lblDireccion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDireccion.setText("...");

        lblTelefono.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTelefono.setText("...");

        lblSede.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSede.setText("...");

        lblPabellon.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblPabellon.setText("...");

        lblAula.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblAula.setText("...");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel18.setText("ESCUELA:");

        lblEscuela.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEscuela.setText("...");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel10.setText("CICLO:");

        lblCiclo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblCiclo.setText("...");

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel19.setText("TURNO:");

        lblTurno.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTurno.setText("...");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel20.setText("HUELLA:");

        lblHuella.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHuella.setText("...");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombres, javax.swing.GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)
                            .addComponent(lblApellidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEscuela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCiclo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSede, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPabellon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblAula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTurno, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblHuella, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblNombres))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblApellidos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblDireccion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblTelefono))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(lblCiclo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(lblEscuela))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(lblSede))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(lblPabellon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(lblAula))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(lblTurno))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(lblHuella))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblImagenHuella.setBackground(new java.awt.Color(255, 255, 255));
        lblImagenHuella.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/NO_IMAGE.jpg"))); // NOI18N
        lblImagenHuella.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel14.setText("DNI:");

        lblDni.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblDni.setText("...");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblDni, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(lblImagenFoto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblImagenHuella)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblImagenHuella, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblImagenFoto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(lblDni))
                .addGap(16, 16, 16))
        );

        btnNuevo1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnNuevo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/delete.png"))); // NOI18N
        btnNuevo1.setText("Cerrar");
        btnNuevo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevo1ActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.SystemColor.activeCaptionBorder));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/40/icons8-EditUser-40.png"))); // NOI18N

        lblTitulo.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        lblTitulo.setText("Datos del estudiante");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(lblTitulo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnNuevo2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnNuevo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/print.png"))); // NOI18N
        btnNuevo2.setText("Asistencia examenes");
        btnNuevo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevo2ActionPerformed(evt);
            }
        });

        btnNuevo3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnNuevo3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/edit.png"))); // NOI18N
        btnNuevo3.setText("Editar datos");
        btnNuevo3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevo3ActionPerformed(evt);
            }
        });

        btnNuevo4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnNuevo4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/print.png"))); // NOI18N
        btnNuevo4.setText("Imprimir datos");
        btnNuevo4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevo4ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("FOTO");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("HUELLA");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(105, 105, 105)
                .addComponent(jLabel3)
                .addGap(224, 224, 224)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNuevo2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNuevo4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNuevo1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo1ActionPerformed
        accionCerrarVentana();
    }//GEN-LAST:event_btnNuevo1ActionPerformed

    private void btnNuevo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo2ActionPerformed
//        accionCerrarVentana();
        imprimirAsistenciaExamens();
    }//GEN-LAST:event_btnNuevo2ActionPerformed

    private void btnNuevo3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo3ActionPerformed
        accionCerrarVentana();
        DLGEstudianteEditar dlg = new DLGEstudianteEditar(getAulaEstudiante().getId(), getParentFrame());
        dlg.setVisible(true);

    }//GEN-LAST:event_btnNuevo3ActionPerformed

    private void btnNuevo4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo4ActionPerformed
//        accionCerrarVentana();
        imprimirDatos();
    }//GEN-LAST:event_btnNuevo4ActionPerformed

    @Override
    protected JRootPane createRootPane() {
        JRootPane rootPane_ = new JRootPane();
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        Action actionListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                accionCerrarVentana();
            }
        };
        InputMap inputMap = rootPane_.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(stroke, "ESCAPE");
        rootPane_.getActionMap().put("ESCAPE", actionListener);

        return rootPane_;
    }

    private void accionCerrarVentana() {
        setVisible(false);
        dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNuevo1;
    private javax.swing.JButton btnNuevo2;
    private javax.swing.JButton btnNuevo3;
    private javax.swing.JButton btnNuevo4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblApellidos;
    private javax.swing.JLabel lblAula;
    private javax.swing.JLabel lblCiclo;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblDni;
    private javax.swing.JLabel lblEscuela;
    private javax.swing.JLabel lblHuella;
    private javax.swing.JLabel lblImagenFoto;
    private javax.swing.JLabel lblImagenHuella;
    private javax.swing.JLabel lblNombres;
    private javax.swing.JLabel lblPabellon;
    private javax.swing.JLabel lblSede;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblTurno;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the estudiante
     */
    public AulaEstudiante getAulaEstudiante() {
        return estudiante;
    }

    /**
     * @param estudiante the estudiante to set
     */
    public void setAulaEstudiante(AulaEstudiante estudiante) {
        this.estudiante = estudiante;
    }

    /**
     * @return the parentFrame
     */
    public IFIEstudiantes getParentFrame() {
        return parentFrame;
    }

    /**
     * @param parentFrame the parentFrame to set
     */
    public void setParentFrame(IFIEstudiantes parentFrame) {
        this.parentFrame = parentFrame;
    }

}
