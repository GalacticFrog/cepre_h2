package com.flesoft.cepre.dao.impl;

import com.flesoft.cepre.dao.PabellonDao;
import com.flesoft.cepre.model.Pabellon;
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
public class PabellonDaoImpl implements PabellonDao {

    private static final Log LOG = LogFactory.getLog(PabellonDaoImpl.class);

    private SessionFactory sessionFactory;

    @Override
    @Transactional(readOnly = true)
    public List<Pabellon> listar() {
        return sessionFactory.getCurrentSession().createQuery("from Pabellon c").list();
    }

    @Override
    @Transactional
    public Pabellon buscarPorId(long id) {
        Session session = sessionFactory.getCurrentSession();
        Pabellon pabellone = (Pabellon) session.get(Pabellon.class, id);
        return pabellone;
    }

    @Override
    @Transactional(readOnly = true)
    public Pabellon buscarPorNombre(String denominacion) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Pabellon where denominacion=:denominacion");
        query.setParameter("denominacion", denominacion);
        Pabellon pabellone = (Pabellon) query.uniqueResult();
        return pabellone;
    }

    @Override
    @Transactional
    public void guardar(Pabellon pabellonE) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(pabellonE);
        LOG.info("Pabellon guardado con el id: " + pabellonE.getId());
    }

    @Override
    @Transactional
    public void editar(Pabellon pabellonE) {
        Session session = sessionFactory.getCurrentSession();
        session.update(pabellonE);
        LOG.info("sede guardado con el id: " + pabellonE.getId());
    }

    @Override
    @Transactional
    public void eliminar(Pabellon pabellonE) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(pabellonE);
        LOG.info("sede borrado con el id: " + pabellonE.getId());
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
