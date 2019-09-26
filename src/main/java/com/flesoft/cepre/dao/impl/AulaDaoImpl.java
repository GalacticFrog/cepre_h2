package com.flesoft.cepre.dao.impl;

import com.flesoft.cepre.dao.AulaDao;
import com.flesoft.cepre.dao.AulaEDao;
import com.flesoft.cepre.model.Aula;
import com.flesoft.cepre.model.AulaE;
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
public class AulaDaoImpl implements AulaDao {

    private static final Log LOG = LogFactory.getLog(AulaDaoImpl.class);

    private SessionFactory sessionFactory;

    @Override
    @Transactional(readOnly = true)
    public List<Aula> listar() {
        return sessionFactory.getCurrentSession().createQuery("FROM Aula a").list();
    }

    @Override
    @Transactional
    public Aula buscarPorId(long id) {
        Session session = sessionFactory.getCurrentSession();
        Aula aulae = (Aula) session.get(Aula.class, id);
        return aulae;
    }

    @Override
    @Transactional(readOnly = true)
    public Aula buscarPorNombre(String denominacion) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Aula WHERE denominacion=:denominacion");
        query.setParameter("denominacion", denominacion);
        Aula aulae = (Aula) query.uniqueResult();
        return aulae;
    }

    @Override
    @Transactional
    public void guardar(Aula aula) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(aula);
        LOG.info("AulaE guardado con el id: " + aula.getId());
    }

    @Override
    @Transactional
    public void editar(Aula aula) {
        Session session = sessionFactory.getCurrentSession();
        session.update(aula);
        LOG.info("aula guardado con el id: " + aula.getId());
    }

    @Override
    @Transactional
    public void eliminar(Aula aula) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(aula);
        LOG.info("aula borrado con el id: " + aula.getId());
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
