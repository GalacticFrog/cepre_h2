package com.flesoft.cepre.dao;

import com.flesoft.cepre.model.AsistenciaDocente;

import java.util.List;

public interface AsistenciaDocenteDao {

    long totalRecords();

    List<AsistenciaDocente> listar();

    void guardar(AsistenciaDocente asistenciaDocente);

    AsistenciaDocente existeAsistencia(long personal_id);
}
