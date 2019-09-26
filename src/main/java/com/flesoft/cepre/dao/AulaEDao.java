package com.flesoft.cepre.dao;

import com.flesoft.cepre.model.AulaE;
import java.util.List;

public interface AulaEDao {

    List<AulaE> listar();

    AulaE buscarPorId(long id);

    AulaE buscarPorNombre(String denominacion);

    void guardar(AulaE aulaE);

    void editar(AulaE aulaE);

    void eliminar(AulaE aulaE);
}
