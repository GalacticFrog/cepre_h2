/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.examen.importar;

/**
 *
 * @author user
 */
import com.flesoft.cepre.dao.AulaEDao;
import com.flesoft.cepre.dao.PabelloneDao;
import com.flesoft.cepre.dao.SedeDao;
import com.flesoft.cepre.model.AulaE;
import com.flesoft.cepre.model.PabellonE;
import com.flesoft.cepre.model.Sede;
import com.flesoft.cepre.ui.util.ProgressGlassPane;
import com.flesoft.cepre.util.AppContext;
import com.flesoft.cepre.util.Config;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.oxbow.swingbits.dialog.task.TaskDialogs;

/**
 *
 * @author jomaveger
 */
public class THprocesaAulasPabellones extends SwingWorker<Void, Integer> {

    private static final Log LOGGER = LogFactory.getLog(THprocesaAulasPabellones.class);
    private JProgressBar barraProgreso;
    private JButton btnProcesar;
    //private JButton btnPestudiantes;
    //private JLabel indicador;
    private ProgressGlassPane indicador;
    private LinkedHashSet<String> pabellones;
    private List<Data> datas;
    private JTextArea txtaSalida;

    public THprocesaAulasPabellones(List<Data> datas, LinkedHashSet<String> pabellones, ProgressGlassPane indicador, JButton btnProcesar, JTextArea txtaSalida) {
        this.datas = datas;
        this.pabellones = pabellones;
        this.indicador = indicador;
        this.btnProcesar = btnProcesar;
        // this.btnPestudiantes = btnPestudiantes;
        this.txtaSalida = txtaSalida;
    }

    @Override
    protected Void doInBackground() {
        int id_sede = Config.SEDE;
        PabelloneDao pd = AppContext.getInstance().getBean(PabelloneDao.class);
        SedeDao sd = AppContext.getInstance().getBean(SedeDao.class);

//        StringBuilder sb = new StringBuilder();
//        StringBuilder sbmain = new StringBuilder();
        //pabellones insertamos
        LOGGER.info("#####  Insertando pabellones  #####");
        txtaSalida.setText("");
        pabellones.forEach((String pabellon) -> {
            System.out.println("pabellon: " + pabellon);
            PabellonE pabellonDB = pd.buscarPorNombre(pabellon);
            System.out.print("pabellonDB: " + pabellonDB);
            if (pabellonDB == null) {
                Sede sede = sd.buscarPorId(id_sede);
                PabellonE pe = new PabellonE();
                pe.setDenominacion(pabellon);
                pe.setSede(sede);
                try {
                    pd.guardar(pe);
                    txtaSalida.append("Guardado: " + pe.getDenominacion() + "\n");
                    //sb.append("Creado pabellon: ").append(pe.getDenominacion()).append("\n");

                } catch (Exception e) {
                    System.out.println("no se pudo guardar: " + e.toString());
                }

            } else {
                System.out.println("ya existe: " + pabellonDB.getDenominacion());
                txtaSalida.append("Ya existe: " + pabellonDB.getDenominacion() + "\n");
            }
        });
        LOGGER.info("##### fin Insertando pabellones  #####");
        //System.out.println("dayas size" + datas.size());
        //aulas
        LOGGER.info("#####  Insertando aulas  #####");
        AulaEDao aeDao = AppContext.getInstance().getBean(AulaEDao.class);
        datas.forEach((data) -> {
            PabellonE pabellonDB = pd.buscarPorNombre(data.getPabellon().trim());
            System.out.println(pabellonDB.getDenominacion() + " == " + data.getPabellon());
            if (pabellonDB.getDenominacion().equals(data.getPabellon())) {
                AulaE aeDB = aeDao.buscarPorNombre(data.getAula().trim());
                if (aeDB == null) {
                    System.out.println("inserto: " + data.getAula() + " en-> " + pabellonDB.getDenominacion());
                    txtaSalida.append("Guardado: " + data.getAula() + " en-> " + pabellonDB.getDenominacion() + "\n");
                    //sb.append("Creado aula: ").append(data.getAula()).append(" en-> ").append(pabellonDB.getDenominacion()).append("\n");
                    AulaE aulaE = new AulaE();
                    aulaE.setDenominacion(data.getAula().trim());
                    aulaE.setCapacidad(20);
                    aulaE.setPabellone(pabellonDB);
                    aeDao.guardar(aulaE);
                } else {
                    System.out.println("Ya existe aula:" + data.getAula());
                    txtaSalida.append("Ya existe aula:" + data.getAula() + "\n");
                }
            }
        });
        LOGGER.info("#####  fin Insertando aulas  #####");
        //TaskDialogs.inform(null, "RESUMEN!", sb.toString());
        return null;
    }

    @Override
    protected void process(java.util.List<Integer> lista) {
        if (!isCancelled()) {
            Integer parteCompletada = lista.get(lista.size() - 1);
            //barraProgreso.setValue(parteCompletada.intValue());
            System.out.println(parteCompletada.intValue());
        }
    }

    @Override
    protected void done() {
        if (!isCancelled()) {
            try {
                get();
            } catch (InterruptedException | ExecutionException e) {
                this.indicador.deactivate();
                TaskDialogs.showException(e, "ERROR", "Se produjo un error procesando el archivo.");
                e.printStackTrace();
            } catch (Exception ex) {
                this.indicador.deactivate();
                TaskDialogs.showException(ex, "ERROR", "Se produjo un error procesando el archivo.");
                ex.printStackTrace();
            }
            //System.out.println("DONE!!");
            // this.btnPestudiantes.setEnabled(true);
            // this.indicador.deactivate();
            txtaSalida.append("LISTO!!!");
        }
    }

}
