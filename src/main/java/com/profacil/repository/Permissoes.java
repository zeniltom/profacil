package com.profacil.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.profacil.model.Grupo;
import com.profacil.model.Permissao;

public class Permissoes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public Permissao findById(Long id) {
		return this.entityManager.find(Permissao.class, id);
	}

	public void persist(Permissao permissao) {
		this.entityManager.persist(permissao);
	}

	public void update(Permissao permissao) {
		this.entityManager.merge(permissao);
	}

	public void delete(Permissao permissao) {
		this.entityManager.remove(this.findById(permissao.getId()));
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Permissao> filter(Permissao BETAPermissao) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Permissao.class);

		criteria.addOrder(Order.asc("nome"));

		return criteria.list();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Permissao> findComponenteByGrupo(Grupo grupo) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Permissao.class);
		criteria.createAlias("grupo", "grupo");

		if (grupo != null && grupo.getId() != null)
			criteria.add(Restrictions.eq("grupo.id", grupo.getId()));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Permissao> findAll() {
		return entityManager.createQuery("Select m from Permissao m").getResultList();
	}
}