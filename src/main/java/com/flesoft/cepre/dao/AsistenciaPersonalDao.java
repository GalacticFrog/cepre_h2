package com.flesoft.cepre.dao;

import com.flesoft.cepre.model.AsistenciaPersonal;
import java.util.Date;
import java.util.List;

public interface AsistenciaPersonalDao {

    long totalRecords();

    List<AsistenciaPersonal> listar();

    List<AsistenciaPersonal> listar(int numeroPagina);

    List<AsistenciaPersonal> listarPorExamen(long examen_id);

    List<AsistenciaPersonal> listarPorExamenCargo(long examen_id, long cargo_id);
//
//    AsistenciaClase buscarPorId(long id);

    void guardar(AsistenciaPersonal asistenciaExamen);

    AsistenciaPersonal existeAsistencia(/*Date fecha, */long aula_examen_id, long examen_id);

    public void eliminar(AsistenciaPersonal asistenciaPersonal);
}
