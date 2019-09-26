package com.flesoft.cepre.ui.examen;

import com.flesoft.cepre.model.Examen;
import com.flesoft.cepre.ui.usuario.*;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.flesoft.cepre.model.Usuario;
import java.util.Date;

public class ExamenTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private List<Examen> examens;

    private String[] colNomes = {"EXAMEN", "FECHA", "ESTADO"};

    private Class<?>[] colTipos = {String.class, Date.class, String.class, String.class};

    public ExamenTableModel() {
    }

    public void reload(List<Examen> examens) {
        this.examens = examens;
        //actualiza el componente
        fireTableDataChanged();
    }

    @Override
    public Class<?> getColumnClass(int col) {
        return colTipos[col];
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int col) {
        return colNomes[col];
    }

    @Override
    public int getRowCount() {
        if (examens == null) {
            return 0;
        }
        return examens.size();
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        Examen examen = examens.get(linha);
        switch (coluna) {
            case 0:
                return examen.getNombre();
            case 1:
                return examen.getFecha();

            case 2:
                return examen.isAbierto() ? "ABIERTO" : "CERRADO";
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public Examen getExamenAt(int index) {
        return examens.get(index);
    }

}
