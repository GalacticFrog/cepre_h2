/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.examen;

import com.flesoft.cepre.dao.ExamenDao;
import com.flesoft.cepre.ui.usuario.*;
import com.flesoft.cepre.dao.UsuarioDao;
import com.flesoft.cepre.model.Examen;
import com.flesoft.cepre.validator.Validator;
import com.flesoft.cepre.model.Persona;
import com.flesoft.cepre.model.Usuario;
import com.flesoft.cepre.ui.util.DateUtils;
import com.flesoft.cepre.ui.util.FiltroNumeros;
import com.flesoft.cepre.ui.util.UppercaseDocumentFilter;
import com.flesoft.cepre.util.AppContext;
import com.flesoft.cepre.util.ShiroUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
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
public class DLGExamen extends javax.swing.JDialog {

    private Examen examen;
    private IFIExamen parentFrame;
    private Validator<Examen> validador;

    public DLGExamen() {
        super(new java.awt.Frame(), true);
        initComponents();
        setLocationRelativeTo(null);
    }

    private void accionCerrarVentana() {
        setVisible(false);
        dispose();
    }

    public void mostrarInformacion() {
        txtNombreExamen.setText(getExamen().getNombre());
        LocalDate fecha = DateUtils.asLocalDate(getExamen().getFecha());
        fechaExamen.setDate(fecha);
    }

    public void cambiarCabecera() {
        lblTitulo.setText("Modificar EXAMEN");
        lblDescripcion.setText("Ingrese la informacion del examen a continuacion.");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNombreExamen = new javax.swing.JTextField();
        fechaExamen = new com.github.lgooddatepicker.components.DatePicker();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblDescripcion = new javax.swing.JLabel();
        btnNuevo1 = new javax.swing.JButton();
        btnNuevo2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.SystemColor.activeCaptionBorder));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel6.setText("NOMBRE:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel7.setText("FECHA EXAMEN:");

        txtNombreExamen.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N

        fechaExamen.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNombreExamen)
                .addGap(10, 10, 10))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fechaExamen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNombreExamen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fechaExamen, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.SystemColor.activeCaptionBorder));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/48/admissions.png"))); // NOI18N

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitulo.setText("Agregar Nuevo EXAMEN");

        lblDescripcion.setText("Ingrese la informacion del examen a continuacion.");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo)
                    .addComponent(lblDescripcion))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblDescripcion)))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNuevo1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNuevo2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevo2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private Examen getExamenDLG() {
        if (getExamen() == null) {
            Examen ex = new Examen();
            ex.setNombre(txtNombreExamen.getText());
            Date fecha = DateUtils.asDate(fechaExamen.getDate());
            ex.setFecha(fecha);
            ex.setAbierto(true);
            return ex;
        } else {
            Examen ex_ = getExamen();
            ex_.setNombre(txtNombreExamen.getText());
            Date fecha = DateUtils.asDate(fechaExamen.getDate());
            ex_.setFecha(fecha);
            return ex_;
        }
    }

    private void guardarExamen() {
        try {
            ExamenDao uDao = AppContext.getInstance().getBean(ExamenDao.class);
            Examen examen_ = getExamenDLG();
            validador = new ExamenValidator();
            String msg = validador.validate(examen_);
            if (fechaExamen.getComponentDateTextField().getText().isEmpty()) {
                msg = msg + "La fecha es obligatoria.\n";
            }
            if (!"".equals(msg == null ? "" : msg)) {
                TaskDialogs.warning(this, "Corrija los siguientes errores e intentelo nuevamente!", msg);
                return;
            }
            uDao.guardar(examen_);

            TaskDialogs.inform(this, "CORRECTO!", "EXAMEN guardado correctamente.");
            getParentFrame().cargarExamens();
            accionCerrarVentana();
        } catch (Exception ex) {
            TaskDialogs.showException(ex, "ERROR", "NO SE PUDO GUARDAR EL EXAMEN");
            ex.printStackTrace();
        }
    }
    private void btnNuevo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo1ActionPerformed
        accionCerrarVentana();
    }//GEN-LAST:event_btnNuevo1ActionPerformed

    private void btnNuevo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo2ActionPerformed
        guardarExamen();
    }//GEN-LAST:event_btnNuevo2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNuevo1;
    private javax.swing.JButton btnNuevo2;
    private com.github.lgooddatepicker.components.DatePicker fechaExamen;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtNombreExamen;
    // End of variables declaration//GEN-END:variables

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

    /**
     * @return the parentFrame
     */
    public IFIExamen getParentFrame() {
        return parentFrame;
    }

    /**
     * @param parentFrame the parentFrame to set
     */
    public void setParentFrame(IFIExamen parentFrame) {
        this.parentFrame = parentFrame;
    }

}
