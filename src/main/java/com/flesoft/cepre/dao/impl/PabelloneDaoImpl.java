package com.flesoft.cepre.dao.impl;

import com.flesoft.cepre.dao.PabelloneDao;
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
import com.flesoft.cepre.model.PabellonE;

@Transactional
@Repository
public class PabelloneDaoImpl implements PabelloneDao {

    private static final Log LOG = LogFactory.getLog(PabelloneDaoImpl.class);

    private SessionFactory sessionFactory;

    @Override
    @Transactional(readOnly = true)
    public List<PabellonE> listar() {
        return sessionFactory.getCurrentSession().createQuery("FROM PabellonE c ORDER BY c.denominacion").list();
    }

    @Override
    @Transactional
    public PabellonE buscarPorId(long id) {
        Session session = sessionFactory.getCurrentSession();
        PabellonE pabellone = (PabellonE) session.get(PabellonE.class, id);
        return pabellone;
    }

    @Override
    @Transactional(readOnly = true)
    public PabellonE buscarPorNombre(String denominacion) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from PabellonE where denominacion=:denominacion");
        query.setParameter("denominacion", denominacion);
        PabellonE pabellone = (PabellonE) query.uniqueResult();
        return pabellone;
    }

    @Override
    @Transactional
    public void guardar(PabellonE pabellonE) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(pabellonE);
        LOG.info("Pabellon guardado con el id: " + pabellonE.getId());
    }

    @Override
    @Transactional
    public void editar(PabellonE pabellonE) {
        Session session = sessionFactory.getCurrentSession();
        session.update(pabellonE);
        LOG.info("sede guardado con el id: " + pabellonE.getId());
    }

    @Override
    @Transactional
    public void eliminar(PabellonE pabellonE) {
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
