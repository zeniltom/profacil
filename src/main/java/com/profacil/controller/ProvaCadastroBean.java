package com.profacil.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.profacil.model.Acervo;
import com.profacil.model.Curso;
import com.profacil.model.Disciplina;
import com.profacil.model.ENTurnoEscolar;
import com.profacil.model.Prova;
import com.profacil.model.Questao;
import com.profacil.model.Resposta;
import com.profacil.security.ProfessorSistema;
import com.profacil.service.AcervoService;
import com.profacil.service.CursoService;
import com.profacil.service.DisciplinaService;
import com.profacil.service.ProvaService;
import com.profacil.service.QuestaoService;
import com.profacil.service.RelatorioService;
import com.profacil.service.RespostaService;
import com.profacil.util.Util;
import com.profacil.util.jsf.FacesUtil;

import net.sf.jasperreports.engine.JRException;

@Named
@ViewScoped
public class ProvaCadastroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletResponse response;

	@Inject
	@PersistenceContext(unitName = "ProfacilPU")
	private EntityManager manager;

	private ProfessorSistema professorSistema;

	@Inject
	private AcervoService acervoService;

	@Inject
	private ProvaService provaService;

	@Inject
	private CursoService cursoService;

	@Inject
	private DisciplinaService disciplinaService;

	@Inject
	private QuestaoService questaoService;

	@Inject
	private RespostaService respostaService;

	@Inject
	private RelatorioService relatorioService;

	private Acervo acervo;
	private Prova prova;
	private Questao questao;

	private static final String ALFABETO[] = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "l", "m", "n", "o",
			"p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };

	private List<Curso> cursos;
	private List<Disciplina> disciplinas;
	private List<Questao> questoes = new ArrayList<>();
	private List<Questao> questoesSelecionadas = new ArrayList<>();
	private List<Resposta> respostas = new ArrayList<>();

	public ProvaCadastroBean() throws IOException {
		limpar();
	}

	public void inicializar() throws IOException {
		professorSistema = (ProfessorSistema) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		acervo = acervoService.findByProfessor(professorSistema.getProfessor());

		if (this.prova == null)
			limpar();
		else
			carregarDisciplinas();

		if (FacesUtil.isNotPostback())
			cursos = cursoService.findByAcervo(acervo);

		if (acervo == null) {
			Acervo a = new Acervo();
			a.setNomeAcervo(professorSistema.getProfessor().getNome());
			a.setProfessor(professorSistema.getProfessor());
			acervoService.createNew(a);

			this.prova.setAcervo(a);
		}

		this.prova.setAcervo(acervo);
	}

	public void carregarDisciplinas() {
		if (this.prova.getDisciplina().getCurso() != null && this.prova.getDisciplina().getCurso().getId() != null) {
			disciplinas = disciplinaService.findByCurso(this.prova.getDisciplina().getCurso());
			carregarQuestoes();
		} else
			disciplinas = new ArrayList<>();
	}

	public void carregarQuestoes() {
		questoes = (this.prova.getDisciplina() != null && this.prova.getDisciplina().getId() != null)
				? questaoService.findByIdDisciplina(this.prova.getDisciplina())
				: new ArrayList<>();
	}

	public void carregarRespostas(Questao questaoDetalhe) {
		respostas = (questaoDetalhe != null) ? respostaService.findByIdQuestao(questaoDetalhe) : new ArrayList<>();
	}

	private void limpar() throws IOException {
		prova = new Prova();
		prova.setDisciplina(new Disciplina());
		prova.getDisciplina().setCurso(new Curso());
		questao = new Questao();
	}

	public void listar() throws IOException {
		Util.redirecionarObjeto("/prova/Pesquisa.xhtml");
	}

	public void salvar() throws IOException {
		if (this.prova.getId() == null) {
			provaService.createNew(this.prova);
			FacesUtil.addInfoMessage("Prova salva com sucesso!");
			listar();

		} else {
			provaService.update(this.prova);
			FacesUtil.addInfoMessage("Prova atualizada com sucesso!");
			listar();
		}

		limpar();
	}

	public void excluir() throws IOException {
		try {
			provaService.delete(this.prova);
			FacesUtil.addInfoMessage("Prova " + this.prova.getDescricao() + " excluída com sucesso!");
			listar();

		} catch (ConstraintViolationException e) {
			FacesUtil.addErrorMessage("Não foi possível excluir a prova de " + prova.getDescricao());
		}
	}

	public void gerarProva() throws IOException, JRException {
		if (this.questoesSelecionadas != null && this.questoesSelecionadas.size() > 0)
			relatorioService.gerarRelatorioProva(this.facesContext, this.response, this.manager, this.professorSistema,
					this.prova, this.questoesSelecionadas);
		else
			FacesUtil.addErrorMessage("Selecione pelo menos 1 questão para a prova!");
	}

	public String numeroParaLetra(int nLinha) {
		return ALFABETO[nLinha];
	}

	public ENTurnoEscolar[] getTurnos() {
		return ENTurnoEscolar.values();
	}

	public Prova getProva() {
		return prova;
	}

	public void setProva(Prova prova) {
		this.prova = prova;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public List<Questao> getQuestoes() {
		return questoes;
	}

	public void setQuestoes(List<Questao> questoes) {
		this.questoes = questoes;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public List<Questao> getQuestoesSelecionadas() {
		return questoesSelecionadas;
	}

	public void setQuestoesSelecionadas(List<Questao> questoesSelecionadas) {
		this.questoesSelecionadas = questoesSelecionadas;
	}

	public List<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
	}

	public boolean isPossueRespostas() {
		return this.respostas.size() > 0;
	}

	public boolean isEditando() {
		return this.prova.getId() != null;
	}
}
