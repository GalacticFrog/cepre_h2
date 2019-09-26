/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.examen.importar;

import javax.swing.SwingUtilities;

/**
 *
 * @author user
 */
public class Threads {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Importar().setVisible(true);
            }
        });
    }

}
