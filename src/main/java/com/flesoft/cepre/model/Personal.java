/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.model;

import java.io.Serializable;
import java.sql.Blob;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 *
 * @author edrev
 */
@Entity
@Table(name = "personal")
//@FieldMatch.List({
//    @FieldMatch(first = "contrasena", second = "contrasena2", message = "Las contrase\u00F1as no coinciden")
//})
public class Personal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @NotBlank(message = "La contrase\u00F1a2 es requerida")
//    @Size(min = 8, message = "La contrase\u00F1a debe contener por lo menos 6 caracteres")
//    @Transient
//    private String contrasena2;
    @Lob
    private Blob huella;

    private String condicion;
    @NotNull
    @Valid
    //@ManyToOne(cascade = CascadeType.ALL)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "persona_id")
    private Persona persona;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sede_id")
    private Sede sede;

    public Personal() {

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
     * @return the huella
     */
    public Blob getHuella() {
        return huella;
    }

    /**
     * @param huella the huella to set
     */
    public void setHuella(Blob huella) {
        this.huella = huella;
    }

    /**
     * @return the persona
     */
    public Persona getPersona() {
        return persona;
    }

    /**
     * @param persona the persona to set
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    /**
     * @return the contrasena2
     */
//    public String getContrasena2() {
//        return contrasena2;
//    }
//
//    /**
//     * @param contrasena2 the contrasena2 to set
//     */
//    public void setContrasena2(String contrasena2) {
//        this.contrasena2 = contrasena2;
//    }
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
        if (!(o instanceof Personal)) {
            return false;
        }
        Personal docente = (Personal) o;
        if (id != docente.id) {
            return false;
        }
//        if (nombreSede != null ? !nombreSede.equals(docente.nombreSede) : docente.nombreSede != null) {
//            return false;
//        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.getPersona().getDni() != null ? this.getPersona().getDni().hashCode() : 0);
        hash = (int) (89 * hash + this.id);
        return hash;
    }

    @Override
    public String toString() {
        return " id: " + getId();
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

    /**
     * @return the condicion
     */
    public String getCondicion() {
        return condicion;
    }

    /**
     * @param condicion the condicion to set
     */
    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    /**
     * @return the tipo
     */
//    public String getTipo() {
//        return tipo;
//    }
//
//    /**
//     * @param tipo the tipo to set
//     */
//    public void setTipo(String tipo) {
//        this.tipo = tipo;
//    }
}
