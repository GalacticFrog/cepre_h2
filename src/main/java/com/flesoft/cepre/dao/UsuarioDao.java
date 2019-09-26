package com.flesoft.cepre.dao;

import com.flesoft.cepre.model.Usuario;
import java.io.ByteArrayInputStream;
import java.util.List;

public interface UsuarioDao {

    List<Usuario> listar();

    Usuario buscarPorId(int id);

    Usuario buscarPorNombre(String nombre);

    Usuario insertar(Usuario usuario, ByteArrayInputStream datosHuella, Integer tamanioHuella);

    Usuario guardar(Usuario usuario);

    void eliminar(Usuario usuario);
}
