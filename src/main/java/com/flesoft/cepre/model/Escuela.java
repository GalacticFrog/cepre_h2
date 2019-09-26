/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author edrev
 */
@Entity
@Table(name = "escuela")
public class Escuela implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_escuela")
    private long id;

    @Column(name = "nombre_escuela")
    private String nombreEscuela;

    public Escuela() {

    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the nombreEscuela
     */
    public String getNombreEscuela() {
        return nombreEscuela;
    }

    /**
     * @param nombreEscuela the nombreEscuela to set
     */
    public void setNombreEscuela(String nombreEscuela) {
        this.nombreEscuela = nombreEscuela;
    }

    @Override
    public String toString() {
        return " id: " + getId() + " - escuela: " + getNombreEscuela();
    }
}
