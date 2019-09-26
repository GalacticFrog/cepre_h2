package com.flesoft.cepre.ui.docentes;

import com.flesoft.cepre.ui.personal.*;
import com.flesoft.cepre.model.Docente;
import com.flesoft.cepre.ui.usuario.*;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.flesoft.cepre.model.Usuario;

public class DocenteTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private List<Docente> docentes;

    private String[] colNomes = {"#", "DNI", "Nombre", "Apellidos", "Sede", "Huella"};

    private Class<?>[] colTipos = {Integer.class, String.class, String.class, String.class, String.class, String.class};

    public DocenteTableModel() {
    }

    public void reload(List<Docente> docentes) {
        this.docentes = docentes;
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
        if (docentes == null) {
            return 0;
        }
        return docentes.size();
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        Docente docente = docentes.get(linha);
        switch (coluna) {
            case 0:
                return docente.getId();
            case 1:
                return docente.getPersona().getDni();
            case 2:
                return docente.getPersona().getNombres();
            case 3:
                return docente.getPersona().getApellidos();
            case 4:
                return docente.getSede();
            case 5:
                return docente.getHuella() == null ? "Sin Huella" : "Registrado";
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Docente getUsuarioAt(int index) {
        return docentes.get(index);
    }

}
