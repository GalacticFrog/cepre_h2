package com.flesoft.cepre.ui.examen;

import com.flesoft.cepre.model.AsistenciaExamen;
import com.flesoft.cepre.ui.usuario.*;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.flesoft.cepre.model.Usuario;

public class AExamenTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private List<AsistenciaExamen> asistencias;

    private String[] colNomes = {"Alumno", "DNI", "Escuela", "Aula", "Pabellon", "Examen"};

    private Class<?>[] colTipos = {String.class, String.class, String.class, String.class, String.class, String.class};

    public AExamenTableModel() {
    }

    public void reload(List<AsistenciaExamen> asistencias) {
        this.asistencias = asistencias;
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
        if (asistencias == null) {
            return 0;
        }
        return asistencias.size();
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        AsistenciaExamen asistencia = asistencias.get(linha);
        switch (coluna) {
            case 0:
                return asistencia.getAulaExamen().getEstudiante().getCompleto();
            case 1:
                return asistencia.getAulaExamen().getEstudiante().getDni();
            case 2:
                return asistencia.getAulaExamen().getEstudiante().getEscuela().getNombreEscuela();
            case 3:
                return asistencia.getAulaExamen().getAulae().getDenominacion();
            case 4:
                return asistencia.getAulaExamen().getAulae().getPabellone().getDenominacion();
            case 5:
                return asistencia.getAulaExamen().getExamen().getNombre();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public AsistenciaExamen getAsistenciaExamenAt(int index) {
        return asistencias.get(index);
    }

}
