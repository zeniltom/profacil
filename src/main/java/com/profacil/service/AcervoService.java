package com.profacil.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.profacil.model.Acervo;
import com.profacil.model.Professor;
import com.profacil.repository.Acervos;
import com.profacil.util.jpa.Transactional;

public class AcervoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Acervos acervos;

	@Transactional
	public void createNew(Acervo acervo) {
		acervos.persist(acervo);
	}

	@Transactional
	public void update(Acervo acervo) {
		acervos.update(acervo);
	}

	@Transactional
	public void delete(Acervo acervo) {
		acervos.delete(acervo);
	}

	@Transactional
	public Acervo findByProfessor(Professor professor) {
		return acervos.findByProfessor(professor);
	}
}
