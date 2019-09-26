/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

/**
 *
 * @author user
 */
import com.digitalpersona.uareu.Fmd;
import com.digitalpersona.uareu.UareUException;
import com.digitalpersona.uareu.UareUGlobal;
import com.flesoft.cepre.dao.HuellaDao;
import com.flesoft.cepre.model.Huella;
import com.flesoft.cepre.ui.util.MessageBox;
import com.flesoft.cepre.util.AppContext;
import com.flesoft.cepre.util.Config;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 *
 * @author jomaveger
 */
public class GeneraPrimos extends SwingWorker<ArrayList<Huella>, Integer> {

    private DefaultListModel modeloLista;
    private JProgressBar barraProgreso;
    private JButton botonInicio, botonParada;

    public List<Huella> m_listOfRecords = new ArrayList<>();

    public GeneraPrimos(DefaultListModel modeloLista, JProgressBar barraProgreso, JButton botonInicio, JButton botonParada) {
        this.modeloLista = modeloLista;
        this.barraProgreso = barraProgreso;
        this.botonInicio = botonInicio;
        this.botonParada = botonParada;
    }

    protected ArrayList<Huella> doInBackground() throws SQLException {
        ///////////

        String ciclo_id = Config.CICLO;
        HuellaDao huellaDao = AppContext.getInstance().getBean(HuellaDao.class);
        List<Huella> huellas = huellaDao.listar();

        Huella valorTemp = new Huella();
        ArrayList<Huella> lista = new ArrayList<Huella>();

        int index = 0;
//        for (Huella huella : huellas) {
//            Blob blob = huella.getHuella();
//            int blobLength = (int) blob.length();
//            byte[] bBytes = blob.getBytes(1, blobLength);
//
//            if (!isCancelled()) {
//                valorTemp = huella;
//            }
////            for (int i = 0; i < 1000; i++) {
////                System.out.print(".");
////            }
//            publish(new Integer(index));
//            lista.add(valorTemp);
//
//            index++;
//        }

        ///////////
//        Integer valorTemp = new Integer(1);
//        ArrayList<Integer> lista = new ArrayList<Integer>();
//        for (int i = 0; i < 100; i++) {
////            for (int j = 0; j < 100000 && !isCancelled(); j++) {
////                valorTemp = encuentraSiguientePrimo(valorTemp.intValue());
////            }
//            publish(new Integer(i));
//            lista.add(valorTemp);
//        }
        return lista;
    }

    @Override
    protected void process(java.util.List<Integer> lista) {
        if (!isCancelled()) {
            Integer parteCompletada = lista.get(lista.size() - 1);
            barraProgreso.setValue(parteCompletada.intValue());
            System.out.println(parteCompletada.intValue());
        }
    }

    @Override
    protected void done() {
        if (!isCancelled()) {
            try {
                ArrayList<Huella> results = get();
                for (Huella i : results) {
                    modeloLista.addElement(i.toString());
                }
                this.botonInicio.setEnabled(true);
                this.botonParada.setEnabled(false);
            } catch (ExecutionException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

//    private Integer encuentraSiguientePrimo(int num) {
//        do {
//            if (num % 2 == 0) {
//                num++;
//            } else {
//                num = num + 2;
//            }
//        } while (!esPrimo(num));
//        return new Integer(num);
//    }
//
//    private boolean esPrimo(int num) {
//        int i;
//        for (i = 2; i <= num / 2; i++) {
//            if (num % i == 0) {
//                return false;
//            }
//        }
//        return true;
//    }
}
