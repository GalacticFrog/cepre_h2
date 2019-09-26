package com.flesoft.cepre.dao;

import com.flesoft.cepre.model.Huella;
import java.io.ByteArrayInputStream;
import java.util.List;

public interface HuellaDao {

    List<Huella> listar();

    Huella buscarPorId(long id);

    public Huella buscarPorDni(String dni);

    void guardar(Huella huella);

    Huella insertar(Huella huella, byte[] datosHuella, Integer tamanioHuella);

    void eliminar(Huella huella);
}
