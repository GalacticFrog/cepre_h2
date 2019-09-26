package com.flesoft.cepre.ui.estudiante;

import com.flesoft.cepre.model.AsistenciaExamen;
import com.flesoft.cepre.model.AulaEstudiante;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class EstudianteTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private List<AulaEstudiante> estudiantes;

    private final String[] colNomes = {"Estudiante", "DNI", "Escuela", "Aula", "Turno", "Huella"};

    private final Class<?>[] colTipos = {String.class, String.class, String.class, String.class, String.class, Boolean.class};

    public EstudianteTableModel() {
    }

    public void reload(List<AulaEstudiante> estudiantes) {
        this.estudiantes = estudiantes;
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
        if (estudiantes == null) {
            return 0;
        }
        return estudiantes.size();
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        AulaEstudiante estudiante = estudiantes.get(linha);
        switch (coluna) {
            case 0:
                return estudiante.getEstudiante().getCompleto();
            case 1:
                return estudiante.getEstudiante().getDni();
            case 2:
                return estudiante.getEstudiante().getEscuela().getNombreEscuela();
            case 3:
                return estudiante.getAula().getDenominacion();
            case 4:
                return "m".equals(estudiante.getTurno()) ? "Ma\u00f1ana" : "Tarde";
            case 5:
                return estudiante.getHuella() != null;
            default:
                return "NN";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public AulaEstudiante getAulaEstudianteAt(int index) {
        return estudiantes.get(index);
    }

}
