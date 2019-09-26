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
import com.flesoft.cepre.ui.util.ProgressGlassPane;
import com.flesoft.cepre.ui.util.TableColumnAdjuster;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import org.oxbow.swingbits.dialog.task.TaskDialogs;

/**
 *
 * @author jomaveger
 */
public class THleerExcel extends SwingWorker<ArrayList<Data>, Integer> {

    private DataTable tablaDatos;
    private JProgressBar barraProgreso;
    private JButton botonInicio, botonParada;
    //private JLabel indicador;
    private ProgressGlassPane indicador;
    private String excelFilePath;
    private JButton btnPaulaPab;
    private JTextArea txtaSalida;

    public THleerExcel(ProgressGlassPane indicador, String excelFilePath, DataTable tablaDatos, JButton botonInicio, JButton btnPaulaPab, JTextArea txtaSalida) {
        this.excelFilePath = excelFilePath;
        this.tablaDatos = tablaDatos;
        this.indicador = indicador;
        this.botonInicio = botonInicio;
        this.btnPaulaPab = btnPaulaPab;
        this.txtaSalida = txtaSalida;
    }

    @Override
    protected ArrayList<Data> doInBackground() throws FileNotFoundException, IOException {

        ArrayList<Data> datas = new ArrayList<>();
        FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();

        int index = 0;
        txtaSalida.setText("");
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            int rowIndex = nextRow.getRowNum();
            Data data = new Data();
            if (rowIndex >= 4) {
                System.out.print("[columna: " + rowIndex + "] ");
                while (cellIterator.hasNext()) {
                    Cell nextCell = cellIterator.next();
                    int columnIndex = nextCell.getColumnIndex();
                    switch (columnIndex) {
                        case 0:
                            Double numero = (double) getCellValue(nextCell);
                            data.setNumero(numero.intValue());
                            System.out.print("num->" + numero.intValue());
                            txtaSalida.append("Leido: #" + numero.intValue());
                            break;
                        case 1:
                            data.setNombres((String) getCellValue(nextCell));
                            System.out.print(getCellValue(nextCell));
                            txtaSalida.append(" - " + getCellValue(nextCell));
                            break;
                        case 2:
                            data.setDni((String) getCellValue(nextCell));
                            System.out.print(getCellValue(nextCell));
                            txtaSalida.append(" - " + getCellValue(nextCell));
                            break;
                        case 3:
                            data.setAula((String) getCellValue(nextCell));
                            System.out.print(getCellValue(nextCell));
                            txtaSalida.append(" - " + getCellValue(nextCell) + "\n");
                            break;
                        case 4:
                            data.setPabellon((String) getCellValue(nextCell));
                            System.out.println(getCellValue(nextCell));
                            //txtaSalida.append(" - Pabellon:->" + getCellValue(nextCell) + "\n");
                            break;
                    }
                }

                datas.add(data);
            }
            index++;
        }

        workbook.close();
        inputStream.close();

        return datas;

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
                ArrayList<Data> results = get();
                tablaDatos.reload(results);
                TableColumnAdjuster tca = new TableColumnAdjuster(tablaDatos);
                tca.adjustColumns();
//                for (Data i : results) {
//                    modeloLista.addElement(i.toString());
//                }
                TaskDialogs.inform(null, "Informacion!", "Leido: " + results.size() + " estudiantes.");
                this.botonInicio.setEnabled(true);
                this.btnPaulaPab.setEnabled(true);
                indicador.deactivate();

            } catch (ExecutionException | InterruptedException eex) {
                indicador.deactivate();
                TaskDialogs.showException(eex, "ERROR", "Se produjo un error.");
                eex.printStackTrace();
            }
        }
    }

    private Object getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();

            case BOOLEAN:
                return cell.getStringCellValue();

            case NUMERIC:
                return cell.getNumericCellValue();
        }

        return null;
    }
}
