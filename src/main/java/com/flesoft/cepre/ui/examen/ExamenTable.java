package com.flesoft.cepre.ui.examen;

import com.flesoft.cepre.model.Examen;
import java.util.List;
import javax.swing.JTable;

public class ExamenTable extends JTable {

    private static final long serialVersionUID = 1L;

    private ExamenTableModel modelo;

    public ExamenTable() {
        modelo = new ExamenTableModel();
        setModel(modelo);
    }

    public Examen getExamenSelected() {
        int i = getSelectedRow();
        if (i < 0) {
            return null;
        }
        return modelo.getExamenAt(i);
    }

    public void reload(List<Examen> examen) {
        modelo.reload(examen);
    }
}
