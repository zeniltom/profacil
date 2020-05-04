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
import com.profacil.model.Curso;
import com.profacil.security.ProfessorSistema;
import com.profacil.service.AcervoService;
import com.profacil.service.CursoService;
import com.profacil.service.NegocioException;
import com.profacil.util.Util;
import com.profacil.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CursosPesquisaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ProfessorSistema professorSistema;

	@Inject
	private AcervoService acervoService;

	@Inject
	private CursoService cursoService;

	private Curso curso;

	private List<Curso> cursosFiltrados;

	private Acervo acervo;
	private Curso cursoSelecionado;
	private String descricao;

	public CursosPesquisaBean() {
		curso = new Curso();
	}

	@PostConstruct
	public void inicializar() {
		professorSistema = (ProfessorSistema) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		acervo = acervoService.findByProfessor(professorSistema.getProfessor());
	}

	public void pesquisar() {
		cursosFiltrados = cursoService.filter(descricao, acervo);
	}

	public void excluir() {
		try {
			cursoService.delete(cursoSelecionado);
			FacesUtil.addInfoMessage("Curso " + cursoSelecionado.getDescricao() + " excluído com sucesso!");

		} catch (ConstraintViolationException e) {
			FacesUtil.addErrorMessage("Não foi possível excluir o curso de " + cursoSelecionado.getDescricao()
					+ " , pois possue Disciplinas cadastradas!");
		}
	}

	public void doubleClick(SelectEvent event) throws IOException {

		if (event != null) {
			Curso curso = (Curso) event.getObject();

			Util.redirecionarObjeto("/curso/Cadastro.xhtml?curso=" + curso.getId().toString());
		} else
			throw new NegocioException("Não foi possível verificar esta curso.");
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Curso getCurso() {
		return curso;
	}

	public List<Curso> getCursosFiltrados() {
		return cursosFiltrados;
	}

	public Curso getCursoSelecionado() {
		return cursoSelecionado;
	}

	public void setCursoSelecionado(Curso cursoSelecionado) {
		this.cursoSelecionado = cursoSelecionado;
	}

}
