package com.flesoft.cepre.dao.impl;

import com.flesoft.cepre.dao.PersonaDao;
import com.flesoft.cepre.model.Persona;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
@PropertySource("classpath:config.properties")
public class PersonaDaoImpl implements PersonaDao {

    private static final Log LOG = LogFactory.getLog(PersonaDaoImpl.class);

    private SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    @Override
    public List<Persona> listar() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Persona p").list();
    }

    public Persona guardar(Persona usuario) {
        sessionFactory.getCurrentSession().saveOrUpdate(usuario);
        LOG.info("usuario guardado con el id: " + usuario.getId());
        return usuario;
    }

    @Override
    public List<Persona> buscar(String dni) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Persona where dni=:dni");
        query.setParameter("dni", dni);
        //Persona persona = (Persona) query.uniqueResult();
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
