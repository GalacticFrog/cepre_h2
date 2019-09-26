/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.config;

import com.flesoft.cepre.dao.PabellonDao;
import com.flesoft.cepre.dao.PabelloneDao;
import com.flesoft.cepre.dao.SedeDao;

import com.flesoft.cepre.model.Pabellon;
import com.flesoft.cepre.model.PabellonE;
import com.flesoft.cepre.model.Sede;
import com.flesoft.cepre.util.AppContext;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author user
 */
public class IFConfig extends javax.swing.JInternalFrame {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(IFConfig.class);

    private String titulo;
    private int sede;
    private String ciclo;
    private int pabellon;
    private int pabellone;
    private String host;
    private String port;
    private String database;
    private String username;
    private String password;

    public IFConfig() {
        initComponents();
        cardarDatosConfig();
        cargarCombos();
        cargarCfgBd();
    }

    private void cardarDatosConfig() {

        try {
            Parameters params = new Parameters();

            FileBasedConfigurationBuilder<FileBasedConfiguration> builder
                    = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                            .configure(params.properties()
                                    .setFileName("config.properties"));
            Configuration config = builder.getConfiguration();

            this.titulo = config.getString("app.titulo");
            this.sede = config.getInt("app.sede");
            this.ciclo = config.getString("app.ciclo");
            this.pabellon = config.getInt("app.pabellon");
            this.pabellone = config.getInt("app.pabellone");

            this.host = config.getString("db.host");
            this.port = config.getString("db.port");
            this.database = config.getString("db.database");
            this.username = config.getString("db.username");
            this.password = config.getString("db.password");

        } catch (ConfigurationException cex) {
            cex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void cargarCombos() {

        //CicloDao cicloDao = AppContext.getInstance().getBean(CicloDao.class);
        SedeDao sedeDao = AppContext.getInstance().getBean(SedeDao.class);
        PabellonDao pabellonDaoDao = AppContext.getInstance().getBean(PabellonDao.class);
        PabelloneDao pabelloneDao = AppContext.getInstance().getBean(PabelloneDao.class);

        //List<Ciclo> ciclos = cicloDao.listar();
        List<Sede> sedes = sedeDao.listar();
        List<Pabellon> pabellones = pabellonDaoDao.listar();
        List<PabellonE> pabellonees = pabelloneDao.listar();

        //comboAppCiclo.setModel(new DefaultComboBoxModel(ciclos.toArray()));
        comboAppSede.setModel(new DefaultComboBoxModel(sedes.toArray()));
        comboAppPabellon.setModel(new DefaultComboBoxModel(pabellones.toArray()));
        comboAppPabellone.setModel(new DefaultComboBoxModel(pabellonees.toArray()));

        //Ciclo ciclo_ = cicloDao.buscarPorId(ciclo);
        Sede sede_ = sedeDao.buscarPorId(sede);
        Pabellon pabellone_ = pabellonDaoDao.buscarPorId(pabellon);
        PabellonE pabellonee_ = pabelloneDao.buscarPorId(pabellone);

        txtAppTitulo.setText(titulo);
        txtAppCiclo.setText(ciclo);

        //comboAppPabellon.setSelectedItem(ciclo_);
        comboAppSede.setSelectedItem(sede_);
        comboAppPabellon.setSelectedItem(pabellone_);
        comboAppPabellone.setSelectedItem(pabellonee_);
        System.out.println(" " + sede_ + pabellone_ + pabellonee_);

    }

    private void cargarCfgBd() {
        txtDbHost.setText(host);
        txtDbPort.setText(port);
        txtDbDatabase.setText(database);
        txtDbUser.setText(username);
        txtDbPassword.setText(password);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        comboAppSede = new javax.swing.JComboBox<>();
        comboAppPabellon = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        comboAppPabellone = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        txtAppTitulo = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        btnRegistrar1 = new javax.swing.JButton();
        txtAppCiclo = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtDbHost = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtDbPort = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtDbDatabase = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtDbUser = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtDbPassword = new javax.swing.JTextField();
        btnRegistrar2 = new javax.swing.JButton();

        setTitle("CONFIGURAR SISTEMA");

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel8.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel8.setText("Titulo:");

        comboAppSede.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N

        comboAppPabellon.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel9.setText("Pabellon clases:");

        jLabel10.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel10.setText("Pabellon examen:");

        comboAppPabellone.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel11.setText("Sede:");

        txtAppTitulo.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel12.setText("Ciclo");

        btnRegistrar1.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        btnRegistrar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/32/Checked-32.png"))); // NOI18N
        btnRegistrar1.setText("Guardar");
        btnRegistrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrar1ActionPerformed(evt);
            }
        });

        txtAppCiclo.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRegistrar1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(comboAppPabellone, 0, 398, Short.MAX_VALUE)
                            .addComponent(txtAppCiclo, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(comboAppSede, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboAppPabellon, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtAppTitulo))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtAppTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtAppCiclo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(comboAppSede, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(comboAppPabellon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(comboAppPabellone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addComponent(btnRegistrar1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("APLICACION", jPanel1);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel13.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel13.setText("Host:");

        txtDbHost.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel18.setText("Puerto:");

        txtDbPort.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel19.setText("Nombre:");

        txtDbDatabase.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel20.setText("Usuario:");

        txtDbUser.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel21.setText("Contraseña:");

        txtDbPassword.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N

        btnRegistrar2.setFont(new java.awt.Font("Segoe UI Emoji", 0, 18)); // NOI18N
        btnRegistrar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/32/Checked-32.png"))); // NOI18N
        btnRegistrar2.setText("Guardar");
        btnRegistrar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrar2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(66, 66, 66)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDbHost)
                            .addComponent(txtDbPort)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
                        .addGap(66, 66, 66)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDbUser, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                            .addComponent(txtDbDatabase)
                            .addComponent(txtDbPassword)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRegistrar2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtDbHost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtDbPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtDbDatabase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtDbUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtDbPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addComponent(btnRegistrar2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("BASE DE DATOS", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegistrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrar1ActionPerformed
        String titulo_c = txtAppTitulo.getText();
        Sede sede_c = (Sede) comboAppSede.getSelectedItem();
        String ciclo_c = txtAppCiclo.getText();
        Pabellon pabellon_c = (Pabellon) comboAppPabellon.getSelectedItem();
        PabellonE pabellone_c = (PabellonE) comboAppPabellone.getSelectedItem();

        try {
            Parameters params = new Parameters();

            FileBasedConfigurationBuilder<FileBasedConfiguration> builder
                    = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                            .configure(params.properties()
                                    .setFileName("config.properties"));
            Configuration config = builder.getConfiguration();

            config.setProperty("app.titulo", titulo_c);
            config.setProperty("app.sede", sede_c.getId());
            config.setProperty("app.ciclo", ciclo_c);
            config.setProperty("app.pabellon", pabellon_c.getId());
            config.setProperty("app.pabellone", pabellone_c.getId());

            builder.save();
            JOptionPane.showMessageDialog(this, "Guardado correctamente.\nREINICIAR LA APLICACION", "AVISO!", JOptionPane.INFORMATION_MESSAGE);
            logger.info("Datos app Guardados...");
            System.exit(0);
        } catch (ConfigurationException ex) {
            Logger.getLogger(IFConfig.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnRegistrar1ActionPerformed

    private void btnRegistrar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrar2ActionPerformed

        String _host = txtDbHost.getText().trim();
        String _port = txtDbPort.getText().trim();
        String _database = txtDbDatabase.getText().trim();
        String _username = txtDbUser.getText().trim();
        String _password = txtDbPassword.getText().trim();

        try {
            Parameters params = new Parameters();

            FileBasedConfigurationBuilder<FileBasedConfiguration> builder
                    = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                            .configure(params.properties()
                                    .setFileName("config.properties"));
            Configuration config = builder.getConfiguration();

            config.setProperty("db.host", _host);
            config.setProperty("db.port", _port);
            config.setProperty("db.database", _database);
            config.setProperty("db.username", _username);
            config.setProperty("db.password", _password);
            builder.save();
            JOptionPane.showMessageDialog(this, "Guardado correctamente.\nREINICIAR LA APLICACION", "AVISO!", JOptionPane.INFORMATION_MESSAGE);
            logger.info("Datos db Guardados...");
            System.exit(0);

        } catch (ConfigurationException ex) {
            Logger.getLogger(IFConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRegistrar2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegistrar1;
    private javax.swing.JButton btnRegistrar2;
    private javax.swing.JComboBox<String> comboAppPabellon;
    private javax.swing.JComboBox<String> comboAppPabellone;
    private javax.swing.JComboBox<String> comboAppSede;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField txtAppCiclo;
    private javax.swing.JTextField txtAppTitulo;
    private javax.swing.JTextField txtDbDatabase;
    private javax.swing.JTextField txtDbHost;
    private javax.swing.JTextField txtDbPassword;
    private javax.swing.JTextField txtDbPort;
    private javax.swing.JTextField txtDbUser;
    // End of variables declaration//GEN-END:variables
}
