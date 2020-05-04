package com.profacil.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.profacil.model.ProfessorGrupo;
import com.profacil.repository.ProfessorGrupos;
import com.profacil.util.jpa.Transactional;

public class CadastroProfessorGrupoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProfessorGrupos ProfessorGrupos;

	@Transactional
	public void createNew(ProfessorGrupo ProfessorGrupo) {
		ProfessorGrupos.persist(ProfessorGrupo);
	}

	@Transactional
	public void update(ProfessorGrupo ProfessorGrupo) {
		ProfessorGrupos.update(ProfessorGrupo);
	}

	@Transactional
	public void delete(ProfessorGrupo ProfessorGrupo) {
		ProfessorGrupos.delete(ProfessorGrupo);
	}

	@Transactional
	public List<ProfessorGrupo> findAll() {
		return ProfessorGrupos.findAll();
	}
}
