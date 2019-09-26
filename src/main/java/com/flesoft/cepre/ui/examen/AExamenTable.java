package com.flesoft.cepre.ui.examen;

import com.flesoft.cepre.model.AsistenciaExamen;
import java.util.List;
import javax.swing.JTable;

public class AExamenTable extends JTable {

    private static final long serialVersionUID = 1L;

    private final AExamenTableModel modelo;

    public AExamenTable() {
        modelo = new AExamenTableModel();
        setModel(modelo);
    }

    public AsistenciaExamen getAsistenciaExamenSelected() {
        int i = getSelectedRow();
        if (i < 0) {
            return null;
        }
        return modelo.getAsistenciaExamenAt(i);
    }

    public void reload(List<AsistenciaExamen> asistencias) {
        modelo.reload(asistencias);
    }
}
