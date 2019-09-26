package com.flesoft.cepre.ui.personal;

import com.flesoft.cepre.model.Personal;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class PersonalTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private List<Personal> docentes;

    private String[] colNomes = {"DNI", "Apellidos y Nombres", "Telefono", "Cargo", "Sede", "Huella"};

    private Class<?>[] colTipos = {String.class, String.class, String.class, String.class, String.class, String.class};

    public PersonalTableModel() {
    }

    public void reload(List<Personal> docentes) {
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
        Personal personal = docentes.get(linha);
        switch (coluna) {
            case 0:
                return personal.getPersona().getDni();
            case 1:
                return personal.getPersona().getApellidos() + ", " + personal.getPersona().getNombres();
            case 2:
                return personal.getPersona().getTelefono();
            case 3:
                return personal.getCargo().getDenominacion();
            case 4:
                return personal.getSede();
            case 5:
                return personal.getHuella() == null ? "Sin Huella" : "Registrado";
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Personal getUsuarioAt(int index) {
        return docentes.get(index);
    }

}
