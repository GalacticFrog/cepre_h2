package com.flesoft.cepre.dao;

import com.flesoft.cepre.model.Pabellon;
import java.util.List;

public interface PabellonDao {

    List<Pabellon> listar();

    Pabellon buscarPorId(long id);

    Pabellon buscarPorNombre(String denominacion);

    void guardar(Pabellon sede);

    void editar(Pabellon sede);

    void eliminar(Pabellon sede);
}
