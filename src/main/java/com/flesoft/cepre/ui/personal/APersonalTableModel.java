package com.flesoft.cepre.ui.personal;

import com.flesoft.cepre.model.AsistenciaExamen;
import com.flesoft.cepre.model.AsistenciaPersonal;
import com.flesoft.cepre.ui.util.DateUtils;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class APersonalTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private List<AsistenciaPersonal> asistencias;

    private String[] colNomes = {"APELLIDOS Y NOMBRES", "DNI", "CARGO", "EXAMEN", "INGRESO", "SALIDA"};

    private Class<?>[] colTipos = {String.class, String.class, String.class, String.class, String.class, String.class};

    public APersonalTableModel() {
    }

    public void reload(List<AsistenciaPersonal> asistencias) {
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
        AsistenciaPersonal asistencia = asistencias.get(linha);
        switch (coluna) {
            case 0:
                return asistencia.getPersonal().getPersona().getApellidos() + ", " + asistencia.getPersonal().getPersona().getNombres();
            case 1:
                return asistencia.getPersonal().getPersona().getDni();
            case 2:
                return asistencia.getCargo().getDenominacion();
            case 3:
                return asistencia.getExamen().getNombre();
//            case 4:
//                return asistencia.getFecha();
            case 4:
                return DateUtils.localDateTime(asistencia.getEntrada());
            case 5:
                return DateUtils.localDateTime(asistencia.getSalida());
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public AsistenciaPersonal getAsistenciaExamenAt(int index) {
        return asistencias.get(index);
    }

}
