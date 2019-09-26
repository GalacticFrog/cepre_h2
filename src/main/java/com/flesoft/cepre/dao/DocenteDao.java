package com.flesoft.cepre.dao;

import com.flesoft.cepre.model.Docente;
import com.flesoft.cepre.model.Usuario;
import java.io.ByteArrayInputStream;
import java.util.List;

public interface DocenteDao {

    List<Docente> listar();

    Docente buscarPorId(int id);

    Docente insertar(Docente docente);

    Docente insertar(Docente docente, ByteArrayInputStream datosHuella, Integer tamanioHuella);

    Docente guardar(Docente docente);

    void eliminar(Docente docente);
}
