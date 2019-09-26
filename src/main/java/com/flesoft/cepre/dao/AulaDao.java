package com.flesoft.cepre.dao;

import com.flesoft.cepre.model.Aula;
import java.util.List;

public interface AulaDao {

    List<Aula> listar();

    Aula buscarPorId(long id);

    Aula buscarPorNombre(String denominacion);

    void guardar(Aula aula);

    void editar(Aula aula);

    void eliminar(Aula aula);
}
