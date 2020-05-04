package com.profacil.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.profacil.model.Acervo;
import com.profacil.model.Prova;
import com.profacil.repository.Provas;
import com.profacil.util.jpa.Transactional;

public class ProvaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Provas provas;

	@Transactional
	public void createNew(Prova prova) {
		provas.persist(prova);
	}

	@Transactional
	public void update(Prova prova) {
		provas.update(prova);
	}

	@Transactional
	public void delete(Prova prova) {
		provas.delete(prova);
	}

	@Transactional
	public List<Prova> findAll() {
		return provas.findAll();
	}

	@Transactional
	public List<Prova> filter(String prova, Acervo acervo) {
		return provas.filter(prova, acervo);
	}
}
