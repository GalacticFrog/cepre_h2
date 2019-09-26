package com.flesoft.cepre.dao.impl;

import com.flesoft.cepre.dao.HuellaDao;
import com.flesoft.cepre.model.AulaEstudiante;
import com.flesoft.cepre.model.Docente;
import com.flesoft.cepre.model.Estudiante;
import com.flesoft.cepre.model.Huella;
import java.io.ByteArrayInputStream;
import java.sql.Blob;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import javax.annotation.Resource;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.query.Query;

@Transactional
@Repository
public class HuellaoDaoImpl implements HuellaDao {

    private static final Log LOG = LogFactory.getLog(HuellaoDaoImpl.class);

    private SessionFactory sessionFactory;

    @Override
    @Transactional(readOnly = true)
    public List<Huella> listar() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Huella h");
        List<Huella> huellas = query.list();
        return huellas;
    }

    @Override
    @Transactional
    public Huella buscarPorId(long id) {
        Session session = sessionFactory.getCurrentSession();
        Huella huella = (Huella) session.get(Huella.class, id);
        return huella;
    }

    @Transactional(readOnly = true)
    @Override
    public Huella buscarPorDni(String dni) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM Huella h WHERE h.dni=:dni");
        query.setParameter("dni", dni);
        Huella huella = (Huella) query.uniqueResult();
        return huella;

    }

    @Override
    @Transactional
    public void guardar(Huella huella) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(huella);
        LOG.info("Huella guardado con el id: " + huella.getId());
    }

    @Override
    public Huella insertar(Huella huella, byte[] datosHuella, Integer tamanioHuella) {
        Session session = sessionFactory.getCurrentSession();
        //Blob blobHuella = Hibernate.getLobCreator(session).createBlob(datosHuella, tamanioHuella);
        huella.setHuella(datosHuella);
        session.saveOrUpdate(huella);
        LOG.info("huella guardado con el id: " + huella.getId());
        return huella;
    }

    @Override
    @Transactional
    public void eliminar(Huella huella) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(huella);
        LOG.info("Huella borrado con el id: " + huella.getId());
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
