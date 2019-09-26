/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.docentes;

import com.digitalpersona.uareu.Fid;
import com.flesoft.cepre.ui.util.FileUtil;
import com.flesoft.cepre.ui.util.ImageUtils;
import com.flesoft.cepre.util.Config;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author edrev
 */
public class DLGImagenHuella extends javax.swing.JDialog {

    private static final Log logger = LogFactory.getLog(DLGImagenHuella.class);
    private String ruta;//ruta de la imagena  guardar
    private List<Fid> muestras = new ArrayList<>();

    /**
     * Creates new form DLGHuella
     */
    public DLGImagenHuella(String ruta, ArrayList<Fid> muestras) {
        super(new javax.swing.JFrame(), true);

        this.ruta = ruta;
        this.muestras = muestras;
        initComponents();
        setLocationRelativeTo(null);
        keyListener();
        DibujarHuellas();
        setFocusable(true);//Setting Focus true for ur container
    }

    private void keyListener() {
        addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent ke) {
                switch (ke.getKeyCode()) {
                    case KeyEvent.VK_NUMPAD1:
                    case KeyEvent.VK_1:
                        System.out.println("1");

                        guardarImagenHuella(0);

                        break;
                    case KeyEvent.VK_NUMPAD2:
                    case KeyEvent.VK_2:
                        System.out.println("2");

                        guardarImagenHuella(1);

                        break;
                    case KeyEvent.VK_NUMPAD3:
                    case KeyEvent.VK_3:
                        System.out.println("3");

                        guardarImagenHuella(2);

                        break;
                    case KeyEvent.VK_NUMPAD4:
                    case KeyEvent.VK_4:
                        System.out.println("4");

                        guardarImagenHuella(3);

                        break;

                    default:
                        System.out.println("default");

                        guardarImagenHuella(2);

                        break;
                }

            }

            @Override
            public void keyTyped(KeyEvent e) {
                System.out.println("keyTyped");
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //System.out.println("keyReleased");
            }
        });
    }

    private void guardarImagenHuella(int indice) {

        Fid image_h = muestras.get(indice);
        Fid.Fiv view = image_h.getViews()[0];
        BufferedImage img = new BufferedImage(view.getWidth(), view.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        img.getRaster().setDataElements(0, 0, view.getWidth(), view.getHeight(), view.getImageData());
        //ImageUtils.save(this.ruta, img, "jpg");  // png okay, j2se 1.4+
        String dir_huellas = Config.DIR_HUELLAS;
        String _ruta = dir_huellas + "/" + this.ruta;
        String _ruta2 = "huellas/" + this.ruta;
        boolean isdir = FileUtil.isDirRewritable(dir_huellas);//el path es un directoro y es escribible?
        if (isdir) {
            try {
                logger.info("guardando huella en: " + _ruta);
                ImageUtils.save(_ruta, img, "gif");
            } catch (Exception ex1) {
                logger.error("(1)No se puede guardar la huella en la ruta: " + _ruta, ex1);
            }
        } else {
            try {
                logger.info("No es scribible el directorio:" + _ruta + ", guardando huella en:" + _ruta2);
                logger.info("Guardando en:" + _ruta2);
                ImageUtils.save(_ruta2, img, "gif");
            } catch (Exception ex2) {
                logger.error("(2)No se puede guardar la huella en la ruta: " + _ruta2, ex2);
            }
        }
        //ImageUtils.save(this.ruta, img, "gif");
        logger.info("guardando imagen");
//        ImageUtils.saveTiff(this.ruta, img);
        accionCerrarVentana();
    }

    public void DibujarHuellas() {
        DibujarHuella(muestras.get(0), lblImagenHuella_1);
        DibujarHuella(muestras.get(1), lblImagenHuella_2);
        DibujarHuella(muestras.get(2), lblImagenHuella_3);
        DibujarHuella(muestras.get(3), lblImagenHuella_4);

    }

    public void DibujarHuella(Fid image_h, JLabel lblImagenHuella) {
        Fid.Fiv view = image_h.getViews()[0];
        BufferedImage image = new BufferedImage(view.getWidth(), view.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        image.getRaster().setDataElements(0, 0, view.getWidth(), view.getHeight(), view.getImageData());

        lblImagenHuella.setIcon(new ImageIcon(image.getScaledInstance(lblImagenHuella.getWidth(), lblImagenHuella.getHeight(), Image.SCALE_DEFAULT)));
        repaint();
    }

    private void accionCerrarVentana() {
        setVisible(false);
        dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        lblImagenHuella_1 = new javax.swing.JLabel();
        lblMuestras = new javax.swing.JLabel();
        lblImagenHuella_2 = new javax.swing.JLabel();
        lblImagenHuella_3 = new javax.swing.JLabel();
        lblImagenHuella_4 = new javax.swing.JLabel();
        lblMuestras1 = new javax.swing.JLabel();
        lblMuestras2 = new javax.swing.JLabel();
        lblMuestras3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "MUESTRAS", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        lblImagenHuella_1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        lblMuestras.setFont(new java.awt.Font("Tahoma", 1, 100)); // NOI18N
        lblMuestras.setText("1");

        lblImagenHuella_2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        lblImagenHuella_3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        lblImagenHuella_4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        lblMuestras1.setFont(new java.awt.Font("Tahoma", 1, 100)); // NOI18N
        lblMuestras1.setText("3");

        lblMuestras2.setFont(new java.awt.Font("Tahoma", 1, 100)); // NOI18N
        lblMuestras2.setText("4");

        lblMuestras3.setFont(new java.awt.Font("Tahoma", 1, 100)); // NOI18N
        lblMuestras3.setText("2");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImagenHuella_1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblImagenHuella_2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblImagenHuella_3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblImagenHuella_4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(lblMuestras)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblMuestras3)
                .addGap(128, 128, 128)
                .addComponent(lblMuestras1)
                .addGap(136, 136, 136)
                .addComponent(lblMuestras2)
                .addGap(69, 69, 69))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblImagenHuella_4, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblMuestras2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblImagenHuella_1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblMuestras, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblImagenHuella_2, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblMuestras3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblImagenHuella_3, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblMuestras1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29))
        );

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("SELECCIONE LA MEJOR MUESTRA PARA GUARDAR HUELLA");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(270, 270, 270)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

//    @Override
//    protected JRootPane createRootPane() {
//        JRootPane rootPanes = new JRootPane();
//        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_NUMPAD1, 0);
//        KeyStroke stroke2 = KeyStroke.getKeyStroke(KeyEvent.VK_1, 0);
//        Action actionListener = new AbstractAction() {
//
//        @Override
//            public void keypre
//        };
//
//        InputMap inputMap = rootPanes.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
//
//        inputMap.put(stroke2, "1");
//        rootPanes.getActionMap().put("1", actionListener);
//        inputMap.put(stroke, "NUMPAD1");
//        rootPanes.getActionMap().put("NUMPAD1", actionListener);
//
//        return rootPanes;
////    }
//    public static void main(String args[]) {
//
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                DLGImagenHuella dialog = new DLGImagenHuella();
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblImagenHuella_1;
    private javax.swing.JLabel lblImagenHuella_2;
    private javax.swing.JLabel lblImagenHuella_3;
    private javax.swing.JLabel lblImagenHuella_4;
    private javax.swing.JLabel lblMuestras;
    private javax.swing.JLabel lblMuestras1;
    private javax.swing.JLabel lblMuestras2;
    private javax.swing.JLabel lblMuestras3;
    // End of variables declaration//GEN-END:variables
}
