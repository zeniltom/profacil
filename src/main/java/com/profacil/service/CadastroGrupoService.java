package com.profacil.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.profacil.model.Grupo;
import com.profacil.repository.Grupos;
import com.profacil.util.jpa.Transactional;

public class CadastroGrupoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Grupos grupos;

	@Transactional
	public void createNew(Grupo grupo) {
		grupos.persist(grupo);
	}

	@Transactional
	public List<Grupo> findByNome(Grupo grupoSelecionado) {
		return grupos.findByNome(grupoSelecionado);
	}

}
