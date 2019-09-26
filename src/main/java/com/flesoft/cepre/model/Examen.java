/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Type;


/**
 *
 * @author edrev
 */
@Entity
@Table(name = "examen")
public class Examen implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "El Nombre del EXAMEN es requerido.")
    private String nombre;
    @NotNull(message = "La fecha del EXAMEN es requerida.")
    private Date fecha;

    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean ejecutado;

    @Column(columnDefinition = "TINYINT")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean abierto;

    public Examen() {

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
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
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
     * @return the ejecutado
     */
    public boolean isEjecutado() {
        return ejecutado;
    }

    /**
     * @param ejecutado the ejecutado to set
     */
    public void setEjecutado(boolean ejecutado) {
        this.ejecutado = ejecutado;
    }

    @Override
    public String toString() {
        return getNombre();
    }

    /**
     * @return the abierto
     */
    public boolean isAbierto() {
        return abierto;
    }

    /**
     * @param abierto the abierto to set
     */
    public void setAbierto(boolean abierto) {
        this.abierto = abierto;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (!(o instanceof Examen)) {
            return false;
        }
        Examen examen = (Examen) o;
        if (id != examen.id) {
            return false;
        }
        if (nombre != null ? !nombre.equals(examen.nombre) : examen.nombre != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        hash = (int) (89 * hash + this.id);
        return hash;
    }
}
