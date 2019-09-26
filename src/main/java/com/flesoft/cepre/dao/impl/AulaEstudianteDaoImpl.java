package com.flesoft.cepre.dao.impl;

import com.flesoft.cepre.dao.AulaEstudianteDao;
import com.flesoft.cepre.model.AulaEstudiante;
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
public class AulaEstudianteDaoImpl implements AulaEstudianteDao {

    private static final Log LOG = LogFactory.getLog(AulaEstudianteDaoImpl.class);
    private SessionFactory sessionFactory;

    @Override
    public long totalRecords() {
        Session session = sessionFactory.getCurrentSession();
        Query countQuery = session.createQuery("SELECT COUNT (ae.id) FROM AulaEstudiante ae");
        long countResults = (Long) countQuery.uniqueResult();
        return countResults;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AulaEstudiante> listar() {
        return sessionFactory.getCurrentSession().createQuery("FROM AulaEstudiante ae ORDER BY ae.estudiante.apellidos, ae.aula.denominacion").list();
    }

    @Override
    public List<AulaEstudiante> listar2() {
        return sessionFactory.getCurrentSession().createQuery("FROM AulaEstudiante ae WHERE ae.jsonTemplate <>''").list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AulaEstudiante> listarConHuella() {
        return sessionFactory.getCurrentSession().createQuery("FROM AulaEstudiante ae WHERE ae.huella IS NOT NULL").list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AulaEstudiante> listar(int numeroPagina, long countResults) {
        //return sessionFactory.getCurrentSession().createQuery("FROM AulaEstudiante ae ORDER BY ae.estudiante.apellidos, ae.aula.denominacion").list();
        Session session = sessionFactory.getCurrentSession();
//        Query countQuery = session.createQuery("SELECT COUNT (ae.id) FROM AulaEstudiante ae");
//        Long countResults = (Long) countQuery.uniqueResult();
        int paginaTamano = 100;
        //int lastPageNumber = (int) (Math.ceil(countResults / pageSize));

        Query selectQuery = session.createQuery("FROM AulaEstudiante ae  ORDER BY ae.estudiante.apellidos");
        selectQuery.setFirstResult((numeroPagina - 1) * paginaTamano);
        selectQuery.setMaxResults(paginaTamano);
        List<AulaEstudiante> result = selectQuery.list();

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AulaEstudiante> listarPorAula(long aula_id, boolean huella) {
        Session session = sessionFactory.getCurrentSession();

        String cond = huella ? "IS NOT NULL" : "IS  NULL";
        String q = "FROM AulaEstudiante ae WHERE ae.aula.id=:aula_id AND ae.huella " + cond + " ORDER BY ae.aula.denominacion, ae.estudiante.apellidos";
        Query query = session.createQuery(q);
        query.setParameter("aula_id", aula_id);
        return query.list();

    }

    @Override
    @Transactional(readOnly = true)
    public List<AulaEstudiante> listarPorPabellon(long pabellon_id, boolean huella) {
        Session session = sessionFactory.getCurrentSession();
        String cond = huella ? "IS NOT NULL" : "IS  NULL";
        String q = "FROM AulaEstudiante ae WHERE ae.aula.pabellon.id=:pabellon_id AND ae.huella " + cond + " ORDER BY ae.aula.denominacion, ae.estudiante.apellidos";
        Query query = session.createQuery(q);
        query.setParameter("pabellon_id", pabellon_id);

        return query.list();

    }

    @Override
    @Transactional(readOnly = true)
    public AulaEstudiante buscarPorEstudianteId(long id) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM AulaEstudiante ae WHERE ae.estudiante.id=:id");
        query.setParameter("id", id);
        AulaEstudiante aulaEstudiante = (AulaEstudiante) query.uniqueResult();
        return aulaEstudiante;

    }

    @Override
    public AulaEstudiante buscarPorEstudianteDni(String dni) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM AulaEstudiante ae WHERE ae.estudiante.dni=:dni");
        query.setParameter("dni", dni);
        AulaEstudiante aulaEstudiante = (AulaEstudiante) query.uniqueResult();
        return aulaEstudiante;
    }

    @Override
    @Transactional(readOnly = true)
    public AulaEstudiante buscarPorId(long id) {
        Session session = sessionFactory.getCurrentSession();
        AulaEstudiante aulaEstudiante = (AulaEstudiante) session.get(AulaEstudiante.class, id);
        return aulaEstudiante;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AulaEstudiante> listarPorDni(String dni) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM AulaEstudiante ae WHERE ae.estudiante.dni like :dni ORDER BY ae.aula.denominacion, ae.estudiante.apellidos");
        query.setParameter("dni", dni + "%");
        return query.list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AulaEstudiante> listarPorApellido(String apellidos) {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("FROM AulaEstudiante ae WHERE ae.estudiante.apellidos like :apellidos ORDER BY ae.aula.denominacion, ae.estudiante.apellidos");
        query.setParameter("apellidos", apellidos + "%");
        return query.list();
    }

    @Override
    @Transactional
    public void guardar(AulaEstudiante aulaEstudiante) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(aulaEstudiante);
        LOG.info("aulaEstudiante guardado con el id: " + aulaEstudiante.getId());
    }

    @Override
    public AulaEstudiante editar(long id, byte[] datosHuella) {

        Session session = sessionFactory.getCurrentSession();
        AulaEstudiante aulaEstudiante = (AulaEstudiante) session.get(AulaEstudiante.class, id);
        aulaEstudiante.setHuella(datosHuella);
        session.saveOrUpdate(aulaEstudiante);
        LOG.info("aulaEstudiante huella editado con el id: " + aulaEstudiante.getId());
        return aulaEstudiante;
    }

    @Override
    public AulaEstudiante insertar(AulaEstudiante aulaEstudiante, byte[] datosHuella, Integer tamanioHuella) {
        Session session = sessionFactory.getCurrentSession();
//        Blob blobHuella = Hibernate.getLobCreator(session).createBlob(datosHuella, tamanioHuella);
        aulaEstudiante.setHuella(datosHuella);
        session.saveOrUpdate(aulaEstudiante);
        LOG.info("aulaEstudiante guardado con el id: " + aulaEstudiante.getId());
        return aulaEstudiante;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
