package com.profacil.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.profacil.model.Componente;

public class Componentes implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public Componente findById(Long id) {
		return this.entityManager.find(Componente.class, id);
	}

	public void persist(Componente componente) {
		this.entityManager.persist(componente);
	}

	public void update(Componente componente) {
		this.entityManager.merge(componente);
	}

	public void delete(Componente componente) {
		this.entityManager.remove(this.findById(componente.getId()));
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Componente> findByNome(Componente componente) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Componente.class);

		if (componente.getNome() != null && !componente.getNome().equals(""))
			criteria.add(Restrictions.ilike("nome", "%" + componente.getNome() + "%"));

		criteria.addOrder(Order.asc("nome"));

		return criteria.list();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Componente> filter(Componente componente) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Componente.class);

		if (componente.getNome() != null && !componente.getNome().equals(""))
			criteria.add(Restrictions.ilike("nome", "%" + componente.getNome() + "%"));

		criteria.addOrder(Order.asc("nome"));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Componente> findAll() {
		return entityManager.createQuery("SELECT m FROM Componente m ORDER BY m.id").getResultList();
	}
}