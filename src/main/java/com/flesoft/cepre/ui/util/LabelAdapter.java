/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.util;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;

/**
 *
 * @author user
 */
public class LabelAdapter extends MouseAdapter {

    private final JLabel label;

    public LabelAdapter(JLabel label) {
        this.label = label;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        label.setBackground(new java.awt.Color(33, 63, 86));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        label.setBackground(new java.awt.Color(33, 63, 86));

    }

    @Override
    public void mouseExited(MouseEvent e) {
        label.setBackground(Color.LIGHT_GRAY);
    }
}
