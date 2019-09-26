package com.flesoft.cepre.ui.examen;

import com.flesoft.cepre.model.AsistenciaExamen;
import com.flesoft.cepre.model.AulaExamen;
import java.util.List;
import javax.swing.JTable;

public class DistribucionTable extends JTable {

    private static final long serialVersionUID = 1L;

    private final DistribucionTableModel modelo;

    public DistribucionTable() {
        modelo = new DistribucionTableModel();
        setModel(modelo);
    }

    public AulaExamen getAulaExamenSelected() {
        int i = getSelectedRow();
        if (i < 0) {
            return null;
        }
        return modelo.getAsistenciaExamenAt(i);
    }

    public void reload(List<AulaExamen> asistencias) {
        modelo.reload(asistencias);
    }
}
