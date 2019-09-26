package com.flesoft.cepre.dao;

import com.flesoft.cepre.model.AulaEstudiante;
import com.flesoft.cepre.model.AulaExamen;
import com.flesoft.cepre.model.Estudiante;
import com.flesoft.cepre.model.Huella;
import java.io.ByteArrayInputStream;
import java.util.List;

public interface EstudianteDao {

    List<Estudiante> listar();

    Estudiante guardar(Estudiante estudiante);

    Estudiante buscarPorDni(String dni);

    Estudiante buscarPorId(long id);

    /**
     * busca por id del estudiante y por ciclo academico
     */
    AulaEstudiante buscarAula(long estudiante_id);

    /**
     * busca por id del estudiante y por ciclo academico
     *
     * @return
     */
    AulaExamen buscarAulaExamen(long estudiante_id, long examen_id);

    Huella guardarHuella(Huella huella, ByteArrayInputStream datosHuella, Integer tamanioHuella);

    /**
     * busca una huella por ciclo e id de estudiante
     *
     * @param estudiante_id
     * @param ciclo_id
     * @return
     */
    boolean existeHuellaCiclo(long estudiante_id);

}
