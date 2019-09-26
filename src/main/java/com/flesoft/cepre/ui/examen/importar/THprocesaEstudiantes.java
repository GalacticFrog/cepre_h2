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
import com.flesoft.cepre.dao.EstudianteDao;
import com.flesoft.cepre.model.AulaE;
import com.flesoft.cepre.model.AulaExamen;
import com.flesoft.cepre.model.Estudiante;
import com.flesoft.cepre.util.AppContext;
import com.flesoft.cepre.util.Config;
import javax.swing.SwingWorker;
import java.util.List;
import javax.swing.JLabel;
import com.flesoft.cepre.dao.AulaExamenDao;

import com.flesoft.cepre.dao.ExamenDao;

import com.flesoft.cepre.model.Examen;
import com.flesoft.cepre.ui.Main;
import com.flesoft.cepre.ui.util.ProgressGlassPane;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.oxbow.swingbits.dialog.task.TaskDialogs;

/**
 *
 * @author jomaveger
 */
public class THprocesaEstudiantes extends SwingWorker<Void, Integer> {

    // private final JButton btnPestudiantes;
    //private final JLabel indicador;
    private static final Log LOGGER = LogFactory.getLog(THprocesaEstudiantes.class);
    private ProgressGlassPane indicador;
    private final List<Data> datas;
    private final long examen_id;
    private final JTextArea txtaSalida;

    public THprocesaEstudiantes(List<Data> datas, ProgressGlassPane indicador, long examen_id, JTextArea txtaSalida) {
        this.datas = datas;
        this.indicador = indicador;
        // this.btnPestudiantes = btnPestudiantes;
        this.examen_id = examen_id;
        this.txtaSalida = txtaSalida;
    }

    @Override
    protected Void doInBackground() {
        int id_sede = Config.SEDE;
        String id_ciclo = Config.CICLO;

        AulaExamenDao aexDao = AppContext.getInstance().getBean(AulaExamenDao.class);
        EstudianteDao estDao = AppContext.getInstance().getBean(EstudianteDao.class);
        AulaEDao aeDao = AppContext.getInstance().getBean(AulaEDao.class);

        ExamenDao examDao = AppContext.getInstance().getBean(ExamenDao.class);

        Examen examen = examDao.buscarPorId(examen_id);
        //estudiantes
        txtaSalida.setText("");
        ArrayList<Long> insertados = new ArrayList<>();//contador de estudiantes insertados
        ArrayList<Data> insertadosDLG = new ArrayList<>();

        ArrayList<Long> ignorados = new ArrayList<>();//contador de estudiantes ignorados
        ArrayList<Data> ignoradosDLG = new ArrayList<>();

        ArrayList<String> sinDni = new ArrayList<>();//contador de estudiantes ignorados
        ArrayList<Data> sinDniDLG = new ArrayList<>();

        LOGGER.info("#####  Insertando Estudiantes #####");
        datas.forEach((data) -> {
            String dni_ = data.getDni().trim();
            String aula_ = data.getAula().trim();
            //buscamos estudiante por dni
            Estudiante estudiante = estDao.buscarPorDni(dni_);
            AulaE aulaE = aeDao.buscarPorNombre(aula_);
            // System.out.println(aula_ + " %%%%%%%%%%%%%%%%%%%%%% AulaE=>" + aulaE);
            if (estudiante != null) {
                //buscamos si el estudiante ya fue insertado para este examnen
                long estudiante_id = estudiante.getId();
                long aulae_id = aulaE.getId();
                AulaExamen aulaExamen = aexDao.buscar(estudiante_id, aulae_id, this.examen_id);
                if (aulaExamen == null) {
                    LOGGER.info("NO EXISTE, insertamos: " + estudiante.getDni() + "->" + aulaE.getDenominacion());
                    txtaSalida.append("Guardado: " + estudiante.getDni() + " en aula->" + aulaE.getDenominacion() + "\n");
                    indicador.setMessage("Guardado: " + estudiante.getDni() + " en aula: " + aulaE.getDenominacion());
                    insertados.add(estudiante.getId());
                    insertadosDLG.add(data);
                    AulaExamen auex = new AulaExamen();
                    auex.setEstudiante(estudiante);
                    auex.setAulae(aulaE);
                    auex.setExamen(examen);
                    aexDao.guardar(auex);
                } else {
                    LOGGER.info("ya existe " + estudiante.getDni() + " en aula->" + aulaE.getDenominacion());
                    txtaSalida.append("Ya existe: " + estudiante.getDni() + " en aula->" + aulaE.getDenominacion() + "\n");
                    indicador.setMessage("Ya existe: " + estudiante.getDni() + " en aula: " + aulaE.getDenominacion());
                    ignorados.add(estudiante.getId());
                    ignoradosDLG.add(data);
                }
            } else {
                sinDni.add(dni_ + " : " + data.getNombres() + "\n");
                sinDniDLG.add(data);
            }

        });
        LOGGER.info("#####  Fin Insertando Estudiantes #####");
        txtaSalida.append("### RESUMEN ###\n");
        txtaSalida.append("PROCESADOS: " + datas.size() + " Estudiantes\n");
        txtaSalida.append("INSERTADOS: " + insertados.size() + " Estudiantes\n");
        txtaSalida.append("IGNORADOS: " + ignorados.size() + " Estudiantes\n");
        txtaSalida.append("### DNI NO ENCONTRADOS: " + sinDni.size() + " \n");
        LOGGER.info("#####  resumen #####");

        StringBuilder sb = new StringBuilder();
        sinDni.forEach((str) -> {
            sb.append(str);
        });
        txtaSalida.append(sb.toString());
        LOGGER.info(sb.toString());
        DLGResumen resumen = new DLGResumen(datas, insertadosDLG, ignoradosDLG, sinDniDLG);
        resumen.setVisible(true);
        //resumen.refrescarTablaUsuarios(datas);
        //JOptionPane.showMessageDialog(null, sb.toString(), "RESUMEN", JOptionPane.INFORMATION_MESSAGE);
        return null;
    }

    @Override
    protected void process(java.util.List<Integer> lista) {
        if (!isCancelled()) {
            Integer parteCompletada = lista.get(lista.size() - 1);
            //barraProgreso.setValue(parteCompletada.intValue());
            LOGGER.info(parteCompletada.intValue());
        }
    }

    @Override
    protected void done() {
        if (!isCancelled()) {
            try {
                get();
                indicador.deactivate();
            } catch (InterruptedException | ExecutionException e) {
                this.indicador.deactivate();
                TaskDialogs.showException(e, "ERROR", "Se produjo un error procesando el archivo.");
                e.printStackTrace();
            } catch (Exception ex) {
                this.indicador.deactivate();
                TaskDialogs.showException(ex, "ERROR", "Se produjo un error procesando el archivo.");
                ex.printStackTrace();
            }
            //this.btnPestudiantes.setEnabled(true);
            // this.indicador.deactivate();
            //txtaSalida.append("LISTO!!!");
        }
    }

}
