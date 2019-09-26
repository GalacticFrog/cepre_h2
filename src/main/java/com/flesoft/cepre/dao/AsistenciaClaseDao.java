package com.flesoft.cepre.dao;

import com.flesoft.cepre.model.AsistenciaClase;

public interface AsistenciaClaseDao {

//    List<Ciclo> listar();
//
//    AsistenciaClase buscarPorId(long id);
    void guardar(AsistenciaClase asistenciaClase);

    public boolean existeHuellaCiclo(long aula_estudio_id);

}
