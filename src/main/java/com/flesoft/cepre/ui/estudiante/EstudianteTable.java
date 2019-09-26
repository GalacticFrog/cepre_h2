package com.flesoft.cepre.ui.estudiante;

import com.flesoft.cepre.model.AulaEstudiante;
import java.util.List;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;

public class EstudianteTable extends JTable {

    private static final long serialVersionUID = 1L;

    private final EstudianteTableModel modelo;

    public EstudianteTable() {
        modelo = new EstudianteTableModel();
        setModel(modelo);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public AulaEstudiante getAulaEstudianteSelected() {
        int i = getSelectedRow();
        if (i < 0) {
            return null;
        }
        return modelo.getAulaEstudianteAt(i);
    }

    public void reload(List<AulaEstudiante> estudiantes) {
        modelo.reload(estudiantes);
    }

//    @Override
//    public void setDefaultRenderer(Class<?> columnClass, TableCellRenderer renderer) {
//        super.setDefaultRenderer(columnClass, renderer); //To change body of generated methods, choose Tools | Templates.
//    }
}
