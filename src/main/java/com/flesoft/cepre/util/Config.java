/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.util;

import java.io.File;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 *
 * @author edrev
 */
public class Config {

    public static int SEDE = 1;
    public static String CICLO = "";
    public static int PABELLON = 1;//pabellone de clases !=pabellon de examen
    public static int PABELLONE = 1;//pabellone de examen !=pabellon de clases
    public static String HTTP = "";
    public static String TITULO = "";
    public static String DIR_FOTOS = "";
    public static String DIR_HUELLAS = "";
    public static String HOST = "";

    public static void setVars() {
        Configurations configs = new Configurations();
        try {
            Configuration config = configs.properties(new File("config.properties"));
            int sede = config.getInt("app.sede");
            String ciclo = config.getString("app.ciclo");
            int pabellon = config.getInt("app.pabellon");
            int pabellone = config.getInt("app.pabellone");

            SEDE = sede;
            CICLO = ciclo;
            PABELLON = pabellon;
            PABELLONE = pabellone;
            HTTP = config.getString("app.http");
            TITULO = config.getString("app.titulo");
            DIR_FOTOS = config.getString("app.dir_fotos");
            DIR_HUELLAS = config.getString("app.dir_huellas");
            HOST = config.getString("db.host");
            System.out.println("######### CONGIGURACION CARGADA #########");
            System.out.println(":SEDE:" + sede);
            System.out.println(":CICLO:" + ciclo);
            System.out.println(":PABELLON C:" + pabellon);
            System.out.println(":PABELLON E:" + pabellone);

            System.out.println("######### CONGIGURACION CARGADA #########");
        } catch (ConfigurationException cex) {
            cex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
