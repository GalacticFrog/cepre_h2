/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.estudiante;

import com.flesoft.cepre.dao.AulaEstudianteDao;
import com.flesoft.cepre.dao.PabellonDao;
import com.flesoft.cepre.dao.SedeDao;
import com.flesoft.cepre.model.Aula;
import com.flesoft.cepre.model.AulaEstudiante;
import com.flesoft.cepre.model.Estudiante;
import com.flesoft.cepre.model.Pabellon;
import com.flesoft.cepre.model.Sede;
import com.flesoft.cepre.validator.Validator;
import com.flesoft.cepre.model.Usuario;
import com.flesoft.cepre.ui.util.FiltroNumeros;
import com.flesoft.cepre.ui.util.UppercaseDocumentFilter;
import com.flesoft.cepre.util.AppContext;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import org.oxbow.swingbits.dialog.task.TaskDialogs;

/**
 *
 * @author edrev
 */
public class DLGEstudianteEditar extends javax.swing.JDialog {

    private AulaEstudiante aulaEstudiante;//usuario para mostrar en el form
    private Validator<AulaEstudiante> validador;
    private IFIEstudiantes parentFrame;

    public DLGEstudianteEditar(long aulaEstudiante_id, IFIEstudiantes parentFrame) {
        super(new java.awt.Frame(), true);
        initComponents();
        AulaEstudianteDao aeDao = AppContext.getInstance().getBean(AulaEstudianteDao.class);
        AulaEstudiante ae = aeDao.buscarPorId(aulaEstudiante_id);
        setAulaEstudiante(ae);
        setParentFrame(parentFrame);
        incializaTextos();
        try {
            cargarCombos();
            mostrarEstudiante();
            addComboSedeEvent();
            addComboPabelloneEvent();

        } catch (Exception ex) {

            TaskDialogs.showException(ex, "Error", "Error cargando Datos");
            ex.printStackTrace();
        }

        setLocationRelativeTo(null);

    }

    private void incializaTextos() {
        txtDni.setDocument(new FiltroNumeros(txtDni, 8));
        DocumentFilter filter = new UppercaseDocumentFilter();
        ((AbstractDocument) txtApellidos.getDocument()).setDocumentFilter(filter);

    }

    private void mostrarEstudiante() {
        Estudiante estudiante_ = getAulaEstudiante().getEstudiante();
        System.out.println("d:" + estudiante_);
        txtNombres.setText(estudiante_.getNombres());
        txtApellidos.setText(estudiante_.getApellidos());
        txtDireccion.setText(estudiante_.getDireccion());
        txtTelefono.setText(estudiante_.getTelefono());
        txtDni.setText(estudiante_.getDni());
        comboAppSede.setSelectedItem(getAulaEstudiante().getAula().getPabellon().getSede());
        comboAppPabellone.setSelectedItem(getAulaEstudiante().getAula().getPabellon());
        comboAppAulae.setSelectedItem(getAulaEstudiante().getAula());
//        txtSede.setText(getAulaEstudiante().getAula().getPabellon().getSede().getNombreSede());
//        txtPabellon.setText(getAulaEstudiante().getAula().getPabellon().getDenominacion());
//        txtAula.setText(getAulaEstudiante().getAula().getDenominacion());

//"fotos/" + estudiante_.getFotografia())
    }

    private AulaEstudiante getAulaEstudianteDLG() {
        Estudiante estudiante_ = getAulaEstudiante().getEstudiante();
        estudiante_.setApellidos(txtApellidos.getText());
        estudiante_.setNombres(txtNombres.getText());
        estudiante_.setDireccion(txtDireccion.getText());
        estudiante_.setTelefono(txtTelefono.getText());
        estudiante_.setDni(txtDni.getText());

        Aula aula_ = (Aula) comboAppAulae.getSelectedItem();
        String turno = comboTurno.getSelectedIndex() == 0 ? "m" : "t";

        AulaEstudiante aulaEstudiante_ = getAulaEstudiante();
        aulaEstudiante_.setEstudiante(estudiante_);
        aulaEstudiante_.setAula(aula_);
        aulaEstudiante_.setTurno(turno);

        return aulaEstudiante_;
    }

    private void guardarUsuario() {
        try {
            AulaEstudianteDao ud = AppContext.getInstance().getBean(AulaEstudianteDao.class);
            AulaEstudiante ae = getAulaEstudianteDLG();
            validador = new AulaEstudianteValidator();
            String msg = validador.validate(ae);

            Sede sede_ = (Sede) comboAppSede.getSelectedItem();
            Pabellon pabellon_ = (Pabellon) comboAppPabellone.getSelectedItem();
            Aula aula_ = (Aula) comboAppAulae.getSelectedItem();

            if (sede_ == null) {
                msg = msg + "Seleccione una sede.\n";
            }
            if (pabellon_ == null) {
                msg = msg + "Selecciona un pabellon.\n";
            }
            if (aula_ == null) {
                msg = msg + "Selecciona un aula.\n";
            }
            if (!"".equals(msg == null ? "" : msg)) {
                TaskDialogs.warning(this, "Corrija los siguientes errores e intentelo nuevamente!", msg);
                return;
            }

            ud.guardar(ae);
            TaskDialogs.inform(this, "Usuario guardado correctamente", "Se editaron los datos del usuario.");
            accionCerrarVentana();
            int actual = getParentFrame().getPaginaActual();
            getParentFrame().setPaginaActual(actual);
            getParentFrame().paginarTablaUsuarios(actual);

        } catch (Exception ex) {
            TaskDialogs.showException(ex, "ERROR", "NO SE PUDO GUARDAR EL ESTUDIANTE");
            ex.printStackTrace();
        }
    }

    private void addComboSedeEvent() {
        comboAppSede.addActionListener((ActionEvent event) -> {
            JComboBox comboBox = (JComboBox) event.getSource();
            Sede selected = (Sede) comboBox.getSelectedItem();
            List<Pabellon> pabellones = selected.getPabellones();
            comboAppPabellone.setModel(new DefaultComboBoxModel(pabellones.toArray()));

            Pabellon selected2 = (Pabellon) comboAppPabellone.getSelectedItem();
            List<Aula> aulas = selected2.getAulas();
            comboAppAulae.setModel(new DefaultComboBoxModel(aulas.toArray()));
        });
    }

    private void addComboPabelloneEvent() {
        comboAppPabellone.addActionListener((ActionEvent event) -> {
            JComboBox comboBox = (JComboBox) event.getSource();
            Pabellon selected = (Pabellon) comboBox.getSelectedItem();
            List<Aula> aulas = selected.getAulas();
            comboAppAulae.setModel(new DefaultComboBoxModel(aulas.toArray()));
        });
    }

    private void cargarCombos() {

        Sede sede = getAulaEstudiante().getAula().getPabellon().getSede();
        Pabellon pabellon = getAulaEstudiante().getAula().getPabellon();

        SedeDao sedeDao = AppContext.getInstance().getBean(SedeDao.class);//comboAppSede
        //PabellonDao pabellonDao = AppContext.getInstance().getBean(PabellonDao.class);
        List<Sede> sedes = sedeDao.listar();
        comboAppSede.setModel(new DefaultComboBoxModel(sedes.toArray()));
        List<Pabellon> pabellones = sede.getPabellones();
        //pabellonees.add(0, new Pabellon(0, "--TODOS--"));
        comboAppPabellone.setModel(new DefaultComboBoxModel(pabellones.toArray()));

        List<Aula> aulas = pabellon.getAulas();
        //aulas.add(0, new Aula(0, "--TODOS--", 0, 0));
        comboAppAulae.setModel(new DefaultComboBoxModel(aulas.toArray()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtNombres = new javax.swing.JTextField();
        txtApellidos = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtDni = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        comboTurno = new javax.swing.JComboBox<>();
        comboAppSede = new javax.swing.JComboBox<>();
        comboAppAulae = new javax.swing.JComboBox<>();
        comboAppPabellone = new javax.swing.JComboBox<>();
        btnNuevo1 = new javax.swing.JButton();
        btnNuevo2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        jLabel11.setText("jLabel11");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.SystemColor.activeCaptionBorder));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel6.setText("NOMBRES:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel7.setText("APELLIDOS:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel14.setText("DNI:");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel8.setText("DIRECCION:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel9.setText("TELEFONO:");

        txtNombres.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N

        txtApellidos.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N

        txtDireccion.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N

        txtTelefono.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N

        txtDni.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel15.setText("TURNO:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel16.setText("SEDE:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel17.setText("LOCAL:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel18.setText("AULA:");

        comboTurno.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        comboTurno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ma\u00f1ana", "Tarde" }));

        comboAppSede.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        comboAppAulae.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        comboAppPabellone.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtApellidos, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombres)
                            .addComponent(txtDni, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTelefono)
                            .addComponent(txtDireccion)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboTurno, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboAppSede, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboAppPabellone, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboAppAulae, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboAppSede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboAppPabellone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboAppAulae, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnNuevo1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnNuevo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/delete.png"))); // NOI18N
        btnNuevo1.setText("Cancelar");
        btnNuevo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevo1ActionPerformed(evt);
            }
        });

        btnNuevo2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnNuevo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/save.png"))); // NOI18N
        btnNuevo2.setText("Guardar");
        btnNuevo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevo2ActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.SystemColor.activeCaptionBorder));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/40/icons8-EditUser-40.png"))); // NOI18N

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTitulo.setText("Datos del estudiante");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 239, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addGap(20, 20, 20)))
                .addGap(5, 5, 5))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNuevo2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNuevo1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo1ActionPerformed
        accionCerrarVentana();
    }//GEN-LAST:event_btnNuevo1ActionPerformed

    private void btnNuevo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo2ActionPerformed
        guardarUsuario();

    }//GEN-LAST:event_btnNuevo2ActionPerformed

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
    private javax.swing.JComboBox<String> comboAppAulae;
    private javax.swing.JComboBox<String> comboAppPabellone;
    private javax.swing.JComboBox<String> comboAppSede;
    private javax.swing.JComboBox<String> comboTurno;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the estudiante
     */
    public AulaEstudiante getAulaEstudiante() {
        return aulaEstudiante;
    }

    /**
     * aulaEstudiante@param estudiante the estudiante to set
     */
    public void setAulaEstudiante(AulaEstudiante aulaEstudiante) {
        this.aulaEstudiante = aulaEstudiante;
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
