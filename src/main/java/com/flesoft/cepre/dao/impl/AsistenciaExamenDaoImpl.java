package com.flesoft.cepre.dao.impl;

import com.flesoft.cepre.dao.AsistenciaExamenDao;
import com.flesoft.cepre.model.AsistenciaExamen;
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
public class AsistenciaExamenDaoImpl implements AsistenciaExamenDao {

    private static final Log LOG = LogFactory.getLog(AsistenciaExamenDaoImpl.class);
    private SessionFactory sessionFactory;

    public long totalRecords() {
        Session session = sessionFactory.getCurrentSession();
        Query countQuery = session.createQuery("SELECT COUNT (ae.id) FROM AsistenciaExamen ae");
        long countResults = (Long) countQuery.uniqueResult();
        return countResults;
    }

    @Override
    public long registrosPorExamen(long examen_id) {
        Session session = sessionFactory.getCurrentSession();
        Query countQuery = session.createQuery("SELECT COUNT (ae.id) FROM AsistenciaExamen ae WHERE ae.examen.id=:examen_id");
        countQuery.setParameter("examen_id", examen_id);
        long countResults = (Long) countQuery.uniqueResult();
        return countResults;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsistenciaExamen> listar() {
        return sessionFactory.getCurrentSession().createQuery("from AsistenciaExamen ae").list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsistenciaExamen> listar(int numeroPagina) {
        Session session = sessionFactory.getCurrentSession();
        int paginaTamano = 100;

        Query selectQuery = session.createQuery("FROM AsistenciaExamen ae ORDER BY ae.aulaExamen.estudiante.apellidos");
        selectQuery.setFirstResult((numeroPagina - 1) * paginaTamano);
        selectQuery.setMaxResults(paginaTamano);
        List<AsistenciaExamen> result = selectQuery.list();

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsistenciaExamen> listarPorExamenAula(long examen_id, long aulae_id) {
        Session session = sessionFactory.getCurrentSession();

        Query selectQuery = session.createQuery("FROM AsistenciaExamen ae WHERE ae.examen.id=:examen_id AND ae.aulaExamen.aulae.id=:aulae_id ORDER BY ae.aulaExamen.aulae.denominacion, ae.aulaExamen.estudiante.apellidos");
        selectQuery.setParameter("examen_id", examen_id);
        selectQuery.setParameter("aulae_id", aulae_id);

        List<AsistenciaExamen> result = selectQuery.list();

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AsistenciaExamen> listarPorExamen(long examen_id, long pabellone_id) {
        Session session = sessionFactory.getCurrentSession();
        Query selectQuery = session.createQuery("FROM AsistenciaExamen ae WHERE ae.examen.id=:examen_id AND ae.aulaExamen.aulae.pabellone.id=:pabellone_id ORDER BY ae.aulaExamen.aulae.denominacion, ae.aulaExamen.estudiante.apellidos");
        selectQuery.setParameter("examen_id", examen_id);
        selectQuery.setParameter("pabellone_id", pabellone_id);

        List<AsistenciaExamen> result = selectQuery.list();
        return result;
    }

    @Override
    @Transactional
    public void guardar(AsistenciaExamen asistenciaExamen) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(asistenciaExamen);
        LOG.info("asistenciaExamen guardado con el id: " + asistenciaExamen.getId());
    }

    @Override
    public boolean existeAsistencia(Date fecha, long aula_examen_id, long examen_id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM AsistenciaExamen where "
                + "DATE(fecha)=:fecha "
                + "AND aula_examen_id=:aula_examen_id "
                + "AND examen_id=:examen_id");
        query.setParameter("fecha", fecha, DateType.INSTANCE);
        query.setParameter("aula_examen_id", aula_examen_id);
        query.setParameter("examen_id", examen_id);

        AsistenciaExamen asistenciaExamen = (AsistenciaExamen) query.uniqueResult();
        //return asistenciaClase == null ? false : true;
        return asistenciaExamen != null;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
