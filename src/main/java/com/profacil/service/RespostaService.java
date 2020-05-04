package com.profacil.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.profacil.model.Questao;
import com.profacil.model.Resposta;
import com.profacil.repository.Respostas;
import com.profacil.util.jpa.Transactional;

public class RespostaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Respostas respostas;

	@Transactional
	public void createNew(Resposta resposta) {
		respostas.persist(resposta);
	}

	@Transactional
	public void update(Resposta resposta) {
		respostas.update(resposta);
	}

	@Transactional
	public void delete(Resposta resposta) {
		respostas.delete(resposta);
	}

	@Transactional
	public List<Resposta> filter(String descricao) {
		return respostas.filter(descricao);
	}

	@Transactional
	public List<Resposta> findByIdQuestao(Questao questao) {
		return respostas.findByIdQuestao(questao);
	}
}
