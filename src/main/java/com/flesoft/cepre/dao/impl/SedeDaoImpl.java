package com.flesoft.cepre.dao.impl;

import com.flesoft.cepre.dao.SedeDao;
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
public class SedeDaoImpl implements SedeDao {

    private static final Log LOG = LogFactory.getLog(SedeDaoImpl.class);

    private SessionFactory sessionFactory;

    @Override
    @Transactional(readOnly = true)
    public List<Sede> listar() {
        return sessionFactory.getCurrentSession().createQuery("from Sede c").list();
    }

    @Override
    @Transactional
    public Sede buscarPorId(long id) {
        Session session = sessionFactory.getCurrentSession();
        Sede sede = (Sede) session.get(Sede.class, id);
        return sede;
    }

    @Override
    @Transactional(readOnly = true)
    public Sede buscarPorNombre(String nombre_sede) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Sede where nombre_sede=:nombre_sede");
        query.setParameter("nombre_sede", nombre_sede);
        Sede sede = (Sede) query.uniqueResult();
        return sede;
    }

    @Override
    @Transactional
    public void guardar(Sede sede) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(sede);
        LOG.info("sede guardado con el id: " + sede.getId());
    }

    @Override
    @Transactional
    public void editar(Sede sede) {
        Session session = sessionFactory.getCurrentSession();
        session.update(sede);
        LOG.info("sede guardado con el id: " + sede.getId());
    }

    @Override
    @Transactional
    public void eliminar(Sede sede) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(sede);
        LOG.info("sede borrado con el id: " + sede.getId());
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
