package com.flesoft.cepre.dao.impl;

import com.flesoft.cepre.dao.AsistenciaDocenteDao;
import com.flesoft.cepre.dao.AsistenciaPersonalDao;
import com.flesoft.cepre.model.AsistenciaDocente;
import com.flesoft.cepre.model.AsistenciaPersonal;
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
public class AsistenciaDocenteDaoImpl implements AsistenciaDocenteDao {

    private static final Log LOG = LogFactory.getLog(AsistenciaDocenteDaoImpl.class);

    private SessionFactory sessionFactory;

    @Override
    public long totalRecords() {
        Session session = sessionFactory.getCurrentSession();
        Query countQuery = session.createQuery("SELECT COUNT (ae.id) FROM AsistenciaDocente ae");
        long countResults = (Long) countQuery.uniqueResult();
        return countResults;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsistenciaDocente> listar() {
        return sessionFactory.getCurrentSession().createQuery("from AsistenciaDocente ae").list();
    }

    @Override
    @Transactional
    public void guardar(AsistenciaDocente asistenciaDocente) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(asistenciaDocente);
        LOG.info("asistenciaDocente guardado con el id: " + asistenciaDocente.getId());
    }

    @Override
    public AsistenciaDocente existeAsistencia(long personal_id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM AsistenciaDocente WHERE "
                + "personal_id=:personal_id "
                + "AND entrada IS NOT NULL AND salida IS NULL");
        query.setParameter("personal_id", personal_id);

        List<AsistenciaDocente> asistenciaDocente = query.list();
        //return asistenciaClase == null ? false : true;
        return asistenciaDocente.size() > 0 ? asistenciaDocente.get(0) : null;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
