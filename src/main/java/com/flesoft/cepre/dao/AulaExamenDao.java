package com.flesoft.cepre.dao;

import com.flesoft.cepre.model.AsistenciaExamen;
import com.flesoft.cepre.model.AulaEstudiante;
import com.flesoft.cepre.model.AulaExamen;
import java.util.List;

public interface AulaExamenDao {

    List<AulaExamen> listarPorExamenAula(long examen_id, long aulae_id);

    List<AulaExamen> listarPorExamen(long examen_id, long pabellone_id);

    AulaExamen buscar(long estudiante_id, long aulae_id, long examen_id);

    List<AulaExamen> listarPorDni(String dni);

    List<AulaExamen> listarPorApellido(String apellidos);

    void guardar(AulaExamen aulaExamen);
}
