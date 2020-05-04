package com.profacil.controller;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.profacil.repository.Preferencias;
import com.profacil.security.ProfessorSistema;

@Named
@SessionScoped
public class PreferenciasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	Preferencias preferencias;

	private ProfessorSistema professorSistema;

	private Authentication authentication;

	private String tema;

	public void carregarUsuario() {
		authentication = SecurityContextHolder.getContext().getAuthentication();

		if (!authentication.getPrincipal().equals("anonymousUser"))
			professorSistema = (ProfessorSistema) authentication.getPrincipal();
	}

	public String getTema() {
		carregarUsuario();

		if (professorSistema != null && professorSistema.getProfessor() != null
				&& professorSistema.getPreferencia() != null)
			tema = professorSistema.getPreferencia().getTema();
		else
			tema = "bootstrap";

		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}
}
