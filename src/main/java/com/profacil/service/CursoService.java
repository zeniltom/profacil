package com.profacil.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.profacil.model.Acervo;
import com.profacil.model.Curso;
import com.profacil.repository.Cursos;
import com.profacil.util.jpa.Transactional;

public class CursoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Cursos cursos;

	@Transactional
	public void createNew(Curso curso) {
		cursos.persist(curso);
	}

	@Transactional
	public void update(Curso curso) {
		cursos.update(curso);
	}

	@Transactional
	public void delete(Curso curso) {
		cursos.delete(curso);
	}

	@Transactional
	public List<Curso> findAll() {
		return cursos.findAll();
	}

	@Transactional
	public List<Curso> findByAcervo(Acervo acervo) {
		return cursos.findByAcervo(acervo);
	}

	@Transactional
	public List<Curso> filter(String curso, Acervo acervo) {
		return cursos.filter(curso, acervo);
	}

}
