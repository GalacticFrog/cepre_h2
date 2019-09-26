package com.flesoft.cepre.dao.impl;

import com.flesoft.cepre.dao.UsuarioDao;
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
public class UsuarioDaoImpl implements UsuarioDao {

    private static final Log LOG = LogFactory.getLog(UsuarioDaoImpl.class);

    private SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    @Override
    public List<Usuario> listar() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Usuario u").list();
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario buscarPorId(int id) {
        Session session = sessionFactory.getCurrentSession(); //.createQuery("from Usuario u").list();
        Usuario usuario = (Usuario) session.get(Usuario.class, id);
        return usuario;
    }

    @Override
    public Usuario buscarPorNombre(String nombre) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Usuario where usuario=:usuario");
        query.setParameter("usuario", nombre);
        Usuario usuario = (Usuario) query.uniqueResult();
        return usuario;
    }

    @Override
    public Usuario insertar(Usuario usuario, ByteArrayInputStream datosHuella, Integer tamanioHuella) {
        Session session = sessionFactory.getCurrentSession();
        Blob blobHuella = Hibernate.getLobCreator(session).createBlob(datosHuella, tamanioHuella);
        usuario.setHuella(blobHuella);
        session.saveOrUpdate(usuario);
        LOG.info("usuario guardado con el id: " + usuario.getId());
        return usuario;
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(usuario);
        LOG.info("usuario editado con el id: " + usuario.getId());
        return usuario;
    }

    @Override
    public void eliminar(Usuario usuario) {
        sessionFactory.getCurrentSession().delete(usuario);
        LOG.info("usuario borrado con el id: " + usuario.getId());
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
