package com.flesoft.cepre.dao.impl;

import com.flesoft.cepre.dao.ExamenDao;
import com.flesoft.cepre.model.Examen;
import java.util.Date;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import javax.annotation.Resource;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.type.DateType;

@Transactional
@Repository
public class ExamenDaoImpl implements ExamenDao {

    private static final Log LOG = LogFactory.getLog(ExamenDaoImpl.class);

    private SessionFactory sessionFactory;

    @Override
    public List<Examen> listar() {
        return sessionFactory.getCurrentSession().createQuery("from Examen e").list();
    }

    @Override
    public List<Examen> listarPendientes() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Examen e where ejecutado = :ejecutado");//.list();
        query.setParameter("ejecutado", false);
        return query.list();
    }

    @Override
    public Examen buscarPorId(long id) {
        Session session = sessionFactory.getCurrentSession();
        Examen examen = (Examen) session.get(Examen.class, id);
        return examen;
    }

    @Override
    public Examen buscarPorFecha(Date fecha) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Examen where fecha=:fecha");
        query.setParameter("fecha", fecha, DateType.INSTANCE);
        Examen examen = (Examen) query.uniqueResult();
        return examen;
    }

    @Override
    public void guardar(Examen examen) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(examen);
        LOG.info("Examen guardado con el id: " + examen.getId());
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
