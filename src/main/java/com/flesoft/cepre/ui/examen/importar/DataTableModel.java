package com.flesoft.cepre.ui.examen.importar;

import com.flesoft.cepre.ui.usuario.*;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.flesoft.cepre.model.Usuario;

public class DataTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private List<Data> data;

    private String[] colNomes = {"#", "Nombres", "DNI", "Aula", "Pabellon"};

    private Class<?>[] colTipos = {Integer.class, String.class, String.class, String.class, String.class};

    public DataTableModel() {
    }

    public void reload(List<Data> usuarios) {
        this.data = usuarios;
        //actualiza el componente
        fireTableDataChanged();
    }

    @Override
    public Class<?> getColumnClass(int col) {
        return colTipos[col];
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int col) {
        return colNomes[col];
    }

    @Override
    public int getRowCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        Data data = this.data.get(linha);
        switch (coluna) {
            case 0:
                return data.getNumero();
            case 1:
                return data.getNombres();
            case 2:
                return data.getDni();
            case 3:
                return data.getAula();
            case 4:
                return data.getPabellon();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Data getUsuarioAt(int index) {
        return data.get(index);
    }

    public List<Data> getData() {
        return data;
    }
}
