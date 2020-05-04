package com.profacil.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.profacil.model.Professor;

public class Professores implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public Professor findById(Long id) {
		return this.entityManager.find(Professor.class, id);
	}

	public void persist(Professor professor) {
		this.entityManager.persist(professor);
	}

	public void update(Professor professor) {
		this.entityManager.merge(professor);
	}

	public void delete(Professor professor) {
		this.entityManager.remove(this.findById(professor.getId()));
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<Professor> filter(Professor professor) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Professor.class);

		if (professor.getNome() != null && !(professor.getNome().equals("")))
			criteria.add(Restrictions.ilike("nome", "%" + professor.getNome() + "%"));

		criteria.addOrder(Order.asc("nome"));

		return criteria.list();
	}

	public Professor findByEmail(String email) {
		Professor professor = null;

		try {
			professor = this.entityManager.createQuery("FROM Professor WHERE LOWER(email) = :email", Professor.class)
					.setParameter("email", email.toLowerCase()).getSingleResult();
		} catch (NoResultException e) {
			throw new UsernameNotFoundException("Usuário não encontrado.");
		}

		return professor;
	}

	@SuppressWarnings("unchecked")
	public List<Professor> findAll() {
		return entityManager.createQuery("SELECT m FROM Professor m ORDER BY m.nome").getResultList();
	}
}