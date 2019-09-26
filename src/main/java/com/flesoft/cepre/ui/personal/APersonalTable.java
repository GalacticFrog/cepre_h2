package com.flesoft.cepre.ui.personal;

import com.flesoft.cepre.model.AsistenciaPersonal;
import java.util.List;
import javax.swing.JTable;

public class APersonalTable extends JTable {

    private static final long serialVersionUID = 1L;

    private final APersonalTableModel modelo;

    public APersonalTable() {
        modelo = new APersonalTableModel();
        setModel(modelo);
    }

    public AsistenciaPersonal getAsistenciaExamenSelected() {
        int i = getSelectedRow();
        if (i < 0) {
            return null;
        }
        return modelo.getAsistenciaExamenAt(i);
    }

    public void reload(List<AsistenciaPersonal> asistencias) {
        modelo.reload(asistencias);
    }
}
