/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.usuario;

import com.flesoft.cepre.dao.UsuarioDao;
import com.flesoft.cepre.model.Usuario;
import com.flesoft.cepre.ui.Main;
import com.flesoft.cepre.ui.registrahuella.DLGRegistraHuella;
import com.flesoft.cepre.ui.util.TableColumnAdjuster;
import com.flesoft.cepre.util.AppContext;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import org.oxbow.swingbits.dialog.task.TaskDialogs;

/**
 *
 * @author edrev
 */
public class IFUsuarios extends javax.swing.JInternalFrame {

    private UsuarioTable tablaUsuarios = new UsuarioTable();
    private Usuario usuariotmp;
    private Main mainFrame;

    public IFUsuarios() {
        initComponents();
        anadirTablajScrollPane();
        try {
            cargarUsuarios();
        } catch (Exception ex) {
            TaskDialogs.showException(ex, "Error", "Error cargando usuarios");
        }

        ajustarTabla();
        accionClickTabla();
    }

    private void ajustarTabla() {
        TableColumnAdjuster tca = new TableColumnAdjuster(tablaUsuarios);
        tca.adjustColumns();
    }

    public void cargarUsuarios() {
        UsuarioDao udao = AppContext.getInstance().getBean(UsuarioDao.class);
        List<Usuario> usuarios = udao.listar();
        refrescarTablaUsuarios(usuarios);
    }

    public void refrescarTablaUsuarios(List<Usuario> usuarios) {
        getTablaUsuarios().reload(usuarios);
    }

    private void anadirTablajScrollPane() {
        jScrollPane2.setViewportView(getTablaUsuarios());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setClosable(true);
        setMaximizable(true);
        setTitle("USUARIOS DEL SISTEMA");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.SystemColor.activeCaptionBorder));

        lblTitulo.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTitulo.setText("Usuarios del sistema");

        jLabel13.setText("Usuarios con acceso al sistema.");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/40/icons8-Users-40.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTitulo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 433, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(lblTitulo)
                .addGap(3, 3, 3)
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addContainerGap())
        );

        btnNuevo.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/add.png"))); // NOI18N
        btnNuevo.setText("Nuevo ");
        btnNuevo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnEditar.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/edit.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setEnabled(false);
        btnEditar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/trash.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.setEnabled(false);
        btnEliminar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnEditar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnNuevo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 218, Short.MAX_VALUE))
                    .addComponent(jSeparator1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if (getUsuariotmp() != null) {
            DLGUsuarioEditar dialog = new DLGUsuarioEditar(getUsuariotmp());
            dialog.setParentFrame(this);//nullpointerException
            dialog.setVisible(true);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        DLGUsuarioRegistrar dialog = new DLGUsuarioRegistrar();
        dialog.setParentFrame(this);
        dialog.setVisible(true);

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void accionClickTabla() {

        getTablaUsuarios().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                Usuario usuario = getTablaUsuarios().getUsuarioSelected();
                System.out.println("Usuario: " + usuario);
                setUsuariotmp(usuario);
                if (usuariotmp != null) {
                    btnEditar.setEnabled(true);
                }
                if (event.getClickCount() == 2) {
                    if (usuario != null) {
                        DLGUsuarioEditar dialog = new DLGUsuarioEditar(usuario);
                        dialog.setParentFrame(getJIframe());
                        //dialog.setUsuario(usuario);//nullpointerException
                        dialog.setVisible(true);
                    }
                }
            }
        }
        );
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the usuariotmp
     */
    public Usuario getUsuariotmp() {
        return usuariotmp;
    }

    /**
     * @param usuariotmp the usuariotmp to set
     */
    public void setUsuariotmp(Usuario usuariotmp) {
        this.usuariotmp = usuariotmp;
    }

    /**
     * @return the tablaUsuarios
     */
    public UsuarioTable getTablaUsuarios() {
        return tablaUsuarios;
    }

    /**
     * @param tablaUsuarios the tablaUsuarios to set
     */
    public void setTablaUsuarios(UsuarioTable tablaUsuarios) {
        this.tablaUsuarios = tablaUsuarios;
    }

    public IFUsuarios getJIframe() {
        return this;
    }

    /**
     * @return the mainFrame
     */
    public Main getMainFrame() {
        return mainFrame;
    }

    /**
     * @param mainFrame the mainFrame to set
     */
    public void setMainFrame(Main mainFrame) {
        this.mainFrame = mainFrame;
    }
}
