package com.profacil.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.profacil.model.Disciplina;
import com.profacil.model.Questao;

public class Questoes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public Questao findById(Long id) {
		return entityManager.find(Questao.class, id);
	}

	public void persist(Questao questao) {
		this.entityManager.persist(questao);
	}

	public void update(Questao questao) {
		this.entityManager.merge(questao);
	}

	public void delete(Questao questao) {
		this.entityManager.remove(this.findById(questao.getId()));
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Questao> filter(String questao) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Questao.class);

		if (questao != null && !questao.equals(""))
			criteria.add(Restrictions.ilike("descricao", "%" + questao + "%"));

		criteria.addOrder(Order.asc("descricao"));

		return criteria.list();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Questao> findByIdDisciplina(Disciplina disciplina) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Questao.class);
		criteria.createAlias("disciplina", "disciplina");

		if (disciplina.getId() != null)
			criteria.add(Restrictions.eq("disciplina.id", disciplina.getId()));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Questao> findAll() {
		return entityManager.createQuery("SELECT m FROM Questao m ORDER BY m.descricao").getResultList();
	}
}
