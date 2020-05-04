package com.profacil.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.profacil.model.Acervo;
import com.profacil.model.Professor;

public class Acervos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public Acervo findById(Long id) {
		return entityManager.find(Acervo.class, id);
	}

	public void persist(Acervo acervo) {
		this.entityManager.persist(acervo);
	}

	public void update(Acervo acervo) {
		this.entityManager.merge(acervo);
	}

	public void delete(Acervo acervo) {
		String AcervoID = "0";

		if (acervo != null && acervo.getId() != null)
			AcervoID = acervo.getId().toString();

		this.entityManager.createNativeQuery("CALL excluirAcervo(" + AcervoID + ")").executeUpdate();
	}

	@SuppressWarnings({ "deprecation" })
	public Acervo findByProfessor(Professor professor) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Acervo.class);
		criteria.createAlias("professor", "professor");

		if (professor.getId() != null)
			criteria.add(Restrictions.eq("professor.id", professor.getId()));

		return (Acervo) criteria.uniqueResult();
	}
}
