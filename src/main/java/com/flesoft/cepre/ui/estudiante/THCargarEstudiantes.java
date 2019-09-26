/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.estudiante;

/**
 *
 * @author user
 */
import com.flesoft.cepre.dao.AulaEstudianteDao;
import com.flesoft.cepre.model.AulaEstudiante;
import com.flesoft.cepre.ui.util.ProgressGlassPane;
import com.flesoft.cepre.ui.util.TableColumnAdjuster;
import com.flesoft.cepre.util.AppContext;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import org.oxbow.swingbits.dialog.task.TaskDialogs;

/**
 *
 * @author verde
 */
public class THCargarEstudiantes extends SwingWorker<ArrayList<AulaEstudiante>, Integer> {

    private final EstudianteTable tablaDatos;
    private final JLabel lblTotal;
    private final long pabellon_id;
    private final long aula_id;
    private final boolean huella;
    private final ProgressGlassPane glassPane;

    public THCargarEstudiantes(EstudianteTable tablaDatos, long pabellon_id, long aula_id, boolean huella, JLabel lblTotal, ProgressGlassPane glassPane) {
        this.tablaDatos = tablaDatos;
        this.pabellon_id = pabellon_id;
        this.aula_id = aula_id;
        this.huella = huella;
        this.lblTotal = lblTotal;
        this.glassPane = glassPane;
    }

    @Override
    protected ArrayList<AulaEstudiante> doInBackground() {

        AulaEstudianteDao aedao = AppContext.getInstance().getBean(AulaEstudianteDao.class);
        List<AulaEstudiante> datas = new ArrayList<>();
        if (pabellon_id != 0) {
            if (aula_id == 0) {
                datas = aedao.listarPorPabellon(pabellon_id, huella);
            } else {
                datas = aedao.listarPorAula(aula_id, huella);
            }
        } else {
            datas = aedao.listar();
        }
        return (ArrayList<AulaEstudiante>) datas;
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
                ArrayList<AulaEstudiante> results = get();
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
        String str_total = String.valueOf(total);
        lblTotal.setText(str_total);
    }
}
