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
@Table(name = "asistencia_examen")
public class AsistenciaExamen implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "aula_examen_id")
    private AulaExamen aulaExamen;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "examen_id")
    private Examen examen;

    public AsistenciaExamen() {

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
     * @return the aulaExamen
     */
    public AulaExamen getAulaExamen() {
        return aulaExamen;
    }

    /**
     * @param aulaExamen the aulaExamen to set
     */
    public void setAulaExamen(AulaExamen aulaExamen) {
        this.aulaExamen = aulaExamen;
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

}
