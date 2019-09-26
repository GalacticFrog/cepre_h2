package com.flesoft.cepre.ui.personal;

import com.flesoft.cepre.model.Docente;
import com.flesoft.cepre.model.Personal;
import java.util.List;
import javax.swing.JTable;

public class PersonalTable extends JTable {

    private static final long serialVersionUID = 1L;

    private PersonalTableModel modelo;

    public PersonalTable() {
        modelo = new PersonalTableModel();
        setModel(modelo);
    }

    public Personal getUsuarioSelected() {
        int i = getSelectedRow();
        if (i < 0) {
            return null;
        }
        return modelo.getUsuarioAt(i);
    }

    public void reload(List<Personal> usuarios) {
        modelo.reload(usuarios);
    }
}
