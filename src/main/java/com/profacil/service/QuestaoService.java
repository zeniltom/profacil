package com.profacil.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.profacil.model.Disciplina;
import com.profacil.model.Questao;
import com.profacil.repository.Questoes;
import com.profacil.util.jpa.Transactional;

public class QuestaoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Questoes questoes;

	@Transactional
	public void createNew(Questao questao) {
		questoes.persist(questao);
	}

	@Transactional
	public void update(Questao questao) {
		questoes.update(questao);
	}

	@Transactional
	public void delete(Questao questao) {
		questoes.delete(questao);
	}

	@Transactional
	public List<Questao> filter(String descricao) {
		return questoes.filter(descricao);
	}

	@Transactional
	public List<Questao> findByIdDisciplina(Disciplina disciplina) {
		return questoes.findByIdDisciplina(disciplina);
	}
}
