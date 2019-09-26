/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author edrev
 */
@Entity
@Table(name = "sede")
public class Sede implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sede")
    private long id;

    @Column(name = "nombre_sede")
    private String nombreSede;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sede")
    @Fetch(value = FetchMode.SUBSELECT)
    //@JoinColumn(name = "pabellone_id")
    private List<Pabellon> pabellones;

    public Sede() {

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
     * @return the nombreSede
     */
    public String getNombreSede() {
        return nombreSede;
    }

    /**
     * @param nombreSede the nombreSede to set
     */
    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    @Override
    public String toString() {
        //return " id: " + getId() + " - sede: " + getNombreSede();
        return getNombreSede();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sede)) {
            return false;
        }
        Sede sede = (Sede) o;
        if (id != sede.id) {
            return false;
        }
        if (nombreSede != null ? !nombreSede.equals(sede.nombreSede) : sede.nombreSede != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.nombreSede != null ? this.nombreSede.hashCode() : 0);
        hash = (int) (89 * hash + this.id);
        return hash;
    }

    /**
     * @return the pabellones
     */
    public List<Pabellon> getPabellones() {
        return pabellones;
    }

    /**
     * @param pabellones the pabellones to set
     */
    public void setPabellones(List<Pabellon> pabellones) {
        this.pabellones = pabellones;
    }
}
