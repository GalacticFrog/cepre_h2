package com.flesoft.cepre.dao;

import com.flesoft.cepre.model.PabellonE;
import java.util.List;

public interface PabelloneDao {

    List<PabellonE> listar();

    PabellonE buscarPorId(long id);

    PabellonE buscarPorNombre(String denominacion);

    void guardar(PabellonE sede);

    void editar(PabellonE sede);

    void eliminar(PabellonE sede);
}
