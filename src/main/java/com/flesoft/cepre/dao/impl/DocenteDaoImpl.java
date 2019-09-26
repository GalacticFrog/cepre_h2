package com.flesoft.cepre.dao.impl;

import com.flesoft.cepre.dao.DocenteDao;
import com.flesoft.cepre.dao.UsuarioDao;
import com.flesoft.cepre.model.Docente;
import com.flesoft.cepre.model.Usuario;
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
import org.springframework.context.annotation.PropertySource;

@Transactional
@Repository
@PropertySource("classpath:config.properties")
public class DocenteDaoImpl implements DocenteDao {

    private static final Log LOG = LogFactory.getLog(DocenteDaoImpl.class);

    private SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    @Override
    public List<Docente> listar() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Docente d").list();
    }

    @Transactional(readOnly = true)
    @Override
    public Docente buscarPorId(int id) {
        Session session = sessionFactory.getCurrentSession(); //.createQuery("from Usuario u").list();
        Docente docente = (Docente) session.get(Docente.class, id);
        return docente;
    }

    @Override
    public Docente insertar(Docente docente) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(docente);
        LOG.info("docente guardado con el id: " + docente.getId());
        return docente;
    }

    @Override
    public Docente insertar(Docente docente, ByteArrayInputStream datosHuella, Integer tamanioHuella) {
        Session session = sessionFactory.getCurrentSession();
        Blob blobHuella = Hibernate.getLobCreator(session).createBlob(datosHuella, tamanioHuella);
        docente.setHuella(blobHuella);
        session.saveOrUpdate(docente);
        LOG.info("docente guardado con el id: " + docente.getId());
        return docente;
    }

    @Override
    public Docente guardar(Docente docente) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(docente);
        LOG.info("docente editado con el id: " + docente.getId());
        return docente;
    }

    @Override
    public void eliminar(Docente docente) {
        sessionFactory.getCurrentSession().delete(docente);
        LOG.info("docente borrado con el id: " + docente.getId());
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
