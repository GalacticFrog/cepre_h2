/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.ui.util;

import java.io.File;

/**
 *
 * @author Carmen
 */
public class FileUtil {

    public static boolean isDirRewritable(String path) {
        File file = new File(path);        
        return file.isDirectory() && file.canWrite();   
    }

}
