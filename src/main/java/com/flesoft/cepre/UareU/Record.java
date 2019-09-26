/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.UareU;

/**
 *
 * @author edrev
 */
public class Record {

    public int id;
    public byte[] fmdBinary;

    Record(int ID, byte[] fmd) {
        id = ID;
        fmdBinary = fmd;
    }
}
