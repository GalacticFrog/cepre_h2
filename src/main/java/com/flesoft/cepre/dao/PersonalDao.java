package com.flesoft.cepre.dao;

import com.flesoft.cepre.model.Personal;
import java.io.ByteArrayInputStream;
import java.util.List;

public interface PersonalDao {

    List<Personal> listar();

    List<Personal> listarConHuella();

    Personal buscarPorId(int id);

    List<Personal> buscarPorApellidos(String str);

    Personal buscarPorDni(String str);

    Personal insertar(Personal personal);

    Personal insertar(Personal personal, ByteArrayInputStream datosHuella, Integer tamanioHuella);

    Personal guardar(Personal personal);

    void eliminar(Personal personal);
}
