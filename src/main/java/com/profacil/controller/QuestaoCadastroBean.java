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
import com.profacil.model.Disciplina;
import com.profacil.model.ENTipoQuestao;
import com.profacil.model.Questao;
import com.profacil.model.Resposta;
import com.profacil.security.ProfessorSistema;
import com.profacil.service.AcervoService;
import com.profacil.service.DisciplinaService;
import com.profacil.service.NegocioException;
import com.profacil.service.QuestaoService;
import com.profacil.service.RespostaService;
import com.profacil.util.Util;
import com.profacil.util.jsf.FacesUtil;

@Named
@ViewScoped
public class QuestaoCadastroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ProfessorSistema professorSistema;

	@Inject
	private AcervoService acervoService;

	@Inject
	private DisciplinaService disciplinaService;

	@Inject
	private QuestaoService questaoService;

	@Inject
	private RespostaService respostaService;

	private Acervo acervo;
	private Questao questao;
	private Resposta resposta;

	private List<Disciplina> disciplinas = new ArrayList<>();
	private List<Questao> questoes = new ArrayList<>();
	private List<Resposta> respostas = new ArrayList<>();

	public QuestaoCadastroBean() {
		limpar();
	}

	public void inicializar() throws IOException {
		professorSistema = (ProfessorSistema) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		acervo = acervoService.findByProfessor(professorSistema.getProfessor());

		if (this.questao == null)
			limpar();
		else
			carregarRespostas();

		if (FacesUtil.isNotPostback()) {
			carregarRespostas();
		}

		disciplinas = disciplinaService.findByAcervo(acervo);
	}

	private void limpar() {
		questao = new Questao();
		questao.setDisciplina(new Disciplina());
		resposta = new Resposta();
	}

	private void limparRespostas() {
		respostas.clear();
		carregarRespostas();
	}

	public void carregarRespostas() {
		respostas = this.questao.getId() != null ? respostaService.findByIdQuestao(this.questao) : new ArrayList<>();
	}

	public void listar() throws IOException {
		Util.redirecionarObjeto("/disciplina/Pesquisa.xhtml");
	}

	public void salvar() throws IOException {
		if (this.questao != null) {
			if (this.questao.getId() == null) {
				questaoService.createNew(this.questao);
				FacesUtil.addInfoMessage("Questão salva com sucesso!");
				listar();

			} else {
				// TODO CONTROLAR SE SALVAR UMA PERGUNTA COM ALTERNATIVAS COMO PERGUNTA ABERTA,
				// EXCLUIR AS ALTERNATIVAS NO BANCO
				questaoService.update(this.questao);
				FacesUtil.addInfoMessage("Questão atualizada com sucesso!");

				Util.redirecionarObjeto(
						"/disciplina/Cadastro.xhtml?disciplina=" + this.questao.getDisciplina().getId());
			}
		} else
			throw new NegocioException("Erro ao salvar a questão!");

		limpar();
	}

	public void excluir() throws IOException {
		try {
			questaoService.delete(this.questao);
			FacesUtil.addInfoMessage("Questão " + this.questao.getDescricao() + " excluída com sucesso!");
			listar();

		} catch (ConstraintViolationException e) {
			FacesUtil.addErrorMessage("Não foi possível excluir a questão de " + questao.getDescricao());
		}
	}

	public void novaResposta() {
		resposta = new Resposta();
		resposta.setDescricao("Sem descrição");
		resposta.setQuestao(this.questao);
	}

	public void adicionarResposta() {
		if (this.resposta.getId() == null) {
			respostaService.createNew(this.resposta);
			FacesUtil.addInfoMessage("Resposta adicionada!");
		} else {
			respostaService.update(this.resposta);
			FacesUtil.addInfoMessage("Resposta atualizada com sucesso!");
		}
	}

	public void excluirResposta() {
		try {
			respostaService.delete(this.resposta);
			FacesUtil.addInfoMessage("Resposta " + this.resposta.getDescricao() + " deletada com sucesso!");

			limparRespostas();

		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("Erro ao excluir a " + this.resposta.getDescricao() + "!");
		}
	}

	public void carregarTipoQuestao() {
		FacesUtil.addInfoMessage("Questão de " + this.questao.getTipoQuestao() + " selecionada!");
	}

	public ENTipoQuestao[] getTipoQuestoes() {
		return ENTipoQuestao.values();
	}

	public List<Questao> getQuestoes() {
		return questoes;
	}

	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
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

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(Questao questao) {
		this.questao = questao;
	}

	public Resposta getResposta() {
		return resposta;
	}

	public void setResposta(Resposta resposta) {
		this.resposta = resposta;
	}

	public boolean isEditando() {
		return this.questao.getId() != null;
	}

	public boolean isEditandoResposta() {
		return this.resposta.getId() != null;
	}
}
