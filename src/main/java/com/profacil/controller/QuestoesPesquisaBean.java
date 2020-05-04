package com.profacil.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

import com.profacil.model.Questao;
import com.profacil.service.QuestaoService;
import com.profacil.service.NegocioException;
import com.profacil.util.Util;
import com.profacil.util.jsf.FacesUtil;

@Named
@ViewScoped
public class QuestoesPesquisaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private QuestaoService questaoService;

	private Questao questao;

	private List<Questao> questoesFiltradas;

	private Questao QuestaoSelecionada;

	private String descricao;

	public QuestoesPesquisaBean() {
		questao = new Questao();
	}

	public void pesquisar() {
		questoesFiltradas = questaoService.filter(descricao);
	}

	public void excluir() {
		try {
			questaoService.delete(QuestaoSelecionada);
			FacesUtil.addInfoMessage("Questão " + QuestaoSelecionada.getDescricao() + " excluída com sucesso!");

		} catch (ConstraintViolationException e) {
			FacesUtil.addErrorMessage("Não foi possível excluir a questão, pois possue Respostas cadastradas!");
		}
	}

	public void doubleClick(SelectEvent event) throws IOException {
		if (event != null) {
			Questao questao = (Questao) event.getObject();

			Util.redirecionarObjeto("/questao/Cadastro.xhtml?questao=" + questao.getId().toString());
		} else
			throw new NegocioException("Não foi possível verificar esta Questão.");
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Questao getQuestao() {
		return questao;
	}

	public List<Questao> getQuestoesFiltradas() {
		return questoesFiltradas;
	}

	public Questao getQuestaoSelecionada() {
		return QuestaoSelecionada;
	}

	public void setQuestaoSelecionada(Questao questaoSelecionada) {
		this.QuestaoSelecionada = questaoSelecionada;
	}
}
