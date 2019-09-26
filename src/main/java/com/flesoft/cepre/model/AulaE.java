/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author edrev aulas examen
 */
@Entity
@Table(name = "aula_e")
public class AulaE implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    private String denominacion;
    private int capacidad;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "pabellone_id")
    private PabellonE pabellone;

    @Override
    public String toString() {
        return getDenominacion();
    }

    public AulaE() {

    }

    public AulaE(long id, String denominacion, int capacidad) {
        this.id = id;
        this.denominacion = denominacion;
        this.capacidad = capacidad;
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
     * @return the denominacion
     */
    public String getDenominacion() {
        return denominacion;
    }

    /**
     * @param denominacion the denominacion to set
     */
    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    /**
     * @return the capacidad
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * @param capacidad the capacidad to set
     */
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * @return the pabellone
     */
    public PabellonE getPabellone() {
        return pabellone;
    }

    /**
     * @param pabellone the pabellone to set
     */
    public void setPabellone(PabellonE pabellone) {
        this.pabellone = pabellone;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof AulaE)) {
            return false;
        }
        AulaE aula = (AulaE) o;
        if (id != aula.id) {
            return false;
        }
        if (denominacion != null ? !denominacion.equals(aula.denominacion) : aula.denominacion != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.denominacion != null ? this.denominacion.hashCode() : 0);
        hash = (int) (89 * hash + this.id);
        return hash;
    }
}
