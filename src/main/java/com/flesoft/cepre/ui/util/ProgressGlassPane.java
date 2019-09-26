/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 *
 * @author user
 */
public class ProgressGlassPane extends JComponent implements KeyListener {

    private static final Color TEXT_COLOR = new Color(0x333333);
    private String message = "Procesando...";
    private Image loader = new ImageIcon(getClass().getResource("/images/loading_bar.gif")).getImage();

    public ProgressGlassPane() {
        super();
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        setBackground(Color.WHITE);
        setFont(new Font("Default", Font.BOLD, 13));
        addMouseListener(new MouseAdapter() {
        });
        addMouseMotionListener(new MouseMotionAdapter() {
        });
        addKeyListener(this);
        setFocusTraversalKeysEnabled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        AlphaComposite alpha = AlphaComposite.SrcOver.derive(0.55f);
        Composite composite = g2.getComposite();
        g2.setComposite(alpha);

        Dimension d = this.getSize();
        g.setColor(Color.white);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.black);
        drawCenteredString(getMessage(), d.width, d.height, g);

        drawCenteredIcon(getLoader(), d.width, d.height, g);
        g2.setComposite(composite);
    }

    public void drawCenteredString(String s, int w, int h, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int x = (w - fm.stringWidth(s)) / 2;
        int y = ((fm.getAscent() + h) / 2) + 17;
        g.drawString(s, x, y);
    }

    public void drawCenteredIcon(Image image, int w, int h, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int x = (this.getWidth() - image.getWidth(null)) / 2;
        int y = (this.getHeight() - image.getHeight(null)) / 2;
        g2d.drawImage(image, x, y, this);
    }

    public void keyPressed(KeyEvent e) {
        e.consume();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        e.consume();
    }

    public void activate() {
        setVisible(true);
        requestFocusInWindow();
    }

    public void activate(String msg) {
        setMessage(msg);
        setVisible(true);
        requestFocusInWindow();
    }

    public void deactivate() {
        setVisible(false);
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the loader
     */
    public Image getLoader() {
        return loader;
    }

    /**
     * @param loader the loader to set
     */
    public void setLoader(Image loader) {
        this.loader = loader;
    }
}
