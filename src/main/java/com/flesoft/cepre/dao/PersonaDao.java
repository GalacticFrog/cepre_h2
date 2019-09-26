package com.flesoft.cepre.dao;

import com.flesoft.cepre.model.Persona;
import java.util.List;

public interface PersonaDao {

    public List<Persona> listar();

    Persona guardar(Persona usuario);

    List<Persona> buscar(String dni);

}
