package com.flesoft.cepre.dao.impl;

import com.flesoft.cepre.dao.EstudianteDao;
import com.flesoft.cepre.model.AulaE;
import com.flesoft.cepre.model.AulaEstudiante;
import com.flesoft.cepre.model.AulaExamen;
import com.flesoft.cepre.model.Estudiante;
import com.flesoft.cepre.model.Huella;
import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import javax.annotation.Resource;
import org.hibernate.Session;

import org.hibernate.Hibernate;
import org.hibernate.query.Query;

@Transactional
@Repository

public class EstudianteDaoImpl implements EstudianteDao {

    private static final Log LOG = LogFactory.getLog(EstudianteDaoImpl.class);

    private SessionFactory sessionFactory;

    @Override
    @Transactional(readOnly = true)
    public List<Estudiante> listar() {
        return sessionFactory.getCurrentSession().createQuery("from Estudiante c").list();
    }

    @Override
    public Estudiante guardar(Estudiante estudiante) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(estudiante);
        LOG.info("Estudiante guardado con el id: " + estudiante.getId());
        return estudiante;
    }

    @Override
    public Estudiante buscarPorDni(String dni) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from Estudiante where dni=:dni");
        query.setParameter("dni", dni);

        Estudiante estudiante = (Estudiante) query.uniqueResult();
        return estudiante;
    }

    @Override
    public AulaEstudiante buscarAula(long estudiante_id) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM AulaEstudiante where estudiante_id=:estudiante_id");
        query.setParameter("estudiante_id", estudiante_id);
        AulaEstudiante aulaEstudiante = (AulaEstudiante) query.uniqueResult();
        return aulaEstudiante;
    }

    @Override
    public AulaExamen buscarAulaExamen(long estudiante_id, long examen_id) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from AulaExamen where"
                + " estudiante_id=:estudiante_id"
                + " AND examen_id=:examen_id");
        query.setParameter("estudiante_id", estudiante_id);
        query.setParameter("examen_id", examen_id);
        List<AulaExamen> res = query.list();
        if (res.size() > 0) {
            LOG.info("registros: " + res.size());
        }
        AulaExamen aulaExamen = res.size() > 0 ? (AulaExamen) res.get(0) : null;//bug #124 notUniqueResultException
        return aulaExamen;
    }

    @Override
    public Huella guardarHuella(Huella huella, ByteArrayInputStream datosHuella, Integer tamanioHuella) {
        Session session = sessionFactory.getCurrentSession();

        Blob blobHuella = Hibernate.getLobCreator(session).createBlob(datosHuella, tamanioHuella);
        //huella.setHuella(blobHuella);

        session.saveOrUpdate(huella);
        LOG.info("Huella guardado con el id: " + huella.getId());

        return huella;
    }

    @Override
    public Estudiante buscarPorId(long id) {
        Session session = sessionFactory.getCurrentSession();

        Estudiante estudiante = (Estudiante) session.get(Estudiante.class, id);
        return estudiante;
    }

    @Override
    public boolean existeHuellaCiclo(long estudiante_id) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from Huella where estudiante_id=:estudiante_id");
        query.setParameter("estudiante_id", estudiante_id);
        Huella huella = (Huella) query.uniqueResult();
        return huella == null ? false : true;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
