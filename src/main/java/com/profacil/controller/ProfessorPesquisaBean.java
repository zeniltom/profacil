package com.profacil.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

import com.profacil.model.Professor;
import com.profacil.repository.ProfessorGrupos;
import com.profacil.service.ProfessorService;
import com.profacil.service.NegocioException;
import com.profacil.util.Util;
import com.profacil.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ProfessorPesquisaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProfessorService cadastroProfessorService;

	@Inject
	ProfessorGrupos professorGrupos;

	private Professor professor;

	private List<Professor> professoresFiltrados;

	private Professor professorSelecionado;

	public ProfessorPesquisaBean() {
		professor = new Professor();
	}

	public void excluir() {
		try {
			cadastroProfessorService.delete(professorSelecionado);
			FacesUtil.addInfoMessage("Professor " + professorSelecionado.getNome() + " excluído com sucesso!");

		} catch (ConstraintViolationException e) {
			FacesUtil.addErrorMessage("Não foi possível excluir o " + professorSelecionado.getNome() + "!");
		}
	}

	public void pesquisar() {
		professoresFiltrados = cadastroProfessorService.filter(professor);
	}

	public void doubleClick(SelectEvent event) throws IOException {
		if (event != null) {
			Professor professor = (Professor) event.getObject();

			Util.redirecionarObjeto("/usuario/CadastroUsuario.xhtml?professor=" + professor.getId().toString());
		} else
			throw new NegocioException("Não foi possível verificar este usuário.");
	}

	public Professor getProfessor() {
		return professor;
	}

	public List<Professor> getProfessorsFiltrados() {
		return professoresFiltrados;
	}

	public Professor getProfessorSelecionado() {
		return professorSelecionado;
	}

	public void setProfessorSelecionado(Professor ProfessorSelecionado) {
		this.professorSelecionado = ProfessorSelecionado;
	}

}
