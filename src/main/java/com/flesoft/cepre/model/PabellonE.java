/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
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
 * @author edrev pabellon para examane
 *
 */
@Entity
@Table(name = "pabellon_e")
public class PabellonE implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    private String denominacion;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sede_id")
    private Sede sede;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "pabellone")
    //@JoinColumn(name = "pabellone_id")
    private List<AulaE> aulaes;

    public PabellonE() {

    }

    @Override
    public String toString() {
        return getDenominacion();
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
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof PabellonE)) {
            return false;
        }
        PabellonE pabellone = (PabellonE) o;
        if (id != pabellone.id) {
            return false;
        }
        if (denominacion != null ? !denominacion.equals(pabellone.denominacion) : pabellone.denominacion != null) {
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

    /**
     * @return the aulaes
     */
    public List<AulaE> getAulaes() {
        return aulaes;
    }

    /**
     * @param aulaes the aulaes to set
     */
    public void setAulaes(List<AulaE> aulaes) {
        this.aulaes = aulaes;
    }
}
