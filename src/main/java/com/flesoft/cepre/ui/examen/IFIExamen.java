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
import com.flesoft.cepre.model.Usuario;
import com.flesoft.cepre.ui.Main;
import com.flesoft.cepre.ui.estudiante.EstudianteTable;
import com.flesoft.cepre.ui.registrahuella.DLGRegistraHuella;
import com.flesoft.cepre.ui.util.TableColumnAdjuster;
import com.flesoft.cepre.util.AppContext;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.table.TableCellRenderer;
import org.oxbow.swingbits.dialog.task.TaskDialogs;

/**
 *
 * @author edrev
 */
public class IFIExamen extends javax.swing.JInternalFrame {

    private ExamenTable tablaExamenes;
    private Examen examenTmp;

    public IFIExamen() {
        initComponents();
        //((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        anadirTablajScrollPane();
        try {
            cargarExamens();
        } catch (Exception ex) {
            TaskDialogs.showException(ex, "Error", "Error cargando usuarios");
        }

        ajustarTabla();
        accionClickTabla();
    }

    private void ajustarTabla() {
        TableColumnAdjuster tca = new TableColumnAdjuster(getTablaExamenes());
        tca.adjustColumns();
    }

    public void cargarExamens() {
        ExamenDao udao = AppContext.getInstance().getBean(ExamenDao.class);
        List<Examen> examenes = udao.listar();
        refrescarTablaExamenes(examenes);
    }

    public void refrescarTablaExamenes(List<Examen> examens) {
        getTablaExamenes().reload(examens);
    }

    private void anadirTablajScrollPane() {
        setTablaExamenes(new ExamenTable() {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                //  Color row based on a cell value
                if (!isRowSelected(row)) {
                    c.setBackground(getBackground());
                    int modelRow = convertRowIndexToModel(row);
                    boolean ejecutado = getModel().getValueAt(modelRow, 2) == "SI";
                    if (ejecutado) {
                        c.setBackground(new Color(223, 240, 216));
                    }
                }
                return c;
            }
        });
        jScrollPane2.setViewportView(getTablaExamenes());
    }

    private void lanzarDLGEditar() {
        if (getExamenTmp() != null) {
            if (!getExamenTmp().isAbierto()) {
                TaskDialogs.inform(null, "Exacem cerrado!", "EXAMEN cerrado, no se puede editar.");
                return;
            }
            DLGExamen dialog = new DLGExamen();
            dialog.setExamen(getExamenTmp());
            dialog.mostrarInformacion();
            dialog.cambiarCabecera();
            dialog.setParentFrame(this);
            dialog.setVisible(true);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnEditar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        btnNuevo1 = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();

        setTitle("EXAMENES PROGRAMADOS");

        btnEditar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/edit.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setEnabled(false);
        btnEditar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Examenes programados", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );

        btnNuevo1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnNuevo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/add.png"))); // NOI18N
        btnNuevo1.setText("Nuevo ");
        btnNuevo1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNuevo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevo1ActionPerformed(evt);
            }
        });

        btnCerrar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/lock.png"))); // NOI18N
        btnCerrar.setText("Cerrar examen");
        btnCerrar.setEnabled(false);
        btnCerrar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnEditar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCerrar, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                    .addComponent(btnNuevo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnNuevo1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 205, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        lanzarDLGEditar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnNuevo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo1ActionPerformed
        DLGExamen dialog = new DLGExamen();

        dialog.setParentFrame(this);
        dialog.setVisible(true);
    }//GEN-LAST:event_btnNuevo1ActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed

    }//GEN-LAST:event_btnCerrarActionPerformed

    private void accionClickTabla() {

        getTablaExamenes().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                Examen examen = getTablaExamenes().getExamenSelected();
                // System.out.println("examen: e:" + examen.isEjecutado() + "-e" + examen.isAbierto());
                setExamenTmp(examen);
                if (getExamenTmp() != null) {
                    btnEditar.setEnabled(true);
                }
//                if (!getExamenTmp().isEjecutado() && !getExamenTmp().isAbierto()) {
//                    btnAbrir.setEnabled(true);
//                } else {
//                    btnAbrir.setEnabled(false);
//                }
                if (getExamenTmp().isAbierto()) {
                    btnCerrar.setEnabled(true);
                } else {
                    btnCerrar.setEnabled(false);
                }
                if (event.getClickCount() == 2) {
                    lanzarDLGEditar();
                }
            }
        }
        );
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnNuevo1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the tablaExamenes
     */
    public ExamenTable getTablaExamenes() {
        return tablaExamenes;
    }

    /**
     * @param tablaExamenes the tablaExamenes to set
     */
    public void setTablaExamenes(ExamenTable tablaExamenes) {
        this.tablaExamenes = tablaExamenes;
    }

    /**
     * @return the examenTmp
     */
    public Examen getExamenTmp() {
        return examenTmp;
    }

    /**
     * @param examenTmp the examenTmp to set
     */
    public void setExamenTmp(Examen examenTmp) {
        this.examenTmp = examenTmp;
    }

}
