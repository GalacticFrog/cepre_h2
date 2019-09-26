package com.flesoft.cepre.dao.impl;

import com.flesoft.cepre.dao.CargoDao;
import com.flesoft.cepre.model.Cargo;
import com.flesoft.cepre.model.Sede;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import javax.annotation.Resource;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

@Transactional
@Repository
public class CargoDaoImpl implements CargoDao {

    private static final Log LOG = LogFactory.getLog(CargoDaoImpl.class);

    private SessionFactory sessionFactory;

    @Override
    @Transactional(readOnly = true)
    public List<Cargo> listar() {
        return sessionFactory.getCurrentSession().createQuery("from Cargo c").list();
    }

    @Override
    @Transactional
    public Cargo buscarPorId(long id) {
        Session session = sessionFactory.getCurrentSession();
        Cargo sede = (Cargo) session.get(Cargo.class, id);
        return sede;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Cargo buscarPorNombre(String nombre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void guardar(Cargo sede) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar(Cargo sede) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminar(Cargo sede) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
