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

/**
 *
 * @author edrev
 */
@Entity
@Table(name = "asistencia_clase")
public class AsistenciaClase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Date fecha;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "aula_estudio_id")
    private AulaEstudiante aulaEstudiante;

    public AsistenciaClase() {

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
     * @return the aulaEstudiante
     */
    public AulaEstudiante getAulaEstudiante() {
        return aulaEstudiante;
    }

    /**
     * @param aulaEstudiante the aulaEstudiante to set
     */
    public void setAulaEstudiante(AulaEstudiante aulaEstudiante) {
        this.aulaEstudiante = aulaEstudiante;
    }

    @Override
    public String toString() {
        return "[asistencia examen] id: " + getId() + " - fecha: " + getFecha().toString();
    }
}
