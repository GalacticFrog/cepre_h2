package com.flesoft.cepre.dao.impl;

import com.flesoft.cepre.dao.AsistenciaPersonalDao;
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
public class AsistenciaPersonalDaoImpl implements AsistenciaPersonalDao {

    private static final Log LOG = LogFactory.getLog(AsistenciaPersonalDaoImpl.class);

    private SessionFactory sessionFactory;

    public long totalRecords() {
        Session session = sessionFactory.getCurrentSession();
        Query countQuery = session.createQuery("SELECT COUNT (ae.id) FROM AsistenciaPersonal ae");
        long countResults = (Long) countQuery.uniqueResult();
        return countResults;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsistenciaPersonal> listar() {
        return sessionFactory.getCurrentSession().createQuery("from AsistenciaPersonal ae").list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsistenciaPersonal> listar(int numeroPagina) {
        Session session = sessionFactory.getCurrentSession();
        int paginaTamano = 100;

        Query selectQuery = session.createQuery("FROM AsistenciaPersonal ae ORDER BY ae.personal.persona.apellidos");
        selectQuery.setFirstResult((numeroPagina - 1) * paginaTamano);
        selectQuery.setMaxResults(paginaTamano);
        List<AsistenciaPersonal> result = selectQuery.list();

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsistenciaPersonal> listarPorExamen(long examen_id) {
        Session session = sessionFactory.getCurrentSession();
        Query selectQuery = session.createQuery("FROM AsistenciaPersonal ae WHERE ae.examen.id=:examen_id"
                + " ORDER BY ae.personal.persona.apellidos");
        selectQuery.setParameter("examen_id", examen_id);

        List<AsistenciaPersonal> result = selectQuery.list();
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsistenciaPersonal> listarPorExamenCargo(long examen_id, long cargo_id) {
        Session session = sessionFactory.getCurrentSession();
        Query selectQuery = session.createQuery("FROM AsistenciaPersonal ae WHERE ae.examen.id=:examen_id AND ae.cargo.id=:cargo_id"
                + " ORDER BY ae.personal.persona.apellidos");
        selectQuery.setParameter("examen_id", examen_id);
        selectQuery.setParameter("cargo_id", cargo_id);

        List<AsistenciaPersonal> result = selectQuery.list();
        return result;
    }

    @Override
    @Transactional
    public void guardar(AsistenciaPersonal asistenciaExamen) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(asistenciaExamen);
        LOG.info("asistenciaPersonal guardado con el id: " + asistenciaExamen.getId());
    }

    @Override
    public AsistenciaPersonal existeAsistencia(long personal_id, long examen_id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM AsistenciaPersonal WHERE "
                //  + "fecha=:fecha "
                + "personal_id=:personal_id "
                + "AND examen_id=:examen_id");
        // query.setParameter("fecha", fecha, DateType.INSTANCE);
        query.setParameter("personal_id", personal_id);
        query.setParameter("examen_id", examen_id);

        AsistenciaPersonal asistenciaExamen = (AsistenciaPersonal) query.uniqueResult();
        //return asistenciaClase == null ? false : true;
        return asistenciaExamen;
    }

    @Override
    public void eliminar(AsistenciaPersonal asistenciaPersonal) {
        sessionFactory.getCurrentSession().delete(asistenciaPersonal);
        LOG.info("AsistenciaPersonal borrado con el id: " + asistenciaPersonal.getId());
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
