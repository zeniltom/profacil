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
import com.profacil.model.Prova;

public class Provas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public Prova findById(Long id) {
		return entityManager.find(Prova.class, id);
	}

	public void persist(Prova prova) {
		this.entityManager.persist(prova);
	}

	public void update(Prova prova) {
		this.entityManager.merge(prova);
	}

	public void delete(Prova prova) {
		this.entityManager.remove(this.findById(prova.getId()));
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Prova> filter(String prova, Acervo acervo) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Prova.class);
		criteria.createAlias("acervo", "acervo");
		criteria.createAlias("disciplina", "disciplina");

		if (acervo != null && acervo.getId() != null)
			criteria.add(Restrictions.eq("acervo.id", acervo.getId()));

		if (prova != null && !prova.equals(""))
			criteria.add(Restrictions.ilike("descricao", "%" + prova + "%"));

		criteria.addOrder(Order.asc("disciplina.curso.id"));
		criteria.addOrder(Order.asc("disciplina.descricao"));
		criteria.addOrder(Order.asc("descricao"));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Prova> findAll() {
		return entityManager.createQuery("SELECT m FROM Prova m ORDER BY m.descricao").getResultList();
	}
}
