package com.profacil.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.profacil.model.Preferencia;
import com.profacil.model.Professor;
import com.profacil.repository.Preferencias;
import com.profacil.util.jpa.Transactional;

public class PreferenciaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Preferencias preferencias;

	@Transactional
	public void createNew(Preferencia preferencia) {
		preferencias.persist(preferencia);
	}

	@Transactional
	public void update(Preferencia preferencia) {
		preferencias.update(preferencia);
	}

	@Transactional
	public void delete(Preferencia preferencia) {
		preferencias.delete(preferencia);
	}

	@Transactional
	public Preferencia findByProfessor(Professor professor) {
		return preferencias.findByProfessor(professor);
	}
}
