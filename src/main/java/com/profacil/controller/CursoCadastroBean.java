package com.profacil.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.profacil.model.Acervo;
import com.profacil.model.Curso;
import com.profacil.security.ProfessorSistema;
import com.profacil.service.AcervoService;
import com.profacil.service.CursoService;
import com.profacil.util.Util;
import com.profacil.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CursoCadastroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ProfessorSistema professorSistema;

	@Inject
	private AcervoService acervoService;

	@Inject
	private CursoService cursoService;

	private Curso curso;

	private List<Curso> cursos = new ArrayList<>();

	public CursoCadastroBean() {
		limpar();
	}

	public void inicializar() {
		professorSistema = (ProfessorSistema) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (this.curso == null)
			limpar();

		// SE NÃO POSSUI ACERVO, CRIA UM
		Acervo acervo = acervoService.findByProfessor(professorSistema.getProfessor());
		if (acervo == null) {
			Acervo novoAcervo = new Acervo();
			novoAcervo.setNomeAcervo(professorSistema.getProfessor().getNome());
			novoAcervo.setProfessor(professorSistema.getProfessor());
			acervoService.createNew(novoAcervo);

			this.curso.setAcervo(novoAcervo);
		}

		this.curso.setAcervo(acervo);
	}

	private void limpar() {
		curso = new Curso();
	}

	public void listar() throws IOException {
		Util.redirecionarObjeto("/curso/Pesquisa.xhtml");
	}

	public void salvar() throws IOException {
		if (this.curso.getId() == null) {
			cursoService.createNew(this.curso);
			FacesUtil.addInfoMessage("Curso salvo com sucesso!");
			listar();

		} else {
			cursoService.update(this.curso);
			FacesUtil.addInfoMessage("Curso atualizado com sucesso!");
			listar();
		}

		limpar();
	}

	public void excluir() throws IOException {
		try {
			cursoService.delete(this.curso);
			FacesUtil.addInfoMessage("Curso " + this.curso.getDescricao() + " excluído com sucesso!");
			listar();

		} catch (ConstraintViolationException e) {
			FacesUtil.addErrorMessage("Não foi possível excluir o curso de " + curso.getDescricao()
					+ " , pois possue Disciplinas cadastradas!");
		}
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso Curso) {
		this.curso = Curso;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public boolean isEditando() {
		return this.curso.getId() != null;
	}
}
