/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.model;

import com.machinezoo.sourceafis.FingerprintTemplate;
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
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 *
 * @author edrev
 */
@Entity
@Table(name = "aula_estudiante")
public class AulaEstudiante implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    private String turno;

    @Lob
    private byte[] huella;
    private String jsonTemplate;
    @Transient
    private FingerprintTemplate template;
    @NotNull
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    //@ManyToOne(cascade = CascadeType.ALL)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "aula_id")
    private Aula aula;

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
     * @return the turno
     */
    public String getTurno() {
        return turno;
    }

    /**
     * @param turno the turno to set
     */
    public void setTurno(String turno) {
        this.turno = turno;
    }

    /**
     * @return the estudiante
     */
    public Estudiante getEstudiante() {
        return estudiante;
    }

    /**
     * @param estudiante the estudiante to set
     */
    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    /**
     * @return the aula
     */
    public Aula getAula() {
        return aula;
    }

    /**
     * @param aula the aula to set
     */
    public void setAula(Aula aula) {
        this.aula = aula;
    }

    @Override
    public String toString() {
        return " id: " + getId() + " - turno: " + getTurno();
    }

    /**
     * @return the huella
     */
    public byte[] getHuella() {
        return huella;
    }

    /**
     * @param huella the huella to set
     */
    public void setHuella(byte[] huella) {
        this.huella = huella;
    }

    /**
     * @return the jsonTemplate
     */
    public String getJsonTemplate() {
        return jsonTemplate;
    }

    /**
     * @param jsonTemplate the jsonTemplate to set
     */
    public void setJsonTemplate(String jsonTemplate) {
        this.jsonTemplate = jsonTemplate;
    }

    /**
     * @return the template
     */
    public FingerprintTemplate getTemplate() {
        FingerprintTemplate _template = new FingerprintTemplate()
                .deserialize(getJsonTemplate());

        return _template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(FingerprintTemplate template) {
        this.template = template;
    }
}
