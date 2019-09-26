package com.flesoft.cepre.dao;

import com.flesoft.cepre.model.Personal2;
import java.io.ByteArrayInputStream;
import java.util.List;

public interface Personal2Dao {

    List<Personal2> listar();

    List<Personal2> listarConHuella();

    Personal2 buscarPorId(int id);

    List<Personal2> buscarPorApellidos(String str);

    Personal2 buscarPorDni(String str);

    Personal2 insertar(Personal2 personal);

    Personal2 insertar(Personal2 personal, ByteArrayInputStream datosHuella, Integer tamanioHuella);

    Personal2 guardar(Personal2 personal);

    void eliminar(Personal2 personal);
}
