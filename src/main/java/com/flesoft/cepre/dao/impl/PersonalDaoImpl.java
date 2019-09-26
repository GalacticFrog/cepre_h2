package com.flesoft.cepre.dao.impl;

import com.flesoft.cepre.dao.PersonalDao;
import com.flesoft.cepre.model.Personal;
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
public class PersonalDaoImpl implements PersonalDao {

    private static final Log LOG = LogFactory.getLog(PersonalDaoImpl.class);

    private SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    @Override
    public List<Personal> listar() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Personal p").list();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Personal> listarConHuella() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Personal p WHERE p.huella IS NOT NULL").list();
    }

    @Transactional(readOnly = true)
    @Override
    public Personal buscarPorId(int id) {
        Session session = sessionFactory.getCurrentSession(); //.createQuery("from Usuario u").list();
        Personal personal = (Personal) session.get(Personal.class, id);
        return personal;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Personal> buscarPorApellidos(String str) {
        Session session = sessionFactory.getCurrentSession(); //.createQuery("from Usuario u").list();
        Query query = session.createQuery("FROM Personal p where p.persona.apellidos LIKE :str OR p.persona.nombres LIKE :str");
        query.setParameter("str", str + "%");

        return query.list();
    }

    @Override
    public Personal buscarPorDni(String dni) {
        Session session = sessionFactory.getCurrentSession(); //.createQuery("from Usuario u").list();
        Query query = session.createQuery("FROM Personal p where p.persona.dni = :dni");
        query.setParameter("dni", dni);
        Personal personal = (Personal) query.uniqueResult();

        return personal;
    }

    @Override
    public Personal insertar(Personal personal) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(personal);
        LOG.info("personal guardado con el id: " + personal.getId());
        return personal;
    }

    @Override
    public Personal insertar(Personal personal, ByteArrayInputStream datosHuella, Integer tamanioHuella) {
        Session session = sessionFactory.getCurrentSession();
        Blob blobHuella = Hibernate.getLobCreator(session).createBlob(datosHuella, tamanioHuella);
        personal.setHuella(blobHuella);
        session.saveOrUpdate(personal);
        LOG.info("personal guardado con el id: " + personal.getId());
        return personal;
    }

    @Override
    public Personal guardar(Personal personal) {
        Session session = sessionFactory.getCurrentSession();

        session.update(personal);
        LOG.info("personal editado/guarddo con el id: " + personal.getId());
        return personal;
    }

    @Override
    public void eliminar(Personal personal) {
        sessionFactory.getCurrentSession().delete(personal);
        LOG.info("personal borrado con el id: " + personal.getId());
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
