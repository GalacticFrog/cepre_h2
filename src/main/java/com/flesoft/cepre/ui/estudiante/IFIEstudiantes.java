/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.estudiante;

import com.flesoft.cepre.dao.AulaEstudianteDao;
import com.flesoft.cepre.dao.PabellonDao;
import com.flesoft.cepre.model.Aula;
import com.flesoft.cepre.model.AulaEstudiante;
import com.flesoft.cepre.model.Pabellon;
import com.flesoft.cepre.ui.util.ProgressGlassPane;
import com.flesoft.cepre.util.AppContext;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.table.TableCellRenderer;
import org.oxbow.swingbits.dialog.task.TaskDialogs;

/**
 *
 * @author edrev
 */
public class IFIEstudiantes extends javax.swing.JInternalFrame {

    private EstudianteTable tablaEstudiantes;
    private AulaEstudiante aulaestudiantetmp;

    private THCargarEstudiantes thCargarEstudiantes;
    private THPaginarEstudiantes thPaginarEstudiantes;
    private ProgressGlassPane glassPane;
    private int paginaActual = 1;

    public IFIEstudiantes() {
        initComponents();
        setGlassPane(glassPane = new ProgressGlassPane());
        //((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        anadirTablajScrollPane();
        try {
            cargarCombos();
            paginarTablaUsuarios(1);
        } catch (Exception ex) {
            getGlassPane().deactivate();
            TaskDialogs.showException(ex, "Error", "Error cargando usuarios");
            ex.printStackTrace();
        }
        //ajustarTabla();
        accionClickTabla();
        createKeybindings();
        addComboPabelloneEvent();
        addcomboBuscarEvent();
        //accionClickTabla();
    }

    private void cargarCombos() {
        PabellonDao pabellonDao = AppContext.getInstance().getBean(PabellonDao.class);

        List<Aula> aulas = new ArrayList<>();
        aulas.add(0, new Aula(0, "--TODOS--", 0, 0));

        List<Pabellon> pabellonees = pabellonDao.listar();
        pabellonees.add(0, new Pabellon(0, "--TODOS--"));
        comboAppPabellone.setModel(new DefaultComboBoxModel(pabellonees.toArray()));
        comboAppAulae.setModel(new DefaultComboBoxModel(aulas.toArray()));
    }

//    private void ajustarTabla() {
//        TableColumnAdjuster tca = new TableColumnAdjuster(getTablaEstudiantes());
//        tca.adjustColumns();
//    }
//    public void cargarEstudiantes() {
//        Pabellon selectedPab = (Pabellon) comboAppPabellone.getSelectedItem();
//        Aula selectedAula = (Aula) comboAppAulae.getSelectedItem();
//        long pabellon_id = selectedPab.getId();
//        long aula_id = selectedAula.getId();
//        filtrarTablaEstudiantes(pabellon_id, aula_id);
//    }
    public void filtrarTablaEstudiantes(long pabellon_id, long aula_id, boolean huella) {
        getGlassPane().activate();
        this.thCargarEstudiantes = new THCargarEstudiantes(tablaEstudiantes, pabellon_id, aula_id, huella, lblTotal, glassPane);
        this.thCargarEstudiantes.execute();
    }

    public void paginarTablaUsuarios(int pagina) {
        Map<String, JButton> botones = new HashMap<>();

        botones.put("btnPrimero", btnPrimero);
        botones.put("btnAnterior", btnAnterior);
        botones.put("btnSiguiente", btnSiguiente);
        botones.put("btnUltimo", btnUltimo);

        getGlassPane().activate();
        this.thPaginarEstudiantes = new THPaginarEstudiantes(tablaEstudiantes, pagina, lblPagina, lblTotal, glassPane, botones);
        this.thPaginarEstudiantes.execute();
    }

    private void anadirTablajScrollPane() {
        setTablaEstudiantes(new EstudianteTable() {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                //  Color row based on a cell value
                if (!isRowSelected(row)) {
                    c.setBackground(getBackground());
                    int modelRow = convertRowIndexToModel(row);
                    boolean type = (boolean) getModel().getValueAt(modelRow, 5);
                    if (!type) {
                        c.setBackground(new Color(217, 83, 79));
                    }
                }
                return c;
            }
        });
        jScrollPane2.setViewportView(getTablaEstudiantes());
    }

    private void addComboPabelloneEvent() {
        comboAppPabellone.addActionListener((ActionEvent event) -> {
            JComboBox comboBox = (JComboBox) event.getSource();
            Pabellon selected = (Pabellon) comboBox.getSelectedItem();
            if (selected.getId() == 0) {
                List<Aula> aulas_ = new ArrayList<>();
                aulas_.add(0, new Aula(0, "--TODOS--", 0, 0));
                comboAppAulae.setModel(new DefaultComboBoxModel(aulas_.toArray()));
            } else {
                List<Aula> aulas = selected.getAulas();
                Aula au = new Aula(0, "-- TODOS --", 0, 0);
                if (!aulas.contains(au)) {
                    aulas.add(0, au);
                }
                comboAppAulae.setModel(new DefaultComboBoxModel(aulas.toArray()));
            }
        });
    }

    private void addcomboBuscarEvent() {
        comboBuscar.addActionListener((ActionEvent event) -> {
            txtBuscarFoco();
        });
    }

    private void buscarEstudiante() {
        AulaEstudianteDao aedao = AppContext.getInstance().getBean(AulaEstudianteDao.class);
        String param = txtBuscar.getText();
        if (comboBuscar.getSelectedIndex() == 0) {
            List<AulaEstudiante> datas = aedao.listarPorDni(param);
            tablaEstudiantes.reload(datas);
            lblTotal.setText(String.valueOf(datas.size()));
        } else {
            List<AulaEstudiante> datas = aedao.listarPorApellido(param);
            tablaEstudiantes.reload(datas);
            lblTotal.setText(String.valueOf(datas.size()));
        }
    }

    private void quitarFiltros() {
        cargarCombos();
        paginarTablaUsuarios(1);
        txtBuscarFoco();
    }

    private void txtBuscarFoco() {
        txtBuscar.requestFocus();
        txtBuscar.selectAll();
    }

    private void tablaSelection() {
        tablaEstudiantes.requestFocus();
        tablaEstudiantes.changeSelection(0, 0, false, false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        comboAppPabellone = new javax.swing.JComboBox<>();
        comboAppAulae = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        checkboxHuella = new javax.swing.JCheckBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        btnAnterior = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        btnPrimero = new javax.swing.JButton();
        btnUltimo = new javax.swing.JButton();
        lblPagina = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        comboBuscar = new javax.swing.JComboBox<>();
        txtBuscar = new javax.swing.JTextField();

        setTitle("LISTADO DE ESTUDIANTES");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtrar", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        comboAppPabellone.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        comboAppAulae.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/filter-icon.png"))); // NOI18N
        jButton1.setText("Filtrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/filters-clear.png"))); // NOI18N
        jButton2.setText("Quitar filtro");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Pabellon:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Aula:");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/print.png"))); // NOI18N
        jButton3.setText("Imprimir");

        checkboxHuella.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        checkboxHuella.setText("Huella");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboAppPabellone, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboAppAulae, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(checkboxHuella)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(comboAppPabellone)
                .addComponent(jLabel3)
                .addComponent(jLabel4)
                .addComponent(comboAppAulae)
                .addComponent(jButton1)
                .addComponent(jButton2)
                .addComponent(jButton3)
                .addComponent(checkboxHuella))
        );

        jPanel2.setBackground(new java.awt.Color(229, 229, 229));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaptionBorder));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel1.setText("Total: ");

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotal.setText("total");

        btnAnterior.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnAnterior.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/rewind.png"))); // NOI18N
        btnAnterior.setText("Anterior");
        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });

        btnSiguiente.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/fast_forward.png"))); // NOI18N
        btnSiguiente.setText("Siguiente");
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });

        btnPrimero.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnPrimero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/skip_backward.png"))); // NOI18N
        btnPrimero.setText("Primero");
        btnPrimero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimeroActionPerformed(evt);
            }
        });

        btnUltimo.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnUltimo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/skip_forward.png"))); // NOI18N
        btnUltimo.setText("Ultimo");
        btnUltimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoActionPerformed(evt);
            }
        });

        lblPagina.setText("...");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("REGISTROS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPrimero)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAnterior)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUltimo)
                .addGap(6, 6, 6))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(lblTotal)
                .addComponent(btnPrimero)
                .addComponent(btnUltimo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnSiguiente)
                .addComponent(btnAnterior)
                .addComponent(lblPagina)
                .addComponent(jLabel5))
        );

        jPanel4.setBackground(new java.awt.Color(229, 229, 229));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaptionBorder));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel2.setText("Buscar por:");

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/search.png"))); // NOI18N
        jButton4.setText("Buscar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        comboBuscar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        comboBuscar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DNI", "Apellidos" }));

        txtBuscar.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jButton4)
                    .addComponent(comboBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Pabellon selectedPab = (Pabellon) comboAppPabellone.getSelectedItem();
        Aula selectedAula = (Aula) comboAppAulae.getSelectedItem();
        long pabellon_id = selectedPab.getId();
        long aula_id = selectedAula.getId();
        boolean huella = checkboxHuella.isSelected();
        filtrarTablaEstudiantes(pabellon_id, aula_id, huella);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        quitarFiltros();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        buscarEstudiante();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        //System.out.println("key code: " + evt.getKeyCode());

        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                buscarEstudiante();
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

    private void btnPrimeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimeroActionPerformed
        setPaginaActual(1);
        paginarTablaUsuarios(1);
    }//GEN-LAST:event_btnPrimeroActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        int actual = getPaginaActual() - 1;
        setPaginaActual(actual);
        paginarTablaUsuarios(actual);
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        int actual = getPaginaActual() + 1;
        System.out.println("actual:" + actual);
        setPaginaActual(actual);
        paginarTablaUsuarios(actual);
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void btnUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoActionPerformed
        AulaEstudianteDao aedao = AppContext.getInstance().getBean(AulaEstudianteDao.class);
        long total_ = aedao.totalRecords();
        int actual = (int) (Math.ceil(total_ / 100));
        setPaginaActual(actual);
        paginarTablaUsuarios(actual);
    }//GEN-LAST:event_btnUltimoActionPerformed

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

    private void lanzarDLGEstudiante() {
        AulaEstudiante au = getTablaEstudiantes().getAulaEstudianteSelected();
        if (au != null) {
            DLGEstudianteVer dialog = new DLGEstudianteVer(au, this);
            dialog.setVisible(true);
            dialog.pack();
        }
    }

    private void accionClickTabla() {

        getTablaEstudiantes().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
//                AulaEstudiante au = getTablaEstudiantes().getAulaEstudianteSelected();
//                System.out.println("Usuario: " + au);
//                setAulaestudiantetmp(au);
//                if (getAulaestudiantetmp() != null) {
//                    btnEditar.setEnabled(true);
//                }
                if (event.getClickCount() == 2) {
                    lanzarDLGEstudiante();
                }
            }
        }
        );
    }

    private void createKeybindings() {
        getTablaEstudiantes().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        getTablaEstudiantes().getActionMap().put("Enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                lanzarDLGEstudiante();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnPrimero;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JButton btnUltimo;
    private javax.swing.JCheckBox checkboxHuella;
    private javax.swing.JComboBox<Aula> comboAppAulae;
    private javax.swing.JComboBox<Pabellon> comboAppPabellone;
    private javax.swing.JComboBox<String> comboBuscar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblPagina;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables

    @Override
    public ProgressGlassPane getGlassPane() {
        return glassPane;
    }

    /**
     * @return the tablaEstudiantes
     */
    public EstudianteTable getTablaEstudiantes() {
        return tablaEstudiantes;
    }

    /**
     * @param tablaEstudiantes the tablaEstudiantes to set
     */
    public void setTablaEstudiantes(EstudianteTable tablaEstudiantes) {
        this.tablaEstudiantes = tablaEstudiantes;
    }

    /**
     * @return the aulaestudiantetmp
     */
    public AulaEstudiante getAulaestudiantetmp() {
        return aulaestudiantetmp;
    }

    /**
     * @param aulaestudiantetmp the aulaestudiantetmp to set
     */
    public void setAulaestudiantetmp(AulaEstudiante aulaestudiantetmp) {
        this.aulaestudiantetmp = aulaestudiantetmp;
    }

    /**
     * @return the paginaActual
     */
    public int getPaginaActual() {
        return paginaActual;
    }

    /**
     * @param paginaActual the paginaActual to set
     */
    public void setPaginaActual(int paginaActual) {
        this.paginaActual = paginaActual;
    }

}
