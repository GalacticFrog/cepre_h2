/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author edrev
 */
@Entity
@Table(name = "pabellon")
public class Pabellon implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    private String denominacion;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sede_id")
    private Sede sede;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pabellon")
    //@JoinColumn(name = "pabellone_id")
    private List<Aula> aulas;

    public Pabellon() {

    }

    public Pabellon(long id, String denominacion) {
        this.id = id;
        this.denominacion = denominacion;
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
     * @return the sede
     */
    public Sede getSede() {
        return sede;
    }

    /**
     * @param sede the sede to set
     */
    public void setSede(Sede sede) {
        this.sede = sede;
    }

    @Override
    public String toString() {
        return getDenominacion();
    }

    /**
     * @return the aulas
     */
    public List<Aula> getAulas() {
        return aulas;
    }

    /**
     * @param aulas the aulas to set
     */
    public void setAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pabellon)) {
            return false;
        }
        Pabellon pabellon = (Pabellon) o;
        if (id != pabellon.id) {
            return false;
        }
        if (denominacion != null ? !denominacion.equals(pabellon.denominacion) : pabellon.denominacion != null) {
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
