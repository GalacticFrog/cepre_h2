/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flesoft.cepre.dao.impl;

import com.flesoft.cepre.model.AulaExamen;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import javax.annotation.Resource;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.flesoft.cepre.dao.AulaExamenDao;
import com.flesoft.cepre.model.AsistenciaExamen;
import com.flesoft.cepre.model.AulaEstudiante;
import java.util.List;

@Transactional
@Repository
public class AulaExamenDaoImpl implements AulaExamenDao {

    private static final Log LOG = LogFactory.getLog(AulaExamenDaoImpl.class);

    private SessionFactory sessionFactory;

//    @Override
//    @Transactional(readOnly = true)
//    public List<PabellonE> listar() {
//        return sessionFactory.getCurrentSession().createQuery("from PabellonE c").list();
//    }
    @Override
    @Transactional(readOnly = true)
    public AulaExamen buscar(long estudiante_id, long aulae_id, long examen_id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from AulaExamen where estudiante_id=:estudiante_id "
                + "AND aulae_id=:aulae_id "
                + "AND examen_id=:examen_id");
        query.setParameter("estudiante_id", estudiante_id);
        query.setParameter("aulae_id", aulae_id);
        query.setParameter("examen_id", examen_id);

        AulaExamen aulaEstudiante = (AulaExamen) query.uniqueResult();
        //return asistenciaClase == null ? false : true;
        return aulaEstudiante;
    }

    @Override
    @Transactional
    public void guardar(AulaExamen aulaExamen) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(aulaExamen);
        LOG.info("Guardado aulaExamen con el id: " + aulaExamen.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AulaExamen> listarPorExamenAula(long examen_id, long aulae_id) {
        Session session = sessionFactory.getCurrentSession();

        Query selectQuery = session.createQuery("FROM AulaExamen ae WHERE ae.examen.id=:examen_id AND ae.aulae.id=:aulae_id ORDER BY ae.aulae.denominacion, ae.estudiante.apellidos");
        selectQuery.setParameter("examen_id", examen_id);
        selectQuery.setParameter("aulae_id", aulae_id);

        List<AulaExamen> result = selectQuery.list();

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AulaExamen> listarPorExamen(long examen_id, long pabellone_id) {
        Session session = sessionFactory.getCurrentSession();
        Query selectQuery = session.createQuery("FROM AulaExamen ae WHERE ae.examen.id=:examen_id AND ae.aulae.pabellone.id=:pabellone_id ORDER BY ae.aulae.denominacion, ae.estudiante.apellidos");
        selectQuery.setParameter("examen_id", examen_id);
        selectQuery.setParameter("pabellone_id", pabellone_id);

        List<AulaExamen> result = selectQuery.list();
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AulaExamen> listarPorDni(String dni) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM AulaExamen ae WHERE ae.estudiante.dni like :dni ORDER BY ae.aulae.denominacion, ae.estudiante.apellidos");
        query.setParameter("dni", dni + "%");
        return query.list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AulaExamen> listarPorApellido(String apellidos) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM AulaExamen ae WHERE ae.estudiante.apellidos like :apellidos ORDER BY ae.aulae.denominacion, ae.estudiante.apellidos");
        query.setParameter("apellidos", apellidos + "%");
        return query.list();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
