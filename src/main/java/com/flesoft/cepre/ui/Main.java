/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui;

import com.digitalpersona.uareu.ReaderCollection;
import com.digitalpersona.uareu.UareUException;
import com.digitalpersona.uareu.UareUGlobal;
import com.flesoft.cepre.dao.ExamenDao;
import com.flesoft.cepre.dao.PabellonDao;
import com.flesoft.cepre.model.Examen;
import com.flesoft.cepre.model.Pabellon;

import com.flesoft.cepre.ui.asistencia.IFAsistenciaExamen;
import com.flesoft.cepre.ui.config.IFConfig;
import com.flesoft.cepre.ui.docentes.IFDocentes;
import com.flesoft.cepre.ui.estudiante.IFEstudiantes;
import com.flesoft.cepre.ui.examen.IFExamen;
import com.flesoft.cepre.ui.personal.IFPersonal;
import com.flesoft.cepre.ui.usuario.IFUsuarios;
import com.flesoft.cepre.ui.util.MessageBox;
import com.flesoft.cepre.ui.util.Silk;
import com.flesoft.cepre.util.AppContext;
import com.flesoft.cepre.util.Config;
import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.SkyYellow;
import com.revolsys.famfamfam.silk.SilkIconLoader;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.oxbow.swingbits.dialog.task.TaskDialogs;

/**
 *
 * @author edrev
 */
public class Main extends javax.swing.JFrame {

    private static final Log LOGGER = LogFactory.getLog(Main.class);

//    private ReaderCollection m_collection;
//    private Reader m_reader;
    private Process process;

    private String errorMsg_ = "";
    //private final CefBrowser browser_;
    //private ControlPanel control_pane_;
    //private StatusPanel status_panel_;
    //private final CefCookieManager cookieManager_;

    public Main() {
        chechDbConn();//test conexion base de datos
        initComponents();
        Config.setVars();
        cargarIFdefecto();
        //setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        hoyExamen();
        pabellon();
        //DibujarLogo();
    }

    private void pabellon() {
        PabellonDao pDao = AppContext.getInstance().getBean(PabellonDao.class);
        Pabellon pab = pDao.buscarPorId(Config.PABELLON);
        lblmsg.setText("Local: " + pab.getDenominacion());
        lblServer.setText("server: " + Config.HOST);
        lblCiclo.setText("CICLO ACAD\u00c9MICO " + Config.CICLO);
    }

    public void DibujarLogo() {
        // File file = new File("img/logo-cepre.jpg");
//        ImageIcon imageIcon = new ImageIcon(new ImageIcon("img/logo-cepre.jpg").getImage().getScaledInstance(lblLogo.getHeight(), lblLogo.getWidth(), Image.SCALE_DEFAULT));
//        lblLogo.setIcon(imageIcon);
        //BufferedImage image = new BufferedImage(lblLogo.getWidth(), lblLogo.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        //image.getRaster().setDataElements(0, 0, view.getWidth(), view.getHeight(), view.getImageData());

        // lblLogo.setIcon(new ImageIcon(image.getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_DEFAULT)));
        repaint();
    }

    public void chechDbConn() {

        try {
            BasicDataSource dataSource = AppContext.getInstance().getBean(BasicDataSource.class);

            Connection connection = dataSource.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("SELECT 1");
            //System.out.println("query:::::::::::" + pstmt.executeQuery());

            //List<Ciclo> listar = cicloDao.listar();
            LOGGER.info("################ PROBANDO CONEXION #################");
        } catch (SQLException ex) {
            LOGGER.info("ERROR" + "NO SE PUEDE CONECTAR A LA BASE DE DATOS");
            TaskDialogs.showException(ex, "ERROR", "NO SE PUEDE CONECTAR A LA BASE DE DATOS");

            System.exit(0);
        } catch (Exception e) {
            TaskDialogs.showException(e, "ERROR", "NO SE PUEDE CONECTAR A LA BASE DE DATOS");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        lblEstado = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblmsg = new javax.swing.JLabel();
        lblServer = new javax.swing.JLabel();
        contenedor = new javax.swing.JDesktopPane();
        jToolBar1 = new javax.swing.JToolBar();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblCiclo = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SISTEMA ELECTRÓNICO DE IDENTIFICACIÓN DACTILAR");
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/images/favicon-32x32.png")).getImage());
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel2.setPreferredSize(new java.awt.Dimension(400, 20));

        lblEstado.setText("Usuario: Invitado");

        jLabel1.setText("|");

        lblmsg.setText("Pabellon:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblmsg, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblServer, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(lblEstado, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addComponent(lblmsg)
                .addComponent(lblServer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        javax.swing.GroupLayout contenedorLayout = new javax.swing.GroupLayout(contenedor);
        contenedor.setLayout(contenedorLayout);
        contenedorLayout.setHorizontalGroup(
            contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1005, Short.MAX_VALUE)
        );
        contenedorLayout.setVerticalGroup(
            contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 505, Short.MAX_VALUE)
        );

        getContentPane().add(contenedor, java.awt.BorderLayout.CENTER);

        jToolBar1.setRollover(true);
        jToolBar1.setPreferredSize(new java.awt.Dimension(999, 90));

        jPanel1.setBackground(javax.swing.UIManager.getDefaults().getColor("CheckBox.light"));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setMaximumSize(new java.awt.Dimension(250, 83));
        jPanel1.setMinimumSize(new java.awt.Dimension(250, 83));
        jPanel1.setPreferredSize(new java.awt.Dimension(250, 83));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logos/android-icon-72x72.png"))); // NOI18N
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(52, 52, 52));
        jLabel4.setText("CENTRO  PREUNIVERSITARIO");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 46)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(82, 82, 82));
        jLabel9.setText("CEPRE");

        lblCiclo.setFont(new java.awt.Font("Times New Roman", 1, 13)); // NOI18N
        lblCiclo.setForeground(new java.awt.Color(37, 37, 37));
        lblCiclo.setText("CICLO ACADEMICO 2019 III");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCiclo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCiclo)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jToolBar1.add(jPanel1);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/separator.png"))); // NOI18N
        jToolBar1.add(jLabel5);

        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/48/icons8-Exam-48.png"))); // NOI18N
        jButton2.setText("Asistencia Examen");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/48/icons8-asist-48.png"))); // NOI18N
        jButton3.setText("Asistencia clases");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/separator.png"))); // NOI18N
        jToolBar1.add(jLabel2);

        jButton4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/48/icons8-Student-48.png"))); // NOI18N
        jButton4.setText("Estudiantes");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        jButton6.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/48/icons8-examen-48.png"))); // NOI18N
        jButton6.setText("Examenes");
        jButton6.setFocusable(false);
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton6);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/separator.png"))); // NOI18N
        jToolBar1.add(jLabel7);

        jButton7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/48/icons8-employe-48.png"))); // NOI18N
        jButton7.setText("Personal");
        jButton7.setFocusable(false);
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton7);

        jButton5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/48/icons8-Teacher-48.png"))); // NOI18N
        jButton5.setText("Docentes");
        jButton5.setFocusable(false);
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton5);

        jButton10.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/48/Security-Check.png"))); // NOI18N
        jButton10.setText(" Usuarios ");
        jButton10.setFocusable(false);
        jButton10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton10.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton10);

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/separator.png"))); // NOI18N
        jToolBar1.add(jLabel12);

        jButton9.setFont(new java.awt.Font("Segoe UI Semibold", 0, 13)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/48/icons8-Settings-48.png"))); // NOI18N
        jButton9.setText("Configurar");
        jButton9.setFocusable(false);
        jButton9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton9);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/separator.png"))); // NOI18N
        jToolBar1.add(jLabel6);

        jButton8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/48/icons8-Logout-48.png"))); // NOI18N
        jButton8.setText(" Salir ");
        jButton8.setFocusable(false);
        jButton8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton8);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/separator.png"))); // NOI18N
        jToolBar1.add(jLabel8);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

    }//GEN-LAST:event_formWindowClosing

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        //Iniciar();
    }//GEN-LAST:event_formWindowOpened

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        limpiarFrames();
        IFConfig iFconfig = new IFConfig();
        //iFasistencia.setParentFrame(this);
        this.contenedor.add(iFconfig);
        try {
            iFconfig.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        iFconfig.show();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        limpiarFrames();
        LOGGER.info("agregando IF Personal ");
        IFUsuarios iFusuarios = new IFUsuarios();
        //        iFusuarios.setMainFrame(this);
        this.contenedor.add(iFusuarios);
        try {
            iFusuarios.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        iFusuarios.show();
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        limpiarFrames();
        LOGGER.info("agregando IF Docentes ");
        IFDocentes iFusuarios = new IFDocentes();
        //        iFusuarios.setMainFrame(this);
        this.contenedor.add(iFusuarios);
        try {
            iFusuarios.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        iFusuarios.show();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        limpiarFrames();
        LOGGER.info("agregando IF Personal ");
        IFPersonal iFusuarios = new IFPersonal();
        //        iFusuarios.setMainFrame(this);
        this.contenedor.add(iFusuarios);
        try {
            iFusuarios.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        iFusuarios.show();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        limpiarFrames();
        LOGGER.info("examenes ");
        IFExamen demo = new IFExamen();
        this.contenedor.add(demo);
        try {
            demo.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        demo.show();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        cargarIFdefecto();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

//        if (!checkReader()) {
//            return;
//        }
//        limpiarFrames();
//        LOGGER.info("AGREGANDO FRAME ASISTENCIA ");
//        IFAsistenciaAula iFasistencia = new IFAsistenciaAula();
//        //iFasistencia.doModal();
//        this.contenedor.add(iFasistencia);
//        try {
//            iFasistencia.setMaximum(true);
//        } catch (PropertyVetoException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        iFasistencia.show();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        System.out.println("hoyExamen()?:::::: " + hoyExamen());
        //        if (!hoyExamen()) {
        //            JOptionPane.showMessageDialog(this, "Ningun Examen programado para hoy..", "AVISO!", JOptionPane.ERROR_MESSAGE);
        //            return;
        //        }
        //System.out.println("AQUI:::::: ");
        if (!checkReader()) {
            return;
        }
        limpiarFrames();
        LOGGER.info("IF Asistencia Examen ");
        ExamenDao examenDao = AppContext.getInstance().getBean(ExamenDao.class);
        Date hoy = new Date();
        Examen examen_ = examenDao.buscarPorFecha(hoy);
        if (examen_ == null) {
            //TaskDialogs.warning(null, "Importante!", "Ningun examen programado para hoy");
            List<Examen> examenes = examenDao.listarPendientes();
            Object[] choices = examenes.toArray();
            Examen input = (Examen) JOptionPane.showInputDialog(null,
                    "NO SE ENCONTRO NINGUN EXAMEN PROGRAMADO PARA HOY \nPor favor seleccione un examen",
                    "NO SE ENCONTRO NINGUN EXAMEN", JOptionPane.INFORMATION_MESSAGE, null,
                    choices, // Array
                    null); // Valor Inicial
            System.out.println(input);
            if (input == null) {
                return;
            }
            examen_ = input;
        }

        IFAsistenciaExamen iFasistencia = new IFAsistenciaExamen(examen_);
        //iFasistencia.cargarDatosExamen();
        //        iFasistencia.setParentFrame(this);
        this.contenedor.add(iFasistencia);
        try {
            iFasistencia.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        iFasistencia.show();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cargarIFdefecto() {
        limpiarFrames();
        IFEstudiantes demo = new IFEstudiantes();
        this.contenedor.add(demo);
        try {
            demo.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        demo.show();
    }

    /**
     * limpia todos los jinternalframes para poder iniciar el lector
     */
    private void limpiarFrames() {
        JInternalFrame[] frames = contenedor.getAllFrames();
        for (JInternalFrame f : frames) {
            f.dispose();
        }
        contenedor.removeAll();
        contenedor.updateUI();
        revalidate();
        repaint();
    }

    private boolean hoyExamen() {
        //String ciclo_id = Config.CICLO;
        Date hoy = new Date();
        ExamenDao examenDao = AppContext.getInstance().getBean(ExamenDao.class);
        try {
            Examen examen = examenDao.buscarPorFecha(hoy);
            LOGGER.info("EXAMEN: " + examen.getNombre());
            return examen != null;
        } catch (NullPointerException e) {
            LOGGER.info("Ningun examen para hoy " + e.toString());
            return false;
            //JOptionPane.showMessageDialog(this, "Ningun Examen programado para hoy", "AVISO!", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ne) {
            LOGGER.info("Ningun examen para hoy " + ne.toString());
            return false;
            //JOptionPane.showMessageDialog(this, "Ningun Examen programado para hoy", "AVISO!", JOptionPane.ERROR_MESSAGE);
        }
        //return examen != null;
    }

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //  WebLookAndFeel.install();
                createAndShowGUI();
            }
        });
    }

    public static void createAndShowGUI() {

        try {

            UIManager.setLookAndFeel(new PlasticXPLookAndFeel());//using jgoodies look
            PlasticLookAndFeel.setPlasticTheme(new SkyYellow());
            //UIManager.setLookAndFeel(new WindowsLookAndFeel());//using jgoodies look

            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
        } catch (Exception e) {

        }
        ///////////////////////////////////////////////
        final Main main = new Main();
        //main.startHttp();
        main.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                main.dispose();
            }
        });
        main.setVisible(true);
        //release capture library by destroying reader collection
//        try {
//            UareUGlobal.DestroyReaderCollection();
//        } catch (UareUException e) {
//            MessageBox.DpError("UareUGlobal.destroyReaderCollection()", e);
//        }
    }

//    private boolean readerStatus() {
//        try {
//            this.m_collection = UareUGlobal.GetReaderCollection();
//            m_collection.GetReaders();
//        } catch (UareUException e1) {
//            // TODO Auto-generated catch block
//            JOptionPane.showMessageDialog(null, "Error getting collection");
//            return false;
//        }
//        if (m_collection.size() == 0) {
//            MessageBox.Warning("Lector no conectado?");
//            return false;
//        }
//        return true;
//    }
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

    public void enviarTexto(String string) {
        lblmsg.setText(string);
    }

    public void enviarTextoDesconectado(String string) {
        //txtArea.append(string + "\n");
        lblEstado.setText(string);
        lblEstado.setIcon(SilkIconLoader.getIcon(Silk.CANCEL));
        //this.parent.getDemolbl().setText("LoL");
    }

    public void enviarTextoConectado(String string) {
        //txtArea.append(string + "\n");
        lblEstado.setText(string);
        lblEstado.setIcon(SilkIconLoader.getIcon(Silk.LIGHTNING));

    }

    public JLabel getLblEstado() {
        return lblEstado;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane contenedor;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblCiclo;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblServer;
    private javax.swing.JLabel lblmsg;
    // End of variables declaration//GEN-END:variables
}
