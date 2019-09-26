/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.personal;

import com.digitalpersona.uareu.ReaderCollection;
import com.digitalpersona.uareu.UareUException;
import com.digitalpersona.uareu.UareUGlobal;
import com.flesoft.cepre.dao.CargoDao;
import com.flesoft.cepre.dao.PersonaDao;
import com.flesoft.cepre.dao.PersonalDao;
import com.flesoft.cepre.dao.SedeDao;
import com.flesoft.cepre.model.Cargo;
import com.flesoft.cepre.model.Persona;
import com.flesoft.cepre.model.Personal;
import com.flesoft.cepre.model.Sede;
import com.flesoft.cepre.ui.util.FiltroNumeros;
import com.flesoft.cepre.ui.util.MessageBox;
import com.flesoft.cepre.ui.util.UppercaseDocumentFilter;
import com.flesoft.cepre.util.AppContext;
import com.flesoft.cepre.validator.Validator;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.oxbow.swingbits.dialog.task.TaskDialogs;

/**
 *
 * @author user
 */
public class DLGRegistrar extends javax.swing.JDialog {

    private IFIPersonal parentFrame;
    private Personal personal;//usuario para mostrar en el form
    private Persona persona;
    private Validator<Personal> validador;

    public DLGRegistrar() {
        super(new java.awt.Frame(), true);
        initComponents();
        this.personal = new Personal();
        this.persona = new Persona();
        incializaTextos();
        cargarCombo();
        cargarDatosEmpleado();
        setLocationRelativeTo(null);
    }

    private void cargarDatosEmpleado() {
        DocumentListener dl = new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateFieldState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateFieldState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateFieldState();
            }

            protected void updateFieldState() {
                if (txtDni.getText().length() == 8) {
                    lblStatus.setText("Cargando...");
                    try {
                        String[] data = datos();
                        if (data[0].trim().isEmpty()) {
                            lblStatus.setText("DNI NO ENCONTRADO.");
                        }
                        txtApellidos.setText(data[0] + " " + data[1]);
                        txtNombres.setText(data[2]);
                        System.out.println("datos;" + Arrays.toString(datos()));
                        lblStatus.setText("Listo!");
                        if (data[0].trim().isEmpty()) {
                            lblStatus.setText("DNI NO ENCONTRADO.");
                        }
                    } catch (UnknownHostException uhe) {
                        lblStatus.setText("No se puede conectar a internet.");
                    } catch (IOException ex) {
                        lblStatus.setText("No se puede conectar a internet");
                    } catch (Exception ex) {
                        lblStatus.setText("No se puede conectar a internet");
                    }
                } else {
                    txtApellidos.setText("");
                    txtNombres.setText("");
                }
            }
        };
        txtDni.getDocument().addDocumentListener(dl);
    }

    private String[] datos() throws IOException {
        Document doc = Jsoup.connect("http://aplicaciones007.jne.gob.pe/srop_publico/Consulta/Afiliado/GetNombresCiudadano?DNI=" + txtDni.getText()).get();
        String data = doc.text();
        String[] datos = data.split("\\|");
        return datos;
//        System.out.println("apellidos:" + separated[0] + " " + separated[1]);
//        System.out.println("nombres:" + separated[2]);
    }

    private void incializaTextos() {
        txtDni.setDocument(new FiltroNumeros(txtDni, 8));
        DocumentFilter filter = new UppercaseDocumentFilter();
        ((AbstractDocument) txtNombres.getDocument()).setDocumentFilter(filter);
        ((AbstractDocument) txtApellidos.getDocument()).setDocumentFilter(filter);

    }

    private void cargarCombo() {
        SedeDao sedeDao = AppContext.getInstance().getBean(SedeDao.class);
        CargoDao cargoDao = AppContext.getInstance().getBean(CargoDao.class);
        List<Cargo> cargos = cargoDao.listar();
        List<Sede> sedes = sedeDao.listar();
        comboCargo.setModel(new DefaultComboBoxModel(cargos.toArray()));
        comboSede.setModel(new DefaultComboBoxModel(sedes.toArray()));
    }

    private Personal getPersonalDLG() {
        Persona persona_ = getPersona();

        persona_.setApellidos(txtApellidos.getText());
        persona_.setNombres(txtNombres.getText());
        persona_.setDni(txtDni.getText());
        persona_.setDireccion(txtDireccion.getText());
        persona_.setTelefono(txtTelefono.getText());

        Personal personal_ = getPersonal();
        //       docente_.setPersonal(txtDni.getText());
//        String _txtpass1 = String.copyValueOf(txtPass1.getPassword());
        personal_.setPersona(persona_);
        Sede sede = (Sede) comboSede.getSelectedItem();
        personal_.setSede(sede);
        Cargo cargo = (Cargo) comboCargo.getSelectedItem();
        personal_.setCargo(cargo);

        return personal_;
    }

    private void guardarEmpleado() {
        try {
            PersonalDao uDao = AppContext.getInstance().getBean(PersonalDao.class);
            PersonaDao pDao = AppContext.getInstance().getBean(PersonaDao.class);
            //validar si ya existe
            String dni_ = txtDni.getText();
            List<Persona> persona_ = pDao.buscar(dni_);
            if (persona_.size() > 0) {
                JOptionPane.showMessageDialog(this, " el DNI: " + txtDni.getText() + " YA ESTA REGISTRADO.", "AVISO", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Personal docente_ = getPersonalDLG();
            docente_.setCondicion("Contratado");
            validador = new PersonalValidator();
            String msg = validador.validate(docente_);

            if (!"".equals(msg == null ? "" : msg)) {
                TaskDialogs.warning(this, "Corrija los siguientes errores e intentelo nuevamente!", msg);
                return;
            }

            Personal nuevo = uDao.insertar(docente_);
            int dialogResult = JOptionPane.showConfirmDialog(null, "Se cre\u00f3 un nuevo registro con los datos proporcionados.\n"
                    + "Desea capturar la huella ahora?",
                    "Capturar huella?",
                    JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                accionCerrarVentana();
                lanzarDialogEstudiante(nuevo);
            } else {
                accionCerrarVentana();
            }
            // TaskDialogs.inform(this, "Guardado correctamente", "Se cre\u00f3 un nuevo registro con los datos proporcionados.");

            getParentFrame().cargarPersonal();

        } catch (Exception ex) {
            TaskDialogs.showException(ex, "ERROR", "NO SE PUDO GUARDAR");
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

    private void lanzarDialogEstudiante(Personal personal) {
        if (!checkReader()) {
            return;
        }
        DLGRegistraHuella.Run(personal);

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
    public Personal getPersonal() {
        return personal;
    }

    /**
     * @param personal the personal to set
     */
    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    /**
     * @return the parentFrame
     */
    public IFIPersonal getParentFrame() {
        return parentFrame;
    }

    /**
     * @param parentFrame the parentFrame to set
     */
    public void setParentFrame(IFIPersonal parentFrame) {
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
        jLabel15 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        comboCargo = new javax.swing.JComboBox<>();
        lblStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.SystemColor.activeCaptionBorder));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/40/icons8-EditUser-40.png"))); // NOI18N

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTitulo.setText("Agregar Nuevo personal.");

        jLabel13.setText("Ingrese los datos  a continuacion.");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
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

        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.SystemColor.activeCaptionBorder));

        txtApellidos.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N

        txtNombres.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel6.setText("NOMBRES:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel7.setText("APELLIDO:");

        txtDni.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel14.setText("DNI:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel10.setText("SEDE:");

        comboSede.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel15.setText("DIRECCION:");

        txtDireccion.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel16.setText("TELEFONO:");

        txtTelefono.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel11.setText("CARGO:");

        comboCargo.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboCargo, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTelefono)
                    .addComponent(comboSede, 0, 300, Short.MAX_VALUE)
                    .addComponent(txtDireccion)
                    .addComponent(txtNombres)
                    .addComponent(txtApellidos)
                    .addComponent(txtDni))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNombres, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11)
                    .addComponent(comboCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboSede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblStatus.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/icons8-light-on-16.png"))); // NOI18N
        lblStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(lblStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnNuevo1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNuevo2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblStatus, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo2ActionPerformed
        guardarEmpleado();
    }//GEN-LAST:event_btnNuevo2ActionPerformed

    private void btnNuevo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo1ActionPerformed
        accionCerrarVentana();        // TODO add your handling code here:
    }//GEN-LAST:event_btnNuevo1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNuevo1;
    private javax.swing.JButton btnNuevo2;
    private javax.swing.JComboBox<String> comboCargo;
    private javax.swing.JComboBox<String> comboSede;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtApellidos;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDni;
    private javax.swing.JTextField txtNombres;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
