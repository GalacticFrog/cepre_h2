package com.flesoft.cepre.dao.impl;

import com.flesoft.cepre.dao.AsistenciaClaseDao;
import com.flesoft.cepre.model.AsistenciaClase;
import com.flesoft.cepre.model.Huella;
import java.util.Date;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import javax.annotation.Resource;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.type.DateType;

@Transactional
@Repository
public class AsistenciaClaseDaoImpl implements AsistenciaClaseDao {

    private static final Log LOG = LogFactory.getLog(AsistenciaClaseDaoImpl.class);

    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void guardar(AsistenciaClase asistenciaClase) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(asistenciaClase);
        LOG.info("asistenciaClase guardado con el id: " + asistenciaClase.getId());
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean existeHuellaCiclo(long aula_estudio_id) {
        Session session = sessionFactory.getCurrentSession();
        java.util.Date fecha = new Date();
        Query query = session.createQuery("FROM AsistenciaClase where "
                + "aula_estudio_id=:aula_estudio_id "
                + "AND fecha=:fecha");
        query.setParameter("aula_estudio_id", aula_estudio_id);
        query.setParameter("fecha", fecha, DateType.INSTANCE);
        AsistenciaClase asistenciaClase = (AsistenciaClase) query.uniqueResult();

        //return asistenciaClase == null ? false : true;
        if (asistenciaClase == null) {
            return false;
        } else {
            return true;
        }
    }
}
