/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.util;

/**
 *
 * @author edrev
 */
import com.jidesoft.dialog.ButtonPanel;
import com.jidesoft.dialog.StandardDialog;
import com.jidesoft.dialog.StandardDialogPane;
import com.jidesoft.swing.JideBoxLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 */
public class ErrorDialog extends StandardDialog {

    public JComponent _detailsPanel;
    private String texto;
    private Exception mensaje;

    public ErrorDialog(String titulo, String texto, Exception mensaje) throws HeadlessException {
        super((Frame) null, titulo);
        this.texto = texto;
        this.mensaje = mensaje;
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public JComponent createBannerPanel() {
        return null;
    }

    public JComponent createDetailsPanel() {
        JTextArea textArea = new JTextArea(parseException());
        textArea.setRows(10);

        JLabel label = new JLabel("Detalless:");

        JPanel panel = new JPanel(new BorderLayout(6, 6));
        panel.add(new JScrollPane(textArea));
        panel.add(label, BorderLayout.BEFORE_FIRST_LINE);
        label.setLabelFor(textArea);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        return panel;
    }

    private String parseException() {
        StringBuilder sb = new StringBuilder(mensaje.toString());
        for (StackTraceElement ste : mensaje.getStackTrace()) {
            sb.append("\n\tat ");
            sb.append(ste);
        }
        String trace = sb.toString();
        return trace;
    }

    @Override
    protected StandardDialogPane createStandardDialogPane() {
        DefaultStandardDialogPane dialogPane = new DefaultStandardDialogPane() {
            @Override
            protected void layoutComponents(Component bannerPanel, Component contentPanel, ButtonPanel buttonPanel) {
                setLayout(new JideBoxLayout(this, BoxLayout.Y_AXIS));
                if (bannerPanel != null) {
                    add(bannerPanel);
                }
                if (contentPanel != null) {
                    add(contentPanel);
                }
                add(buttonPanel, JideBoxLayout.FIX);
                _detailsPanel = createDetailsPanel();
                add(_detailsPanel, JideBoxLayout.VARY);
                _detailsPanel.setVisible(false);
            }
        };
        return dialogPane;
    }

    @Override
    public JComponent createContentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 40, 40));

        JLabel label = new JLabel(texto);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    @Override
    public ButtonPanel createButtonPanel() {
        ButtonPanel buttonPanel = new ButtonPanel();
        JButton closeButton = new JButton();
        JButton detailButton = new JButton();
        detailButton.setMnemonic('D');
        closeButton.setName(OK);
        buttonPanel.addButton(closeButton, ButtonPanel.AFFIRMATIVE_BUTTON);
        buttonPanel.addButton(detailButton, ButtonPanel.OTHER_BUTTON);

        closeButton.setAction(new AbstractAction("Cerrar") {
            public void actionPerformed(ActionEvent e) {
                setDialogResult(RESULT_AFFIRMED);
                setVisible(false);
                dispose();
            }
        });

        detailButton.setAction(new AbstractAction("Detalles >>") {
            public void actionPerformed(ActionEvent e) {
                if (_detailsPanel.isVisible()) {
                    _detailsPanel.setVisible(false);
                    putValue(Action.NAME, "Detalles <<");
                    pack();
                } else {
                    _detailsPanel.setVisible(true);
                    putValue(Action.NAME, "<< Detalles");
                    pack();
                }
            }
        });

        setDefaultCancelAction(closeButton.getAction());
        setDefaultAction(closeButton.getAction());
        getRootPane().setDefaultButton(closeButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setSizeConstraint(ButtonPanel.NO_LESS_THAN); // since the checkbox is quite wide, we don't want all of them have the same size.
        return buttonPanel;
    }
}
