package com.flesoft.cepre.ui.util;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DisabledGlassPane extends JComponent implements KeyListener {

    private static final long serialVersionUID = 5393484427189897719L;
    private String message = "Procesando...";

    public DisabledGlassPane() {
        super();
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Color base = java.awt.Color.lightGray;
        Color background = new Color(base.getRed(), base.getGreen(), base.getBlue(), 128);
        setBackground(background);
        setLayout(new GridBagLayout());
        addMouseListener(new MouseAdapter() {
        });
        addMouseMotionListener(new MouseMotionAdapter() {
        });
        addKeyListener(this);
        setFocusTraversalKeysEnabled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getSize().width, getSize().height);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        AlphaComposite alpha = AlphaComposite.SrcOver.derive(0.25f);
        Composite composite = g2.getComposite();
        g2.setComposite(alpha);
        Dimension d = this.getSize();
        g.setColor(Color.white);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.black);
        drawCenteredString(message, d.width, d.height, g);
        Image icon = new ImageIcon(getClass().getResource("/images/loading_bar.gif")).getImage();
        drawCenteredIcon(icon, d.width, d.height, g);
        g2.setComposite(composite);
    }

    public void drawCenteredString(String s, int w, int h, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        //g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
        int x = (w - fm.stringWidth(s)) / 2;
        int y = ((fm.getAscent() + h) / 2) + 15;
        g.drawString(s, x, y);
    }

    public void drawCenteredIcon(Image image, int w, int h, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int x = (this.getWidth() - image.getWidth(null)) / 2;
        int y = (this.getHeight() - image.getHeight(null)) / 2;
        g2d.drawImage(image, x, y, this);
    }

    /*
     * Implement the KeyListener to consume events
     */
    public void keyPressed(KeyEvent e) {
        e.consume();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        e.consume();
    }

    /*
     *  Make the glass pane visible and change the cursor to the wait cursor
     *
     *  A message can be displayed and it will be centered on the frame.
     */
    public void activate() {
        setVisible(true);
        requestFocusInWindow();
    }

    /*
     *  Hide the glass pane and restore the cursor
     */
    public void deactivate() {
        setVisible(false);
    }

}
