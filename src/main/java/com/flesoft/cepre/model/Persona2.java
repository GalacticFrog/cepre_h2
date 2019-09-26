/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *
 * @author edrev
 */
@Entity
@Table(name = "persona2")
public class Persona2 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "El dni es requerido.")
    @Size(min = 8, max = 8, message = "El dni es de longitud 8.")
    @Column(name = "documento_identidad")
    private String dni;

    private String apellidos;

    @NotBlank(message = "El Nombre es requerido.")
    private String nombres;

    //@NotBlank(message = "La Direccion es requerida.")
    private String direccion;

    // @NotBlank(message = "El Telefono es requerido.")
    private String telefono;

//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "persona_id")
//    private List<Usuario> usuario = new ArrayList<>();
    public Persona2() {

    }

//    public void addUsuario(Usuario usuario) {
//        this.usuario.add(usuario);
//    }
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
     * @return the dni
     */
    public String getDni() {
        return dni;
    }

    /**
     * @param dni the dni to set
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String toString() {
        return "id: " + getId() + " nombres: " + getNombres() + " direccion: " + getDireccion() + " telefono: " + getTelefono() + " dni: " + getDni();
    }

    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

}
