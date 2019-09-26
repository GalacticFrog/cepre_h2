/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.main;

import com.flesoft.cepre.dao.UsuarioDao;
import com.flesoft.cepre.ui.Login;
import com.flesoft.cepre.util.AppContext;

/**
 *
 * @author osusr
 */
class Run {

    public static void main(String[] args) {
        String[] letters1 = {"A", "B", "C"};
        String[] letters2 = {"A", "B", "C"};
        String[] letters3 = letters1;
        System.out.print(letters1.equals(letters2));
        System.out.print(letters1.equals(letters3));
    }
}
