package com.profacil.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.profacil.model.Preferencia;
import com.profacil.model.Professor;

public class Preferencias implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public Preferencia findById(Long id) {
		return entityManager.find(Preferencia.class, id);
	}

	public void persist(Preferencia preferencia) {
		this.entityManager.persist(preferencia);
	}

	public void update(Preferencia preferencia) {
		this.entityManager.merge(preferencia);
	}

	public void delete(Preferencia preferencia) {
		this.entityManager.remove(this.findById(preferencia.getId()));
	}

	@SuppressWarnings({ "deprecation" })
	public Preferencia findByProfessor(Professor professor) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Preferencia.class);
		criteria.createAlias("professor", "professor");

		if (professor.getId() != null)
			criteria.add(Restrictions.eq("professor.id", professor.getId()));

		return (Preferencia) criteria.uniqueResult();
	}
}
