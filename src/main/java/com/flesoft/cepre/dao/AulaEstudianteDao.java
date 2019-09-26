package com.flesoft.cepre.dao;

import com.flesoft.cepre.model.AulaEstudiante;
import com.flesoft.cepre.model.Huella;
import java.io.ByteArrayInputStream;
import java.util.List;

public interface AulaEstudianteDao {

    List<AulaEstudiante> listar();

    List<AulaEstudiante> listar2();

    List<AulaEstudiante> listarConHuella();

    List<AulaEstudiante> listar(int numeroPagina, long countResults);

    long totalRecords();

    List<AulaEstudiante> listarPorAula(long aula_id, boolean huella);

    List<AulaEstudiante> listarPorPabellon(long estudiante_id, boolean huella);

    public AulaEstudiante buscarPorEstudianteId(long estudiante_id);

    public AulaEstudiante buscarPorEstudianteDni(String dni);

    AulaEstudiante buscarPorId(long id);

    List<AulaEstudiante> listarPorDni(String dni);

    List<AulaEstudiante> listarPorApellido(String apellidos);

    AulaEstudiante editar(long id, byte[] datosHuella);

    void guardar(AulaEstudiante huella);

    AulaEstudiante insertar(AulaEstudiante huella, byte[] datosHuella, Integer tamanioHuella);
}
