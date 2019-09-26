package com.flesoft.cepre.ui.examen;

import com.flesoft.cepre.model.AsistenciaExamen;
import com.flesoft.cepre.model.AulaExamen;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class DistribucionTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private List<AulaExamen> aulaExamen;

    private String[] colNomes = {"Alumno", "DNI", "Escuela", "Aula", "Pabellon", "Examen"};

    private Class<?>[] colTipos = {String.class, String.class, String.class, String.class, String.class, String.class};

    public DistribucionTableModel() {
    }

    public void reload(List<AulaExamen> asistencias) {
        this.aulaExamen = asistencias;
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
        if (aulaExamen == null) {
            return 0;
        }
        return aulaExamen.size();
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        AulaExamen asistencia = aulaExamen.get(linha);
        switch (coluna) {
            case 0:
                return asistencia.getEstudiante().getCompleto();
            case 1:
                return asistencia.getEstudiante().getDni();
            case 2:
                return asistencia.getEstudiante().getEscuela().getNombreEscuela();
            case 3:
                return asistencia.getAulae().getDenominacion();
            case 4:
                return asistencia.getAulae().getPabellone().getDenominacion();
            case 5:
                return asistencia.getExamen().getNombre();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public AulaExamen getAsistenciaExamenAt(int index) {
        return aulaExamen.get(index);
    }

}
