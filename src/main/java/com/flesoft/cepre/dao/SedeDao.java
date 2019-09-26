package com.flesoft.cepre.dao;

import com.flesoft.cepre.model.Sede;
import java.util.List;

public interface SedeDao {

    List<Sede> listar();

    Sede buscarPorId(long id);

    Sede buscarPorNombre(String nombre);

    void guardar(Sede sede);

    void editar(Sede sede);

    void eliminar(Sede sede);
}
