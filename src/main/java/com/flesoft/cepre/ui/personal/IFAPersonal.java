/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.personal;

import com.flesoft.cepre.dao.AsistenciaPersonalDao;
import com.flesoft.cepre.dao.CargoDao;
import com.flesoft.cepre.dao.ExamenDao;
import com.flesoft.cepre.dao.SedeDao;
import com.flesoft.cepre.model.AsistenciaPersonal;
import com.flesoft.cepre.model.Cargo;
import com.flesoft.cepre.model.Examen;
import com.flesoft.cepre.model.Sede;
import com.flesoft.cepre.ui.Main;
import com.flesoft.cepre.ui.util.ProgressGlassPane;
import com.flesoft.cepre.ui.util.ReportViewer;
import com.flesoft.cepre.ui.util.RowNumberTable;
import com.flesoft.cepre.util.AppContext;
import com.flesoft.cepre.util.Config;
import demo.FrmContainer;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.oxbow.swingbits.dialog.task.TaskDialogs;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author edrev
 */
public class IFAPersonal extends javax.swing.JInternalFrame {

    private APersonalTable tablaAsistencia = new APersonalTable();
    private AsistenciaPersonal asistenciaTmp;
    private Main mainFrame;
    private ProgressGlassPane glassPane;
    private THPaginarAPersonal thPaginarAExamen;
    private THFiltrarAPersonal thFiltrarAExamen;
    private int paginaActual = 1;
    //private List<PabellonE> pabellonees = new ArrayList<>();

    public IFAPersonal() {
        initComponents();
        setGlassPane(glassPane = new ProgressGlassPane());
        //((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        anadirTablajScrollPane();
        try {
            //cargarAsistencias();
            //paginarTablaAPersonal(1);
            cargarCombos();

            createKeybindings();
            createKeybindingsSpr();
            accionClickTabla();
        } catch (Exception ex) {
            TaskDialogs.showException(ex, "Error", "Error cargando asistencias");
        }
        //ajustarTabla();

        addCombosFiltrosEvent();
    }

    private void cargarCombos() {
        ExamenDao examenDao = AppContext.getInstance().getBean(ExamenDao.class);
        CargoDao cargoDao = AppContext.getInstance().getBean(CargoDao.class);
        List<Examen> examenes = examenDao.listar();
        List<Cargo> cargos = cargoDao.listar();
        comboExamenes.setModel(new DefaultComboBoxModel(examenes.toArray()));
        Cargo au = new Cargo(0, "-- TODOS --");
        if (!cargos.contains(au)) {
            cargos.add(0, au);
        }
        comboCargo.setModel(new DefaultComboBoxModel(cargos.toArray()));
    }

//    public void filtrarTablaAExamen(long pabellon_id, long aula_id) {
//        getGlassPane().activate();
//        this.thCargarEstudiantes = new THCargarEstudiantes(tablaAsistencia, pabellon_id, aula_id, lblTotal, glassPane);
//        this.thCargarEstudiantes.execute();
//    }
    public void paginarTablaAPersonal(int pagina) {
        Map<String, JButton> botones = new HashMap<>();
        botones.put("btnPrimero", btnPrimero);
        botones.put("btnAnterior", btnAnterior);
        botones.put("btnSiguiente", btnSiguiente);
        botones.put("btnUltimo", btnUltimo);
        getGlassPane().activate();
        this.thPaginarAExamen = new THPaginarAPersonal(tablaAsistencia, pagina, lblPagina, lblTotal, glassPane, botones);
        this.thPaginarAExamen.execute();
    }

    public void filtrarTablaAExamen() {

        Examen exx = (Examen) comboExamenes.getSelectedItem();
        Cargo _cargo = (Cargo) comboCargo.getSelectedItem();
        long examen_id = exx.getId();
        long cargo_id = _cargo.getId();

        getGlassPane().activate();
        this.thFiltrarAExamen = new THFiltrarAPersonal(examen_id, cargo_id, tablaAsistencia, lblTotal, glassPane, btnVistaPrevia);
        this.thFiltrarAExamen.execute();
        disableBtnPaginar();
    }

    private void disableBtnPaginar() {
        btnPrimero.setEnabled(false);
        btnAnterior.setEnabled(false);
        btnSiguiente.setEnabled(false);
        btnUltimo.setEnabled(false);
        lblPagina.setText("1/1");
    }
//    private void ajustarTabla() {
//        TableColumnAdjuster tca = new TableColumnAdjuster(tablaAsistencia);
//        tca.adjustColumns();
//    }
//    public void cargarAsistencias() {
//        AsistenciaExamenDao aexDao = AppContext.getInstance().getBean(AsistenciaExamenDao.class);
//        List<AsistenciaExamen> asistencias = aexDao.listar();
//        refrescarTablaUsuarios(asistencias);
//    }
//    public void refrescarTablaUsuarios(List<AsistenciaExamen> asistencias) {
//        getTablaUsuarios().reload(asistencias);
//    }

    private void anadirTablajScrollPane() {
//        tablaAsistencia.setRowSelectionAllowed(true);
//        tablaAsistencia.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jScrollPane2.setViewportView(getTablaUsuarios());

        JTable rowTable = new RowNumberTable(getTablaUsuarios());
        jScrollPane2.setRowHeaderView(rowTable);
        jScrollPane2.setCorner(JScrollPane.UPPER_LEFT_CORNER,
                rowTable.getTableHeader());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        comboExamenes = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        btnVistaPrevia = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        comboCargo = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        btnAnterior = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        btnPrimero = new javax.swing.JButton();
        btnUltimo = new javax.swing.JButton();
        lblPagina = new javax.swing.JLabel();

        setTitle("REPORTE ASISTENCIA");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Asistencia personal", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtros", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

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

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Examen:");

        btnVistaPrevia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/print.png"))); // NOI18N
        btnVistaPrevia.setText("Imprimir");
        btnVistaPrevia.setEnabled(false);
        btnVistaPrevia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVistaPreviaActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Cargo:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboExamenes, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVistaPrevia)
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel5)
                .addComponent(comboExamenes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnVistaPrevia)
                .addComponent(jLabel6)
                .addComponent(comboCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBackground(new java.awt.Color(229, 229, 229));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaptionBorder));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel1.setText("Total: ");

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotal.setText("total");

        btnAnterior.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnAnterior.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/rewind.png"))); // NOI18N
        btnAnterior.setText("Anterior");
        btnAnterior.setEnabled(false);
        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });

        btnSiguiente.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/fast_forward.png"))); // NOI18N
        btnSiguiente.setText("Siguiente");
        btnSiguiente.setEnabled(false);
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });

        btnPrimero.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnPrimero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/skip_backward.png"))); // NOI18N
        btnPrimero.setText("Primero");
        btnPrimero.setEnabled(false);
        btnPrimero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimeroActionPerformed(evt);
            }
        });

        btnUltimo.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        btnUltimo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/skip_forward.png"))); // NOI18N
        btnUltimo.setText("Ultimo");
        btnUltimo.setEnabled(false);
        btnUltimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoActionPerformed(evt);
            }
        });

        lblPagina.setText("...");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(lblTotal)
                .addComponent(btnPrimero)
                .addComponent(btnUltimo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnSiguiente)
                .addComponent(btnAnterior)
                .addComponent(lblPagina))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVistaPreviaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVistaPreviaActionPerformed
        try {
            SedeDao sedeDao = AppContext.getInstance().getBean(SedeDao.class);
            DriverManagerDataSource dataSource = AppContext.getInstance().getBean("externalDataSource", DriverManagerDataSource.class);
            Connection connection = dataSource.getConnection();

//            BasicDataSource dataSource = AppContext.getInstance().getBean(BasicDataSource.class);
//            Connection connection = dataSource.getConnection();
            Sede sede_ = sedeDao.buscarPorId(Config.SEDE);
            Examen selectedEx = (Examen) comboExamenes.getSelectedItem();
            Cargo _cargo = (Cargo) comboCargo.getSelectedItem();
            int examen_id = (int) selectedEx.getId(); //bug 0251

            String dir_huellas = Config.DIR_HUELLAS;
            String LOGO = "img/logo.jpg";
            HashMap param = new HashMap();
            param.put("urlLogo", LOGO);
            param.put("dir_huellas", dir_huellas);
            param.put("examen_id", examen_id);//examen id para el select
            param.put("ciclo_academico", Config.CICLO);//huamanga huanta etc
            param.put("sede", sede_.getNombreSede().toUpperCase());//huamanga huanta etc
            param.put("examen", selectedEx.getNombre());//examen id
            param.put("cargo_id", _cargo.getId());

//            if (pabellone_id != 0 && aulae_id == 0) {
//                //List<AsistenciaExamen> datas = aexDao.listarPorExamen(examen_id, pabellone_id);
//                String condicion = "au_e.pabellone_id =" + pabellone_id + " AND as_ex.examen_id=" + examen_id + " ";
//                param.put("condicion", condicion);
//            }
//            if (pabellone_id != 0 && aulae_id != 0) {
//                //List<AsistenciaExamen> datas = aexDao.listarPorExamenAula(examen_id, aulae_id);
//                String condicion = "au_e.id =" + aulae_id + " AND as_ex.examen_id=" + examen_id + " ";
//                param.put("condicion", condicion);
//            }
            System.out.println("param: " + param);
            JasperPrint jasperPrint = JasperFillManager.fillReport("reportes/reporteAPersonal.jasper", param, connection);
//            JasperViewer.viewReport(jasperPrint, false);
            ReportViewer dialog = new ReportViewer(jasperPrint);
            dialog.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(FrmContainer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FrmContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnVistaPreviaActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        int actual = getPaginaActual() - 1;
        setPaginaActual(actual);
        paginarTablaAPersonal(actual);
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        int actual = getPaginaActual() + 1;
        System.out.println("actual:" + actual);
        setPaginaActual(actual);
        paginarTablaAPersonal(actual);
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void btnPrimeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimeroActionPerformed
        setPaginaActual(1);
        paginarTablaAPersonal(1);
    }//GEN-LAST:event_btnPrimeroActionPerformed

    private void btnUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoActionPerformed
        AsistenciaPersonalDao aexdao = AppContext.getInstance().getBean(AsistenciaPersonalDao.class);
        long total_ = aexdao.totalRecords();
        int actual = (int) (Math.ceil(((double) total_) / 100));
        setPaginaActual(actual);
        paginarTablaAPersonal(actual);
    }//GEN-LAST:event_btnUltimoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        filtrarTablaAExamen();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        cargarCombos();
        paginarTablaAPersonal(1);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void createKeybindings() {
        getTablaUsuarios().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        getTablaUsuarios().getActionMap().put("Enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("aaaa presed");
                AsistenciaPersonal A_personal = getTablaUsuarios().getAsistenciaExamenSelected();
                //Personal _personal = A_personal.getPersonal();
                Examen exx = (Examen) comboExamenes.getSelectedItem();
                DLGRegistraAsistencia_back dialog = new DLGRegistraAsistencia_back(A_personal, exx);
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

    private void createKeybindingsSpr() {
        getTablaUsuarios().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "Delete");
        getTablaUsuarios().getActionMap().put("Delete", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("aaaa delete");
                int dialogResult = JOptionPane.showConfirmDialog(null, "Seguro que desea eliminar.\n"
                        + "Esta accion no se puede deshacer?",
                        "Eliminar registro?",
                        JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    System.out.println("eliminando...");
                    AsistenciaPersonalDao aexdao = AppContext.getInstance().getBean(AsistenciaPersonalDao.class);
                    AsistenciaPersonal _aPersonal = getTablaUsuarios().getAsistenciaExamenSelected();
                    aexdao.eliminar(_aPersonal);
                }
//                else {
//
//                }
//                AsistenciaPersonal A_personal = getTablaUsuarios().getAsistenciaExamenSelected();
//                Personal _personal = A_personal.getPersonal();
//                Examen exx = (Examen) comboExamenes.getSelectedItem();
//                DLGRegistraAsistencia_back dialog = new DLGRegistraAsistencia_back(_personal, exx);
//                dialog.setVisible(true);
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

    private void accionClickTabla() {

        getTablaUsuarios().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                AsistenciaPersonal usuario = getTablaUsuarios().getAsistenciaExamenSelected();
                System.out.println("Usuario: " + usuario);
                setUsuariotmp(usuario);
//                if (asistenciaTmp != null) {
//                    btnEditar.setEnabled(true);
//                }
                if (event.getClickCount() == 2) {
                    if (usuario != null) {
                        //Personal _personal = getUsuariotmp().getPersonal();
                        Examen exx = (Examen) comboExamenes.getSelectedItem();
                        DLGRegistraAsistencia_back dialog = new DLGRegistraAsistencia_back(getUsuariotmp(), exx);
                        dialog.setVisible(true);
                    }
                }
            }
        }
        );
    }

    private void addCombosFiltrosEvent() {
        comboExamenes.addActionListener((ActionEvent event) -> {
            JComboBox comboBox = (JComboBox) event.getSource();
            //System.out.println("changed 1");
            btnVistaPrevia.setEnabled(false);
        });
        comboCargo.addActionListener((ActionEvent event) -> {
            JComboBox comboBox = (JComboBox) event.getSource();
            //System.out.println("changed 2s");
            btnVistaPrevia.setEnabled(false);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnPrimero;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JButton btnUltimo;
    private javax.swing.JButton btnVistaPrevia;
    private javax.swing.JComboBox<Examen> comboCargo;
    private javax.swing.JComboBox<Examen> comboExamenes;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblPagina;
    private javax.swing.JLabel lblTotal;
    // End of variables declaration//GEN-END:variables
    @Override
    public ProgressGlassPane getGlassPane() {
        return glassPane;
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

    /**
     * @return the usuariotmp
     */
    public AsistenciaPersonal getUsuariotmp() {
        return asistenciaTmp;
    }

    /**
     * @param usuariotmp the usuariotmp to set
     */
    public void setUsuariotmp(AsistenciaPersonal usuariotmp) {
        this.asistenciaTmp = usuariotmp;
    }

    /**
     * @return the tablaUsuarios
     */
    public APersonalTable getTablaUsuarios() {
        //tablaAsistencia.setAutoCreateRowSorter(true);
        return tablaAsistencia;
    }

    /**
     * @param tablaAsistencia the tablaUsuarios to set
     */
    public void setTablaUsuarios(APersonalTable tablaAsistencia) {
        this.tablaAsistencia = tablaAsistencia;
    }

    public IFAPersonal getJIframe() {
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
