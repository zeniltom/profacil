package com.profacil.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import com.profacil.model.Grupo;
import com.profacil.model.Permissao;
import com.profacil.repository.Permissoes;
import com.profacil.util.jpa.Transactional;

public class PermissaoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Permissoes permissoes;

	@Transactional
	public void createNew(Permissao Permissao) {
		permissoes.persist(Permissao);
	}

	@Transactional
	public void update(Permissao Permissao) {
		permissoes.update(Permissao);
	}

	@Transactional
	public void delete(Permissao Permissao) {
		permissoes.delete(Permissao);
	}

	@Transactional
	public List<Permissao> findAll() {
		return permissoes.findAll();
	}

	@Transactional
	public List<Permissao> findComponenteByGrupo(Grupo grupo) {
		return permissoes.findComponenteByGrupo(grupo);
	}
}
