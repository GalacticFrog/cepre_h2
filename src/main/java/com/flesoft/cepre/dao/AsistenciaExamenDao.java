package com.flesoft.cepre.dao;

import com.flesoft.cepre.model.AsistenciaExamen;
import java.util.Date;
import java.util.List;

public interface AsistenciaExamenDao {

    long totalRecords();

    long registrosPorExamen(long examen_id);

    List<AsistenciaExamen> listar();

    List<AsistenciaExamen> listar(int numeroPagina);

    List<AsistenciaExamen> listarPorExamenAula(long examen_id, long aulae_id);

    List<AsistenciaExamen> listarPorExamen(long examen_id, long pabellone_id);
//
//    AsistenciaClase buscarPorId(long id);

    void guardar(AsistenciaExamen asistenciaExamen);

    boolean existeAsistencia(Date fecha, long aula_examen_id, long examen_id);

}
