package com.profacil.security;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.profacil.util.Util;

@Named
@RequestScoped
public class Seguranca {

	@Inject
	private ExternalContext externalContext;

	public String getNomeProfessor() {
		String nome = null;

		ProfessorSistema professorLogado = getProfessorLogado();

		if (professorLogado != null)
			nome = professorLogado.getProfessor().getNome();

		return nome;
	}

	@Produces
	@ProfessorLogado
	public ProfessorSistema getProfessorLogado() {
		ProfessorSistema professor = null;

		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken) FacesContext
				.getCurrentInstance().getExternalContext().getUserPrincipal();

		if (auth != null && auth.getPrincipal() != null)
			professor = (ProfessorSistema) auth.getPrincipal();

		return professor;
	}

	public void editarProfessor() throws IOException {
		if (getProfessorLogado() != null)
			Util.redirecionarObjeto(
					"/professor/Professor.xhtml?professor=" + getProfessorLogado().getProfessor().getId().toString());
	}

	// INÍCIO PERMISSÃO PARA COMPONENTES MENUITEM DO MENUBAR

	public boolean isComponenteProfessorPermitido() {
		return externalContext.isUserInRole("ADMINISTRADORES");
	}

	// FIM PERMISSÃO PARA COMPONENTES MENUITEM DO MENUBAR

	// INÍCIO PERMISSÃO PARA COMPONENTES COMMANDBUTTON DO MENUBAR

	// FIM PERMISSÃO PARA COMPONENTES COMMANDBUTTON DO MENUBAR
}
