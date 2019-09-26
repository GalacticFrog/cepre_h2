/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author edrev
 */
@Entity
@Table(name = "asistencia_personal")
public class AsistenciaPersonal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Temporal(TemporalType.TIMESTAMP)
    private Date entrada;

    @Temporal(TemporalType.TIMESTAMP)
    private Date salida;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "examen_id")
    private Examen examen;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "sede_id")
//    private Sede sede;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "personal_id")
    private Personal personal;

    public AsistenciaPersonal() {

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
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the examen
     */
    public Examen getExamen() {
        return examen;
    }

    /**
     * @param examen the examen to set
     */
    public void setExamen(Examen examen) {
        this.examen = examen;
    }

//    /**
//     * @return the sede
//     */
//    public Sede getSede() {
//        return sede;
//    }
//
//    /**
//     * @param sede the sede to set
//     */
//    public void setSede(Sede sede) {
//        this.sede = sede;
//    }
    /**
     * @return the entrada
     */
    public Date getEntrada() {
        return entrada;
    }

    /**
     * @param entrada the entrada to set
     */
    public void setEntrada(Date entrada) {
        this.entrada = entrada;
    }

    /**
     * @return the salida
     */
    public Date getSalida() {
        return salida;
    }

    /**
     * @param salida the salida to set
     */
    public void setSalida(Date salida) {
        this.salida = salida;
    }

    /**
     * @return the personal
     */
    public Personal getPersonal() {
        return personal;
    }

    /**
     * @param personal the personal to set
     */
    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    /**
     * @return the cargo
     */
    public Cargo getCargo() {
        return cargo;
    }

    /**
     * @param cargo the cargo to set
     */
    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
}
