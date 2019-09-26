/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.model;

import java.io.Serializable;
import java.sql.Blob;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 *
 * @author edrev
 */
@Entity
@Table(name = "usuario")
//@FieldMatch.List({
//    @FieldMatch(first = "contrasena", second = "contrasena2", message = "Las contrase\u00F1as no coinciden")
//})
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "El Nombre de Usuario es requerido.")
    //@Size(min = 8, max = 8, message = "El dni es de longitud 8")
    private String usuario; //dni

//    @NotBlank(message = "La contrase\u00F1a es requerida.")
//    @Size(min = 8, message = "La contrase\u00F1a debe contener por lo menos 8 caracteres.")
    @Column(name = "passphrase")
    private String contrasena;

//    @NotBlank(message = "La contrase\u00F1a2 es requerida")
//    @Size(min = 8, message = "La contrase\u00F1a debe contener por lo menos 6 caracteres")
//    @Transient
//    private String contrasena2;
    @Lob
    private Blob huella;

    private String salt;

    @NotNull
    @Valid
    //@ManyToOne(cascade = CascadeType.ALL)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "persona_id")
    private Persona persona;

    public Usuario() {

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
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the contrasena
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @param contrasena the contrasena to set
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
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
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt the salt to set
     */
    public void setSalt(String salt) {
        this.salt = salt;
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
    @Override
    public String toString() {
        return " id: " + getId() + " - usuario: " + getUsuario() + " pass:" + getContrasena();
    }
}
