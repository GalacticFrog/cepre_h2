/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

/**
 *
 * @author user
 */
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

public class VentanaPrincipal extends JFrame implements ActionListener {

    private JButton botonInicio, botonParada;
    private JScrollPane panelDesplazamiento;
    private JList lista;
    private DefaultListModel modeloLista;
    private JProgressBar barraProgreso;
    private GeneraPrimos generaPrimos;

    public VentanaPrincipal() {
        super("Multitarea Swing");
        this.panelDesplazamiento = new JScrollPane();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new FlowLayout());
        this.botonInicio = this.construyeBoton("Iniciar");
        this.botonParada = this.construyeBoton("Parar");
        this.botonParada.setEnabled(false);
        this.barraProgreso = this.construyeBarraProgreso(0, 99);
        this.modeloLista = new DefaultListModel();
        this.lista = new JList(this.modeloLista);
        this.panelDesplazamiento.setViewportView(this.lista);
        this.getContentPane().add(this.panelDesplazamiento);
        this.pack();
        this.setVisible(true);
    }

    private JButton construyeBoton(String titulo) {
        JButton b = new JButton(titulo);
        b.setActionCommand(titulo);
        b.addActionListener(this);
        this.getContentPane().add(b);
        return b;
    }

    private JProgressBar construyeBarraProgreso(int min, int max) {
        JProgressBar progressBar = new JProgressBar();
        progressBar.setMinimum(min);
        progressBar.setMaximum(max);
        progressBar.setStringPainted(true);
        progressBar.setBorderPainted(true);
        this.getContentPane().add(progressBar);
        return progressBar;
    }

    public void actionPerformed(ActionEvent e) {
        if ("Iniciar".equals(e.getActionCommand())) {
            this.modeloLista.clear();
            this.botonInicio.setEnabled(false);
            this.botonParada.setEnabled(true);
            this.generaPrimos = new GeneraPrimos(this.modeloLista, this.barraProgreso, this.botonInicio, this.botonParada);
            this.generaPrimos.execute();
        } else if ("Parar".equals(e.getActionCommand())) {
            this.botonInicio.setEnabled(true);
            this.botonParada.setEnabled(false);
            this.generaPrimos.cancel(true);
            this.generaPrimos = null;
        }
    }
}
