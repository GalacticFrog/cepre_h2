/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.personal;

import com.flesoft.cepre.dao.AulaEstudianteDao;
import com.flesoft.cepre.dao.DocenteDao;
import com.flesoft.cepre.dao.PersonalDao;
import com.flesoft.cepre.model.AulaEstudiante;
import com.flesoft.cepre.model.Docente;
import com.flesoft.cepre.model.Personal;
import com.flesoft.cepre.model.Usuario;
import com.flesoft.cepre.ui.Main;
import com.flesoft.cepre.ui.util.RowNumberTable;
import com.flesoft.cepre.ui.util.TableColumnAdjuster;
import com.flesoft.cepre.util.AppContext;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.TableCellRenderer;
import org.oxbow.swingbits.dialog.task.TaskDialogs;

/**
 *
 * @author edrev
 */
public class IFIPersonal extends javax.swing.JInternalFrame {

    private PersonalTable tablaUsuarios = new PersonalTable();
    private Personal usuariotmp;
    private Main mainFrame;
    //digitalpersona

    public IFIPersonal() {
        initComponents();
        anadirTablajScrollPane();
        try {
            cargarPersonal();
        } catch (Exception ex) {
            TaskDialogs.showException(ex, "Error", "Error cargando usuarios");
            ex.printStackTrace();
        }

        ajustarTabla();
        accionClickTabla();
        createKeybindings();
        createKeybindings2();
    }

    private void ajustarTabla() {
        TableColumnAdjuster tca = new TableColumnAdjuster(tablaUsuarios);
        tca.adjustColumns();
    }

    public void cargarPersonal() {
        PersonalDao udao = AppContext.getInstance().getBean(PersonalDao.class);
        List<Personal> personal = udao.listar();
        refrescarTablaUsuarios(personal);
    }

    public void refrescarTablaUsuarios(List<Personal> docentes) {
        getTablaUsuarios().reload(docentes);
    }

    private void anadirTablajScrollPane() {
        setTablaUsuarios(new PersonalTable() {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                //  Color row based on a cell value
                if (!isRowSelected(row)) {
                    c.setBackground(getBackground());
                    int modelRow = convertRowIndexToModel(row);
                    String type = (String) getModel().getValueAt(modelRow, 5);
                    if (!type.equals("Registrado")) {
                        c.setBackground(new Color(217, 83, 79));
                    }
                }
                return c;
            }
        });
        jScrollPane2.setViewportView(getTablaUsuarios());
        JTable rowTable = new RowNumberTable(getTablaUsuarios());
        jScrollPane2.setRowHeaderView(rowTable);
        jScrollPane2.setCorner(JScrollPane.UPPER_LEFT_CORNER,
                rowTable.getTableHeader());
    }

    private void buscarEmpleado() {
        PersonalDao udao = AppContext.getInstance().getBean(PersonalDao.class);
        String param = txtBuscar.getText();

        List<Personal> datas = udao.buscarPorApellidos(param);
        getTablaUsuarios().reload(datas);
        System.out.println("::" + datas);

    }

    private void quitarFiltros() {
        cargarPersonal();
        txtBuscarFoco();
    }

    private void txtBuscarFoco() {
        txtBuscar.requestFocus();
        txtBuscar.selectAll();
    }

    private void tablaSelection() {
        getTablaUsuarios().requestFocus();
        getTablaUsuarios().changeSelection(0, 0, false, false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setTitle("LISTADO DE PERSONAL/DOCENTE");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.SystemColor.activeCaptionBorder));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel2.setText("Buscar:");

        txtBuscar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/search.png"))); // NOI18N
        jButton4.setText("Buscar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addContainerGap(490, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jButton4)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEditar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 176, Short.MAX_VALUE))
                    .addComponent(jScrollPane2)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if (getUsuariotmp() != null) {
            DLGEditar dialog = new DLGEditar(getUsuariotmp());
            dialog.setParentFrame(this);//nullpointerException
            dialog.setVisible(true);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        DLGRegistrar dialog = new DLGRegistrar();
        dialog.setParentFrame(this);
        dialog.setVisible(true);

    }//GEN-LAST:event_btnNuevoActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        System.out.println("key code: " + evt.getKeyCode());

        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                buscarEmpleado();
                break;
            case KeyEvent.VK_ESCAPE:
                quitarFiltros();
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
                tablaSelection();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_txtBuscarKeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        buscarEmpleado();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void accionClickTabla() {

        getTablaUsuarios().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                Personal docente = getTablaUsuarios().getUsuarioSelected();
                System.out.println("Usuario: " + docente);
                setUsuariotmp(docente);
                if (usuariotmp != null) {
                    btnEditar.setEnabled(true);
                }
                if (event.getClickCount() == 2) {
                    if (docente != null) {
                        DLGEditar dialog = new DLGEditar(docente);
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
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the usuariotmp
     */
    public Personal getUsuariotmp() {
        return usuariotmp;
    }

    /**
     * @param usuariotmp the usuariotmp to set
     */
    public void setUsuariotmp(Personal usuariotmp) {
        this.usuariotmp = usuariotmp;
    }

    /**
     * @return the tablaUsuarios
     */
    public PersonalTable getTablaUsuarios() {
        return tablaUsuarios;
    }

    /**
     * @param tablaUsuarios the tablaUsuarios to set
     */
    public void setTablaUsuarios(PersonalTable tablaUsuarios) {
        this.tablaUsuarios = tablaUsuarios;
    }

    public IFIPersonal getJIframe() {
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

    @Override
    protected JRootPane createRootPane() {
        JRootPane rootPane_ = new JRootPane();
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        Action actionListener = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                quitarFiltros();
            }
        };
        InputMap inputMap = rootPane_.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputMap.put(stroke, "ESCAPE");
        rootPane_.getActionMap().put("ESCAPE", actionListener);

        return rootPane_;
    }

    private void createKeybindings() {
        getTablaUsuarios().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        getTablaUsuarios().getActionMap().put("Enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Personal docente = getTablaUsuarios().getUsuarioSelected();

                if (docente != null) {
                    DLGEditar dialog = new DLGEditar(docente);
                    dialog.setParentFrame(getJIframe());
                    //dialog.setUsuario(usuario);//nullpointerException
                    dialog.setVisible(true);
                }

            }
        });
    }

    private void createKeybindings2() {
        getTablaUsuarios().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "AA");
        getTablaUsuarios().getActionMap().put("AA", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("aaaa presed");
                Personal _personal = getTablaUsuarios().getUsuarioSelected();
                DLGRegistraAsistencia_back1 dialog = new DLGRegistraAsistencia_back1(_personal, null);
                dialog.setVisible(true);
//                Personal docente = getTablaUsuarios().getUsuarioSelected();
//
//                if (docente != null) {
//                    DLGEditar dialog = new DLGEditar(docente);
//                    dialog.setParentFrame(getJIframe());
//                    //dialog.setUsuario(usuario);//nullpointerException
//                    dialog.setVisible(true);
//                }
            }
        });
    }
}
