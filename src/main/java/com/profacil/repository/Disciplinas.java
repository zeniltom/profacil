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
import com.profacil.model.Disciplina;

public class Disciplinas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public Disciplina findById(Long id) {
		return entityManager.find(Disciplina.class, id);
	}

	public void persist(Disciplina disciplina) {
		this.entityManager.persist(disciplina);
	}

	public void update(Disciplina disciplina) {
		this.entityManager.merge(disciplina);
	}

	public void delete(Disciplina disciplina) {
		this.entityManager.remove(this.findById(disciplina.getId()));
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Disciplina> filter(String disciplina, Acervo acervo) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Disciplina.class);
		criteria.createAlias("curso", "curso");
		criteria.createAlias("curso.acervo", "acervo");

		if (acervo != null && acervo.getId() != null)
			criteria.add(Restrictions.eq("acervo.id", acervo.getId()));

		if (disciplina != null && !disciplina.equals(""))
			criteria.add(Restrictions.ilike("descricao", "%" + disciplina + "%"));

		criteria.addOrder(Order.asc("curso.descricao"));
		criteria.addOrder(Order.asc("descricao"));

		return criteria.list();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Disciplina> findByCurso(Curso curso) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Disciplina.class);
		criteria.createAlias("curso", "curso");

		if (curso.getId() != null)
			criteria.add(Restrictions.eq("curso.id", curso.getId()));

		return criteria.list();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Disciplina> findById(Disciplina disciplina) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Disciplina.class);

		if (disciplina.getId() != null)
			criteria.add(Restrictions.eq("id", disciplina.getId()));

		return criteria.list();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Disciplina> findByAcervo(Acervo acervo) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Disciplina.class);
		criteria.createAlias("curso", "curso");
		criteria.createAlias("curso.acervo", "acervo");

		if (acervo != null && acervo.getId() != null)
			criteria.add(Restrictions.eq("acervo.id", acervo.getId()));

		criteria.addOrder(Order.asc("descricao"));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Disciplina> findAll() {
		return entityManager.createQuery("SELECT m FROM Disciplina m ORDER BY m.descricao").getResultList();
	}
}
