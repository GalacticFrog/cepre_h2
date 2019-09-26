/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.personal;

/**
 *
 * @author user
 */
import com.flesoft.cepre.dao.AsistenciaExamenDao;
import com.flesoft.cepre.dao.AsistenciaPersonalDao;
import com.flesoft.cepre.model.AsistenciaExamen;
import com.flesoft.cepre.model.AsistenciaPersonal;
import com.flesoft.cepre.ui.util.ProgressGlassPane;
import com.flesoft.cepre.ui.util.TableColumnAdjuster;
import com.flesoft.cepre.util.AppContext;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import org.oxbow.swingbits.dialog.task.TaskDialogs;

/**
 *
 * @author jomaveger
 */
public class THFiltrarAPersonal extends SwingWorker<List<AsistenciaPersonal>, Integer> {

    private final APersonalTable tablaDatos;
    private final JLabel lblTotal;
    private JButton btnVistaPrevia;
    private final ProgressGlassPane glassPane;
    private long examen_id;
    private long cargo_id;

    public THFiltrarAPersonal(long examen_id, long cargo_id, APersonalTable tablaDatos, JLabel lblTotal, ProgressGlassPane glassPane, JButton btnVistaPrevia) {
        this.examen_id = examen_id;
        this.cargo_id = cargo_id;
        this.tablaDatos = tablaDatos;
        this.lblTotal = lblTotal;
        this.glassPane = glassPane;
        this.btnVistaPrevia = btnVistaPrevia;
    }

    @Override
    protected List<AsistenciaPersonal> doInBackground() {
        AsistenciaPersonalDao aexDao = AppContext.getInstance().getBean(AsistenciaPersonalDao.class);

        return (cargo_id == 0) ? aexDao.listarPorExamen(examen_id) : aexDao.listarPorExamenCargo(examen_id, cargo_id);
    }

    @Override
    protected void process(java.util.List<Integer> lista) {
        if (!isCancelled()) {
            Integer parteCompletada = lista.get(lista.size() - 1);
            System.out.println(parteCompletada.intValue());
        }
    }

    @Override
    protected void done() {
        if (!isCancelled()) {
            try {
                List<AsistenciaPersonal> results = get();
                tablaDatos.reload(results);
                imprimeTotal(results.size());
                TableColumnAdjuster tca = new TableColumnAdjuster(tablaDatos);
                tca.adjustColumns();
                glassPane.deactivate();
//                glassPane.setProgress(0);
                //TaskDialogs.inform(null, "Informacion!", "Leido: " + results.size() + " estudiantes.");
            } catch (ExecutionException | InterruptedException eex) {
                glassPane.deactivate();
                TaskDialogs.showException(eex, "ERROR", "Se produjo un error.");
                eex.printStackTrace();
            }
        }
    }

    private void imprimeTotal(int total) {
        btnVistaPrevia.setEnabled(total > 0);
        String str_total = String.valueOf(total);
        lblTotal.setText(str_total + " registros");
    }

}
