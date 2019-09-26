package com.flesoft.cepre.dao;

import com.flesoft.cepre.model.Examen;
import java.util.Date;
import java.util.List;

public interface ExamenDao {

    List<Examen> listar();

    List<Examen> listarPendientes();

    Examen buscarPorId(long id);

    Examen buscarPorFecha(Date fecha);

    void guardar(Examen examen);

}
