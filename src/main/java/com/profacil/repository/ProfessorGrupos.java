package com.profacil.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.profacil.model.Professor;
import com.profacil.model.ProfessorGrupo;

public class ProfessorGrupos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager entityManager;

	public ProfessorGrupo findById(Long id) {
		return this.entityManager.find(ProfessorGrupo.class, id);
	}

	public void persist(ProfessorGrupo professorGrupo) {
		this.entityManager.persist(professorGrupo);
	}

	public void update(ProfessorGrupo professorGrupo) {
		this.entityManager.merge(professorGrupo);
	}

	public void delete(ProfessorGrupo professorGrupo) {
		this.entityManager.remove(this.findByProfessorANDGrupo(professorGrupo).get(0));
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<ProfessorGrupo> filter(ProfessorGrupo professorGrupo) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(ProfessorGrupo.class);

		criteria.addOrder(Order.asc("nome"));

		return criteria.list();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<ProfessorGrupo> findByProfessor(Professor professor) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(ProfessorGrupo.class);
		criteria.createAlias("professor", "professor");
		criteria.createAlias("grupo", "grupo");

		if (professor.getId() != null)
			criteria.add(Restrictions.eq("professor.id", professor.getId()));

		return criteria.list();
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	public List<ProfessorGrupo> findByProfessorANDGrupo(ProfessorGrupo professorGrupo) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(ProfessorGrupo.class);
		criteria.createAlias("professor", "professor");
		criteria.createAlias("grupo", "grupo");

		if (professorGrupo.getProfessor() != null && professorGrupo.getProfessor().getId() != null
				&& professorGrupo.getProfessor().getId() != 0)
			criteria.add(Restrictions.eq("professor.id", professorGrupo.getProfessor().getId()));

		if (professorGrupo.getGrupo() != null && professorGrupo.getGrupo().getNome() != null
				&& !professorGrupo.getGrupo().getNome().equals(""))
			criteria.add(Restrictions.eq("grupo.nome", professorGrupo.getGrupo().getNome()));

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<ProfessorGrupo> findAll() {
		return entityManager.createQuery("SELECT m FROM ProfessorGrupo m").getResultList();
	}

}