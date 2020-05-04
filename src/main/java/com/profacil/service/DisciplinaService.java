package com.profacil.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.profacil.model.Acervo;
import com.profacil.model.Curso;
import com.profacil.model.Disciplina;
import com.profacil.repository.Disciplinas;
import com.profacil.util.jpa.Transactional;

public class DisciplinaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Disciplinas disciplinas;

	@Transactional
	public void createNew(Disciplina disciplina) {
		disciplinas.persist(disciplina);
	}

	@Transactional
	public void update(Disciplina curso) {
		disciplinas.update(curso);
	}

	@Transactional
	public void delete(Disciplina disciplina) {
		disciplinas.delete(disciplina);
	}

	public List<Disciplina> filter(String descricao, Acervo acervo) {
		return disciplinas.filter(descricao, acervo);
	}

	public List<Disciplina> findAll() {
		return disciplinas.findAll();
	}

	@Transactional
	public Disciplina findById(Long id) {
		return disciplinas.findById(id);
	}

	@Transactional
	public List<Disciplina> findById(Disciplina disciplina) {
		return disciplinas.findById(disciplina);
	}

	@Transactional
	public List<Disciplina> findByCurso(Curso curso) {
		return disciplinas.findByCurso(curso);
	}

	@Transactional
	public List<Disciplina> findByAcervo(Acervo acervo) {
		return disciplinas.findByAcervo(acervo);
	}
}
