package com.flesoft.cepre.dao.impl;

import com.flesoft.cepre.dao.AulaEDao;
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
public class AulaEDaoImpl implements AulaEDao {

    private static final Log LOG = LogFactory.getLog(AulaEDaoImpl.class);

    private SessionFactory sessionFactory;

    @Override
    @Transactional(readOnly = true)
    public List<AulaE> listar() {
        return sessionFactory.getCurrentSession().createQuery("from AulaE c").list();
    }

    @Override
    @Transactional
    public AulaE buscarPorId(long id) {
        Session session = sessionFactory.getCurrentSession();
        AulaE aulae = (AulaE) session.get(AulaE.class, id);
        return aulae;
    }

    @Override
    @Transactional(readOnly = true)
    public AulaE buscarPorNombre(String denominacion) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from AulaE where denominacion=:denominacion");
        query.setParameter("denominacion", denominacion);
        AulaE aulae = (AulaE) query.uniqueResult();
        return aulae;
    }

    @Override
    @Transactional
    public void guardar(AulaE aulaE) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(aulaE);
        LOG.info("AulaE guardado con el id: " + aulaE.getId());
    }

    @Override
    @Transactional
    public void editar(AulaE aulaE) {
        Session session = sessionFactory.getCurrentSession();
        session.update(aulaE);
        LOG.info("aulaE guardado con el id: " + aulaE.getId());
    }

    @Override
    @Transactional
    public void eliminar(AulaE pabellonE) {
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
