/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.util;

/**
 *
 * @author frg
 */
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class ImagePanel extends JPanel {

    private Image image = new ImageIcon(getClass().getResource("/images/logo-cepre.jpg")).getImage();

    public ImagePanel() {
        
    }
//    public ImagePanel(String filename) {
//        this.image = new ImageIcon(getClass().getResource(filename)).getImage();
//    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(this.getWidth() / 2, this.getHeight() / 2);
        g2d.translate(-image.getWidth(null) / 2, -image.getHeight(null) / 2);
        g2d.drawImage(image, 0, 0, null);
    }

    /**
     * @param args
     */
//    public static void main(String[] args) {
//        ImagePanel panel = new ImagePanel("/images/logo-cepre.jpg");
//        //panel.setBorder(BorderFactory.createTitledBorder("Iniciar Sesion"));
//        JFrame frame = new JFrame("Frame");
//        frame.setSize(800, 600);
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.getContentPane().add(panel, BorderLayout.CENTER);
//        frame.setVisible(true);
//    }
}
