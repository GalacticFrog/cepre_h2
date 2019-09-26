package com.flesoft.cepre.ui.usuario;

import java.util.List;
import javax.swing.JTable;
import com.flesoft.cepre.model.Usuario;

public class UsuarioTable extends JTable {

    private static final long serialVersionUID = 1L;

    private UsuarioTableModel modelo;

    public UsuarioTable() {
        modelo = new UsuarioTableModel();
        setModel(modelo);
    }

    public Usuario getUsuarioSelected() {
        int i = getSelectedRow();
        if (i < 0) {
            return null;
        }
        return modelo.getUsuarioAt(i);
    }

    public void reload(List<Usuario> usuarios) {
        modelo.reload(usuarios);
    }
}
