package com.profacil.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

import com.profacil.model.Acervo;
import com.profacil.model.Curso;
import com.profacil.model.Disciplina;
import com.profacil.model.ENTipoQuestao;
import com.profacil.model.Questao;
import com.profacil.model.Resposta;
import com.profacil.security.ProfessorSistema;
import com.profacil.service.AcervoService;
import com.profacil.service.CursoService;
import com.profacil.service.DisciplinaService;
import com.profacil.service.NegocioException;
import com.profacil.service.QuestaoService;
import com.profacil.service.RespostaService;
import com.profacil.util.Util;
import com.profacil.util.jsf.FacesUtil;

@Named
@ViewScoped
public class DisciplinaCadastroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ProfessorSistema professorSistema;

	@Inject
	private AcervoService acervoService;

	@Inject
	private CursoService cursoService;

	@Inject
	private DisciplinaService disciplinaService;

	@Inject
	private QuestaoService questaoService;

	@Inject
	private RespostaService respostaService;

	private Acervo acervo;
	private Curso curso;
	private Disciplina disciplina;
	private Questao questao;

	private List<Curso> cursosRaizes;
	private List<Questao> questoes = new ArrayList<>();
	private List<Resposta> respostas = new ArrayList<>();

	public DisciplinaCadastroBean() {
		limpar();
	}

	public void inicializar() throws IOException {

		professorSistema = (ProfessorSistema) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		acervo = acervoService.findByProfessor(professorSistema.getProfessor());

		if (this.disciplina == null)
			limpar();
		else {
			carregarQuestoes();
			carregarRespostas();
		}

		if (FacesUtil.isNotPostback()) {
			cursosRaizes = cursoService.findByAcervo(acervo);
			carregarQuestoes();
			carregarRespostas();
		}
	}

	public void doubleClickQuestao(SelectEvent event) throws IOException {
		if (event != null) {
			Questao questao = (Questao) event.getObject();

			Util.redirecionarObjeto("/questao/Cadastro.xhtml?questao=" + questao.getId().toString());
		} else
			throw new NegocioException("Não foi possível verificar esta questao.");
	}

	private void limpar() {
		curso = null;
		disciplina = new Disciplina();
		questao = new Questao();
	}

	public void listar() throws IOException {
		Util.redirecionarObjeto("/disciplina/Pesquisa.xhtml");
	}

	private void limparQuestoes() {
		questao = new Questao();
		questoes.clear();
		carregarQuestoes();
	}

	public void salvar() throws IOException {
		if (this.curso != null) {

			this.disciplina.setCurso(this.curso);

			if (this.disciplina.getId() == null) {
				disciplinaService.createNew(this.disciplina);
				FacesUtil.addInfoMessage("Disciplina salva com sucesso!");
				listar();

			} else {
				disciplinaService.update(this.disciplina);
				FacesUtil.addInfoMessage("Disciplina atualizada com sucesso!");
				listar();
			}
		} else {
			throw new NegocioException("Escolha um curso!");
		}

		limpar();
	}

	public void excluir() throws IOException {
		try {
			disciplinaService.delete(this.disciplina);
			FacesUtil.addInfoMessage("Disciplina " + this.disciplina.getDescricao() + " excluída com sucesso!");
 			listar();

		} catch (ConstraintViolationException e) {
			FacesUtil.addErrorMessage("Não foi possível excluir a disciplina de " + disciplina.getDescricao()
					+ " , pois possue Questões cadastradas!");
		}
	}

	public void carregarQuestoes() {
		questoes = this.disciplina.getId() != null ? questoes = questaoService.findByIdDisciplina(this.disciplina)
				: new ArrayList<>();
	}

	public void carregarRespostas() {
		respostas = this.questao.getId() != null ? respostaService.findByIdQuestao(this.questao) : new ArrayList<>();
	}

	public void novaResposta() {
		try {
			Resposta resposta = new Resposta();
			resposta.setDescricao("Nova questão");
			resposta.setQuestao(this.questao);

			respostaService.createNew(resposta);
			FacesUtil.addInfoMessage("Resposta adicionada!");
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("Erro ao adicionar resposta!");
		}
	}

	public void novaQuestao() {
		questao = new Questao();
		questao.setDisciplina(this.disciplina);
	}

	public void adicionarQuestao() {
		if (this.questao.getId() == null) {
			questoes.add(this.questao);
			questaoService.createNew(this.questao);
			FacesUtil.addInfoMessage("Questão adicionada com sucesso!");
		} else {
			questaoService.update(this.questao);
			FacesUtil.addInfoMessage("Questão editada com sucesso!");
		}

		limparQuestoes();
	}

	public void excluirQuestao() {
		try {
			questaoService.delete(this.questao);
			FacesUtil.addInfoMessage("Questão removida com sucesso!");

			limparQuestoes();
		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("Erro ao excluir questão!");
		}
	}

	public void carregarTipoQuestao() {
		FacesUtil.addInfoMessage("Tipo Questão " + this.questao.getTipoQuestao() + " selecionada!");
		System.out.println("Tipo Questão " + this.questao.getTipoQuestao() + " selecionada!");
	}

	public ENTipoQuestao[] getTipoQuestoes() {
		return ENTipoQuestao.values();
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;

		if (this.disciplina != null)
			this.curso = this.disciplina.getCurso();
	}

	public List<Curso> getCursosRaizes() {
		return cursosRaizes;
	}

	public List<Questao> getQuestoes() {
		return questoes;
	}

	public void setQuestoes(List<Questao> questoes) {
		this.questoes = questoes;
	}

	public List<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
	}

	@NotNull
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public boolean isEditando() {
		return this.disciplina.getId() != null;
	}

	public boolean isEditandoQuestao() {
		return this.questao.getId() != null;
	}
}
