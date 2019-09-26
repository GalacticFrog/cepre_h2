/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Carmen
 */
public class Jsoupdemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("http://aplicaciones007.jne.gob.pe/srop_publico/Consulta/Afiliado/GetNombresCiudadano?DNI=25325211").get();
        String data = doc.text();
        String[] separated = data.split("\\|");
        System.out.println("apellidos:" + separated[0] + " " + separated[1]);
        System.out.println("nombres:" + separated[2]);
    }

}
