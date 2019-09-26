package com.flesoft.cepre.dao;

import com.flesoft.cepre.model.Cargo;
import java.util.List;

public interface CargoDao {

    List<Cargo> listar();

    Cargo buscarPorId(long id);

    Cargo buscarPorNombre(String nombre);

    void guardar(Cargo sede);

    void editar(Cargo sede);

    void eliminar(Cargo sede);
}
