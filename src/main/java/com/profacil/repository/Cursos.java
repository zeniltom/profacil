package com.profacil.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.profacil.model.Acervo;
import com.profacil.model.Curso;

public class Cursos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public Curso findById(Long id) {
		return entityManager.find(Curso.class, id);
	}

	public void persist(Curso curso) {
		this.entityManager.persist(curso);
	}

	public void update(Curso curso) {
		this.entityManager.merge(curso);
	}

	public void delete(Curso curso) {
		this.entityManager.remove(this.findById(curso.getId()));
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Curso> filter(String curso, Acervo acervo) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Curso.class);
		criteria.createAlias("acervo", "acervo");

		if (acervo != null && acervo.getId() != null)
			criteria.add(Restrictions.eq("acervo.id", acervo.getId()));

		if (curso != null && !curso.equals(""))
			criteria.add(Restrictions.ilike("descricao", "%" + curso + "%"));

		criteria.addOrder(Order.asc("descricao"));

		return criteria.list();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Curso> findByAcervo(Acervo acervo) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Curso.class);
		criteria.createAlias("acervo", "acervo");

		if (acervo != null && acervo.getId() != null)
			criteria.add(Restrictions.eq("acervo.id", acervo.getId()));

		criteria.addOrder(Order.asc("descricao"));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Curso> findAll() {
		return entityManager.createQuery("SELECT m FROM Curso m ORDER BY m.descricao").getResultList();
	}
}
