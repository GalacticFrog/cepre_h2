/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.docentes;

import com.flesoft.cepre.ui.personal.*;
import com.digitalpersona.uareu.Fmd;
import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.ReaderCollection;
import com.digitalpersona.uareu.UareUException;
import com.digitalpersona.uareu.UareUGlobal;
import com.flesoft.cepre.dao.DocenteDao;
import com.flesoft.cepre.dao.PabellonDao;
import com.flesoft.cepre.dao.SedeDao;
import com.flesoft.cepre.model.Aula;
import com.flesoft.cepre.model.Docente;
import com.flesoft.cepre.model.Pabellon;
import com.flesoft.cepre.model.Persona;
import com.flesoft.cepre.model.Sede;
import com.flesoft.cepre.ui.util.FiltroNumeros;
import com.flesoft.cepre.ui.util.MessageBox;
import com.flesoft.cepre.ui.util.UppercaseDocumentFilter;
import com.flesoft.cepre.util.AppContext;
import com.flesoft.cepre.validator.Validator;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import org.oxbow.swingbits.dialog.task.TaskDialogs;

/**
 *
 * @author user
 */
public class DLGEditar extends javax.swing.JDialog {

    private IFIDocentes parentFrame;
    private Docente docente;//usuario para mostrar en el form
    private Persona persona;
    private Validator<Docente> validador;
    //digitalpersona
//    private ReaderCollection m_collection;
//    private Reader m_reader;
//    private Fmd enrollmentFMD;

    public DLGEditar(Docente docente) {
        super(new java.awt.Frame(), true);
        initComponents();
        setDocente(docente);
        setPersona(docente.getPersona());
        incializaTextos();
        cargarCombo();
        mostrar();

        setLocationRelativeTo(null);
    }

    private void mostrar() {

        Docente docente_ = getDocente();
        Persona persona_ = docente_.getPersona();
        txtNombres.setText(persona_.getNombres());
        txtApellidos.setText(persona_.getApellidos());
        txtDni.setText(persona_.getDni());
        comboSede.setSelectedItem(docente_.getSede());
    }

    private void incializaTextos() {
        txtDni.setDocument(new FiltroNumeros(txtDni, 8));
        DocumentFilter filter = new UppercaseDocumentFilter();
        ((AbstractDocument) txtNombres.getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) txtApellidos.getDocument()).setDocumentFilter(filter);
        // ((AbstractDocument) txtMaterno.getDocument()).setDocumentFilter(filter);

    }

    private void cargarCombo() {
        SedeDao sedeDao = AppContext.getInstance().getBean(SedeDao.class);

        List<Aula> aulas = new ArrayList<>();
        aulas.add(0, new Aula(0, "--TODOS--", 0, 0));

        List<Sede> sedes = sedeDao.listar();

        comboSede.setModel(new DefaultComboBoxModel(sedes.toArray()));

    }

    private Docente getDocenteDLG() {
        Persona persona_ = getPersona();

        persona_.setNombres(txtNombres.getText());
        persona_.setApellidos(txtApellidos.getText());
        persona_.setDni(txtDni.getText());
        Docente docente_ = getDocente();
        //       docente_.setDocente(txtDni.getText());
//        String _txtpass1 = String.copyValueOf(txtPass1.getPassword());
        docente_.setPersona(persona_);
        Sede sede = (Sede) comboSede.getSelectedItem();
        docente_.setSede(sede);
        return docente_;
    }

    private void guardarDocente() {
        try {
            DocenteDao uDao = AppContext.getInstance().getBean(DocenteDao.class);
            Docente usuario_ = getDocenteDLG();
            validador = new DocenteValidator();
            String msg = validador.validate(usuario_);

            if (!"".equals(msg == null ? "" : msg)) {
                TaskDialogs.warning(this, "Corrija los siguientes errores e intentelo nuevamente!", msg);
                return;
            }
            uDao.guardar(usuario_);

            TaskDialogs.inform(this, "Guardado correctamente", "Se edit\u00f3 el registro con los datos proporcionados.");
            getParentFrame().cargarUsuarios();
            accionCerrarVentana();
        } catch (Exception ex) {
            TaskDialogs.showException(ex, "ERROR", "NO SE PUDO GUARDAR EL USUARIO");
            ex.printStackTrace();
        }
    }

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

    private void lanzarDialogEstudiante(Docente docente) {
        if (!checkReader()) {
            return;
        }
        DLGRegistraHuella.Run(docente);
        accionCerrarVentana();
//        DLGRegistraHuella dialog = new DLGRegistraHuella();
//        dialog.setAulaEstudiante(aulaEstudiante);
//        dialog.setParentFrame(getParentFrame());
//        dialog.setVisible(true);
    }

    protected boolean checkReader() {
        try {
            ReaderCollection m_collection = UareUGlobal.GetReaderCollection();
            m_collection.GetReaders();
            if (m_collection.isEmpty()) {
                MessageBox.Warning("No se detecto ningun Lector, por favor conecte el lector de huellas antes...");
                return false;
            }
        } catch (UareUException e1) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, "Error getting collection");
            return false;
        }

        return true;
//        m_reader = m_collection.get(0);
//
//        if (null == m_reader) {
//            MessageBox.Warning("Lector no conectado?");
//        }
    }

    /**
     * @return the docente
     */
    public Docente getDocente() {
        return docente;
    }

    /**
     * @param docente the docente to set
     */
    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    /**
     * @return the parentFrame
     */
    public IFIDocentes getParentFrame() {
        return parentFrame;
    }

    /**
     * @param parentFrame the parentFrame to set
     */
    public void setParentFrame(IFIDocentes parentFrame) {
        this.parentFrame = parentFrame;
    }

    /**
     * @return the persona
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnNuevo1 = new javax.swing.JButton();
        btnNuevo2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtApellidos = new javax.swing.JTextField();
        txtNombres = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtDni = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        comboSede = new javax.swing.JComboBox<>();
        btnNuevo3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.SystemColor.activeCaptionBorder));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/40/icons8-EditUser-40.png"))); // NOI18N

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTitulo.setText("Editar datos del docente");

        jLabel13.setText("Ingrese los datos del docente a continuacion.");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnNuevo1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnNuevo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/delete.png"))); // NOI18N
        btnNuevo1.setText("Cerrar");
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

        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.SystemColor.activeCaptionBorder));

        txtApellidos.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N

        txtNombres.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel6.setText("NOMBRES:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel7.setText("APELLIDOS:");

        txtDni.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel14.setText("DNI:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel10.setText("SEDE:");

        comboSede.setFont(new java.awt.Font("Segoe UI Light", 0, 16)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDni)
                            .addComponent(comboSede, 0, 298, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombres)
                            .addComponent(txtApellidos))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(comboSede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        btnNuevo3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        btnNuevo3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/fingerprint.png"))); // NOI18N
        btnNuevo3.setText("Registrar huella");
        btnNuevo3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevo3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNuevo1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNuevo2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnNuevo1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNuevo3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnNuevo2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo2ActionPerformed
        guardarDocente();
    }//GEN-LAST:event_btnNuevo2ActionPerformed

    private void btnNuevo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo1ActionPerformed
        accionCerrarVentana();        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevo1ActionPerformed

    private void btnNuevo3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo3ActionPerformed
        lanzarDialogEstudiante(getDocente());
    }//GEN-LAST:event_btnNuevo3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNuevo1;
    private javax.swing.JButton btnNuevo2;
    private javax.swing.JButton btnNuevo3;
    private javax.swing.JComboBox<String> comboSede;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtNombres;
    // End of variables declaration//GEN-END:variables
}
