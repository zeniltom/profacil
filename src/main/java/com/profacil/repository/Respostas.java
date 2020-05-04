package com.profacil.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.profacil.model.Questao;
import com.profacil.model.Resposta;

public class Respostas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public Resposta findById(Long id) {
		return entityManager.find(Resposta.class, id);
	}

	public void persist(Resposta resposta) {
		this.entityManager.persist(resposta);
	}

	public void update(Resposta resposta) {
		this.entityManager.merge(resposta);
	}

	public void delete(Resposta resposta) {
		this.entityManager.remove(this.findById(resposta.getId()));
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Resposta> filter(String resposta) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Resposta.class);

		if (resposta != null && !resposta.equals(""))
			criteria.add(Restrictions.ilike("descricao", "%" + resposta + "%"));

		criteria.add(Restrictions.eqOrIsNull("respostaPai", null));

		criteria.addOrder(Order.asc("descricao"));

		return criteria.list();
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Resposta> findByIdQuestao(Questao questao) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Resposta.class);
		criteria.createAlias("questao", "questao");

		if (questao.getId() != null)
			criteria.add(Restrictions.eq("questao.id", questao.getId()));

		criteria.addOrder(Order.asc("descricao"));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Resposta> findAll() {
		return entityManager.createQuery("SELECT m FROM Resposta m ORDER BY m.descricao").getResultList();
	}
}
