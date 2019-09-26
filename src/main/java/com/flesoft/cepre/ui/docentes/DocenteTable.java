package com.flesoft.cepre.ui.docentes;

import com.flesoft.cepre.ui.personal.*;
import com.flesoft.cepre.model.Docente;
import java.util.List;
import javax.swing.JTable;

public class DocenteTable extends JTable {

    private static final long serialVersionUID = 1L;

    private DocenteTableModel modelo;

    public DocenteTable() {
        modelo = new DocenteTableModel();
        setModel(modelo);
    }

    public Docente getUsuarioSelected() {
        int i = getSelectedRow();
        if (i < 0) {
            return null;
        }
        return modelo.getUsuarioAt(i);
    }

    public void reload(List<Docente> usuarios) {
        modelo.reload(usuarios);
    }
}
