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
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import org.oxbow.swingbits.dialog.task.TaskDialogs;

/**
 *
 * @author jomaveger
 */
public class THPaginarAPersonal extends SwingWorker<List<AsistenciaPersonal>, Integer> {

    private final APersonalTable tablaDatos;
    private final JLabel lblTotal;
    private final JLabel lblPagina;
    private final int pagina;
    private final ProgressGlassPane glassPane;
    private final Map<String, JButton> botones;

    private long total;

    public THPaginarAPersonal(APersonalTable tablaDatos, int pagina, JLabel lblPagina, JLabel lblTotal, ProgressGlassPane glassPane, Map<String, JButton> botones) {
        this.tablaDatos = tablaDatos;
        this.pagina = pagina;
        this.lblTotal = lblTotal;
        this.lblPagina = lblPagina;
        this.glassPane = glassPane;
        this.botones = botones;
    }

    @Override
    protected List<AsistenciaPersonal> doInBackground() {
        AsistenciaPersonalDao aexDao = AppContext.getInstance().getBean(AsistenciaPersonalDao.class);
        long total_ = aexDao.totalRecords();
        this.total = total_;
        List<AsistenciaPersonal> datas = aexDao.listar(pagina);

        return datas;
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
                imprimeTotal();
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

    private void imprimeTotal() {
        int paginas = (int) (Math.ceil(((double) total) / 100));
        String str_paginas = String.valueOf(paginas);
        String str_total = String.valueOf(total);
        JButton btnPrimero = botones.get("btnPrimero");
        JButton btnAnterior = botones.get("btnAnterior");
        JButton btnSiguiente = botones.get("btnSiguiente");
        JButton btnUltimo = botones.get("btnUltimo");
        if (pagina == 1) {
            btnPrimero.setEnabled(false);
            btnAnterior.setEnabled(false);
        } else {
            btnPrimero.setEnabled(true);
            btnAnterior.setEnabled(true);
        }
        if (pagina == paginas) {
            btnSiguiente.setEnabled(false);
            btnUltimo.setEnabled(false);
        } else {
            btnSiguiente.setEnabled(true);
            btnUltimo.setEnabled(true);
        }
        lblPagina.setText(pagina + " / " + str_paginas);
        lblTotal.setText(str_total + " registros");
    }

}
