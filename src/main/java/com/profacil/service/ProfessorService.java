package com.profacil.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.profacil.model.Professor;
import com.profacil.repository.Professores;
import com.profacil.util.jpa.Transactional;

public class ProfessorService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Professores professores;

	@Transactional
	public void createNew(Professor professor) {
		professores.persist(professor);
	}

	@Transactional
	public void update(Professor professor) {
		professores.update(professor);
	}

	@Transactional
	public void delete(Professor professor) {
		professores.delete(professor);
	}

	@Transactional
	public List<Professor> findAll() {
		return professores.findAll();
	}

	@Transactional
	public Professor findByEmail(Professor professor) {
		return professores.findByEmail(professor.getEmail());
	}

	@Transactional
	public List<Professor> filter(Professor professor) {
		return professores.filter(professor);
	}
}
