/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import com.flesoft.cepre.util.Config;
import java.io.File;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

/**
 *
 * @author user
 */
public class cfg {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("c:" + Config.CICLO);
        System.out.println("p:" + Config.PABELLONE);
        System.out.println("s:" + Config.SEDE);
        //Config.setVars();

        System.out.println(":" + Config.CICLO);
        System.out.println(":" + Config.PABELLONE);
        System.out.println(":" + Config.SEDE);
    }

}
