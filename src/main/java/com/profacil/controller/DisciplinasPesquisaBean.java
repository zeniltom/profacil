package com.profacil.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

import com.profacil.model.Acervo;
import com.profacil.model.Disciplina;
import com.profacil.security.ProfessorSistema;
import com.profacil.service.AcervoService;
import com.profacil.service.DisciplinaService;
import com.profacil.service.NegocioException;
import com.profacil.util.Util;
import com.profacil.util.jsf.FacesUtil;

@Named
@ViewScoped
public class DisciplinasPesquisaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ProfessorSistema professorSistema;

	@Inject
	private AcervoService acervoService;

	@Inject
	private DisciplinaService disciplinaService;

	private Disciplina disciplina;

	private List<Disciplina> disciplinasFiltradas;

	private Acervo acervo;
	private Disciplina disciplinaSelecionada;

	private String descricao;

	public DisciplinasPesquisaBean() {
		disciplina = new Disciplina();
	}

	@PostConstruct
	public void inicializar() {
		professorSistema = (ProfessorSistema) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		acervo = acervoService.findByProfessor(professorSistema.getProfessor());
	}

	public void pesquisar() {
		disciplinasFiltradas = disciplinaService.filter(descricao, acervo);
	}

	public void excluir() {
 		try {
			disciplinaService.delete(disciplinaSelecionada);
			FacesUtil.addInfoMessage("Disciplina " + disciplinaSelecionada.getDescricao() + " excluída com sucesso!");

		} catch (ConstraintViolationException e) {
			FacesUtil.addErrorMessage("Não foi possível excluir a disciplina de " + disciplinaSelecionada.getDescricao()
					+ " , pois possue Questões cadastradas!");
		}
	}

	public void doubleClick(SelectEvent event) throws IOException {
		if (event != null) {
			Disciplina Disciplina = (Disciplina) event.getObject();

			Util.redirecionarObjeto("/disciplina/Cadastro.xhtml?disciplina=" + Disciplina.getId().toString());
		} else
			throw new NegocioException("Não foi possível verificar esta disciplina.");
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public List<Disciplina> getDisciplinasFiltradas() {
		return disciplinasFiltradas;
	}

	public Disciplina getDisciplinaSelecionada() {
		return disciplinaSelecionada;
	}

	public void setDisciplinaSelecionada(Disciplina disciplinaSelecionada) {
		this.disciplinaSelecionada = disciplinaSelecionada;
	}
}
