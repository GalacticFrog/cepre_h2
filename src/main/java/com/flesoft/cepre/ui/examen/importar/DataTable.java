package com.flesoft.cepre.ui.examen.importar;

import com.flesoft.cepre.ui.usuario.*;
import java.util.List;
import javax.swing.JTable;
import com.flesoft.cepre.model.Usuario;

public class DataTable extends JTable {

    private static final long serialVersionUID = 1L;

    private DataTableModel modelo;

    public DataTable() {
        this.modelo = new DataTableModel();
        setModel(modelo);
    }

    public Data getDataSelected() {
        int i = getSelectedRow();
        if (i < 0) {
            return null;
        }
        return modelo.getUsuarioAt(i);
    }

    public void reload(List<Data> datas) {
        modelo.reload(datas);
    }

    public DataTableModel getMmodelo() {
        return modelo;
    }
}
