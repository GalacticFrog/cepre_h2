package com.flesoft.cepre.ui.usuario;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.flesoft.cepre.model.Usuario;

public class UsuarioTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private List<Usuario> usuarios;

    private String[] colNomes = {"ID", "DNI", "Nombre", "Apellidos", "Telefono", "Direccion"};

    private Class<?>[] colTipos = {Integer.class, String.class, String.class, String.class, String.class, String.class};

    public UsuarioTableModel() {
    }

    public void reload(List<Usuario> usuarios) {
        this.usuarios = usuarios;
        //actualiza el componente
        fireTableDataChanged();
    }

    @Override
    public Class<?> getColumnClass(int col) {
        return colTipos[col];
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int col) {
        return colNomes[col];
    }

    @Override
    public int getRowCount() {
        if (usuarios == null) {
            return 0;
        }
        return usuarios.size();
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        Usuario usuario = usuarios.get(linha);
        switch (coluna) {
            case 0:
                return usuario.getId();
            case 1:
                return usuario.getUsuario();
            case 2:
                return usuario.getPersona().getNombres();
            case 3:
                return usuario.getPersona().getApellidos();
            case 4:
                return usuario.getPersona().getTelefono();
            case 5:
                return usuario.getPersona().getDireccion();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Usuario getUsuarioAt(int index) {
        return usuarios.get(index);
    }

}
