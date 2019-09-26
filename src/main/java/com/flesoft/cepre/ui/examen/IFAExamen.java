/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.examen;

import com.flesoft.cepre.dao.AsistenciaExamenDao;
import com.flesoft.cepre.dao.ExamenDao;
import com.flesoft.cepre.dao.PabelloneDao;
import com.flesoft.cepre.dao.SedeDao;
import com.flesoft.cepre.model.AsistenciaExamen;
import com.flesoft.cepre.model.AulaE;
import com.flesoft.cepre.model.Examen;
import com.flesoft.cepre.model.PabellonE;
import com.flesoft.cepre.model.Sede;
import com.flesoft.cepre.ui.Main;
import com.flesoft.cepre.ui.util.ProgressGlassPane;
import com.flesoft.cepre.util.AppContext;
import com.flesoft.cepre.util.Config;
import demo.FrmContainer;
import com.flesoft.cepre.ui.util.ReportViewer;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.dbcp2.BasicDataSource;
import org.oxbow.swingbits.dialog.task.TaskDialogs;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 *
 * @author edrev
 */
public class IFAExamen extends javax.swing.JInternalFrame {

    private AExamenTable tablaAsistencia = new AExamenTable();
    private AsistenciaExamen asistenciaTmp;
    private Main mainFrame;
    private ProgressGlassPane glassPane;
    private THPaginarAExamen thPaginarAExamen;
    private THFiltrarAExamen thFiltrarAExamen;
    private int paginaActual = 1;
    //private List<PabellonE> pabellonees = new ArrayList<>();

    public IFAExamen() {
        initComponents();
        setGlassPane(glassPane = new ProgressGlassPane());
        // ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        anadirTablajScrollPane();
        try {
            //cargarAsistencias();
            //paginarTablaAExamen(1);
            cargarCombos();
        } catch (Exception ex) {
            TaskDialogs.showException(ex, "Error", "Error cargando asistencias");
        }
        //ajustarTabla();
        addComboPabelloneEvent();
        // accionClickTabla();
    }

    private void cargarCombos() {

        //CicloDao cicloDao = AppContext.getInstance().getBean(CicloDao.class);
        SedeDao sedeDao = AppContext.getInstance().getBean(SedeDao.class);
        PabelloneDao pabelloneDao = AppContext.getInstance().getBean(PabelloneDao.class);
//        AulaEDao aulaEDao = AppContext.getInstance().getBean(AulaEDao.class);
        ExamenDao examenDao = AppContext.getInstance().getBean(ExamenDao.class);
        //List<Ciclo> ciclos = cicloDao.listar();
        // List<Sede> sedes = sedeDao.listar();

        List<PabellonE> pabellonees = pabelloneDao.listar();
        PabellonE aulae_demo = new PabellonE();
        aulae_demo.setId(0);
        aulae_demo.setDenominacion("--SELECCIONE--");
        pabellonees.add(0, aulae_demo);

        List<Examen> examenes = examenDao.listar();
        List<AulaE> aulaes = new ArrayList<>();
        aulaes.add(new AulaE(0, "--SELECCIONE--", 0));
        //comboAppCiclo.setModel(new DefaultComboBoxModel(ciclos.toArray()));
        //comboAppSede.setModel(new DefaultComboBoxModel(sedes.toArray()));
        comboAppPabellone.setModel(new DefaultComboBoxModel(pabellonees.toArray()));
        comboExamenes.setModel(new DefaultComboBoxModel(examenes.toArray()));
        comboAppAulae.setModel(new DefaultComboBoxModel(aulaes.toArray()));
        //Ciclo ciclo_ = cicloDao.buscarPorId(ciclo);
        int pabellon_id = Config.PABELLONE;
        //       PabellonE pabellone_ = pabelloneDao.buscarPorId(pabellon_id);
//        System.out.println("pab:" + pabellone_);
//        comboAppPabellone.setSelectedItem(pabellone_);
    }

//    public void filtrarTablaAExamen(long pabellon_id, long aula_id) {
//        getGlassPane().activate();
//        this.thCargarEstudiantes = new THCargarEstudiantes(tablaAsistencia, pabellon_id, aula_id, lblTotal, glassPane);
//        this.thCargarEstudiantes.execute();
//    }
    public void paginarTablaAExamen(int pagina) {
        Map<String, JButton> botones = new HashMap<>();
//        botones.put("btnPrimero", btnPrimero);
//        botones.put("btnAnterior", btnAnterior);
//        botones.put("btnSiguiente", btnSiguiente);
//        botones.put("btnUltimo", btnUltimo);
        getGlassPane().activate();
        this.thPaginarAExamen = new THPaginarAExamen(tablaAsistencia, pagina, lblTotal, glassPane);
        this.thPaginarAExamen.execute();
    }

    public void filtrarTablaAExamen() {

        Examen exx = (Examen) comboExamenes.getSelectedItem();
        AulaE aulae = (AulaE) comboAppAulae.getSelectedItem();
        PabellonE pabe = (PabellonE) comboAppPabellone.getSelectedItem();
        long examen_id = exx.getId();
        long aulae_id = aulae.getId();
        long pabellone_id = pabe.getId();
        if (pabellone_id == 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un pabellon", "MENSAJE", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        getGlassPane().activate();
        this.thFiltrarAExamen = new THFiltrarAExamen(examen_id, aulae_id, pabellone_id, tablaAsistencia, lblTotal, glassPane, btnVistaPrevia);
        this.thFiltrarAExamen.execute();
        disableBtnPaginar();
    }

    private void disableBtnPaginar() {
//        btnPrimero.setEnabled(false);
//        btnAnterior.setEnabled(false);
//        btnSiguiente.setEnabled(false);
//        btnUltimo.setEnabled(false);
//        lblPagina.setText("1/1");
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
        jScrollPane2.setViewportView(getTablaUsuarios());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        comboAppPabellone = new javax.swing.JComboBox<>();
        comboAppAulae = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        comboExamenes = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnVistaPrevia = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setTitle("REPORTE DE SISTENCIA");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Asistencia Estudiantes a los examenes", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Filtros", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        comboAppAulae.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/16/filter-icon.png"))); // NOI18N
        jButton1.setText("Filtrar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Pabellon:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Aula:");

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboAppPabellone, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboAppAulae, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(comboExamenes, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVistaPrevia, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel3)
                .addComponent(comboAppPabellone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel4)
                .addComponent(comboAppAulae, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel5)
                .addComponent(comboExamenes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnVistaPrevia))
        );

        jPanel3.setBackground(new java.awt.Color(229, 229, 229));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.SystemColor.activeCaptionBorder));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel1.setText("Total: ");

        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTotal.setText("0");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("REGISTROS");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel1)
                .addComponent(lblTotal)
                .addComponent(jLabel2))
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

            PabellonE selectedPab = (PabellonE) comboAppPabellone.getSelectedItem();
            AulaE aulae = (AulaE) comboAppAulae.getSelectedItem();
            Examen selectedEx = (Examen) comboExamenes.getSelectedItem();

            int pabellone_id = (int) selectedPab.getId();
            int examen_id = (int) selectedEx.getId();
            int aulae_id = (int) aulae.getId();

            Sede sede_ = sedeDao.buscarPorId(Config.SEDE);
            //PabellonE pabellonee_ = pabelloneDao.buscarPorId(Config.PABELLONE);//el pabellon puede cambiar dependiendo del filtro
            String LOGO = "img/logo.jpg";
            String dir_huellas = Config.DIR_HUELLAS;

            HashMap param = new HashMap();
            param.put("urlLogo", LOGO);
            param.put("dir_huellas", dir_huellas);
            param.put("pabellone_id", pabellone_id);//pabellone
            param.put("examen_id", examen_id);//examen id para el select
            param.put("aulae_id", aulae_id);
            param.put("sede", sede_.getNombreSede().toUpperCase());//huamanga huanta etc
            param.put("ciclo_academico", Config.CICLO);//huamanga huanta etc
            param.put("local", selectedPab.getDenominacion());//examen id
            param.put("examen", selectedEx.getNombre());//examen id

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
            JasperPrint jasperPrint = JasperFillManager.fillReport("reportes/reportePostulantesPorAula.jasper", param, connection);
            ReportViewer dialog = new ReportViewer(jasperPrint);
            dialog.setVisible(true);
//            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException ex) {
            Logger.getLogger(FrmContainer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FrmContainer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnVistaPreviaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        filtrarTablaAExamen();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void addComboPabelloneEvent() {
        comboAppPabellone.addActionListener((ActionEvent event) -> {
            JComboBox comboBox = (JComboBox) event.getSource();

            PabellonE selected = (PabellonE) comboBox.getSelectedItem();
            if (selected.getId() == 0) {
                List<AulaE> aulas_ = new ArrayList<>();
                aulas_.add(0, new AulaE(0, "--TODOS--", 0));
                comboAppAulae.setModel(new DefaultComboBoxModel(aulas_.toArray()));
            } else {
                System.out.println("combo" + selected.getAulaes());
                List<AulaE> aulaes = selected.getAulaes();
                AulaE aulae_demo = new AulaE(0, "--TODOS--", 0);
                if (!aulaes.contains(aulae_demo)) {
                    aulaes.add(0, aulae_demo);
                }

                comboAppAulae.setModel(new DefaultComboBoxModel(aulaes.toArray()));
            }
        });
    }
//    private void accionClickTabla() {
//
//        getTablaUsuarios().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent event) {
//                Usuario usuario = getTablaUsuarios().getUsuarioSelected();
//                System.out.println("Usuario: " + usuario);
//                setUsuariotmp(usuario);
//                if (asistenciaTmp != null) {
//                    btnEditar.setEnabled(true);
//                }
//                if (event.getClickCount() == 2) {
//                    if (usuario != null) {
//                        DLGUsuarioEditar dialog = new DLGUsuarioEditar(usuario);
//                        dialog.setParentFrame(getJIframe());
//                        //dialog.setUsuario(usuario);//nullpointerException
//                        dialog.setVisible(true);
//                    }
//                }
//            }
//        }
//        );
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnVistaPrevia;
    private javax.swing.JComboBox<AulaE> comboAppAulae;
    private javax.swing.JComboBox<PabellonE> comboAppPabellone;
    private javax.swing.JComboBox<Examen> comboExamenes;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
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
    public AsistenciaExamen getUsuariotmp() {
        return asistenciaTmp;
    }

    /**
     * @param usuariotmp the usuariotmp to set
     */
    public void setUsuariotmp(AsistenciaExamen usuariotmp) {
        this.asistenciaTmp = usuariotmp;
    }

    /**
     * @return the tablaUsuarios
     */
    public AExamenTable getTablaUsuarios() {
        return tablaAsistencia;
    }

    /**
     * @param tablaAsistencia the tablaUsuarios to set
     */
    public void setTablaUsuarios(AExamenTable tablaAsistencia) {
        this.tablaAsistencia = tablaAsistencia;
    }

    public IFAExamen getJIframe() {
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
