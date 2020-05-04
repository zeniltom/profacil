package com.profacil.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

import com.profacil.model.Acervo;
import com.profacil.model.Prova;
import com.profacil.security.ProfessorSistema;
import com.profacil.service.ProvaService;
import com.profacil.service.AcervoService;
import com.profacil.service.NegocioException;
import com.profacil.util.Util;
import com.profacil.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ProvasPesquisaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ProfessorSistema professorSistema;

	@Inject
	private AcervoService acervoService;

	@Inject
	private ProvaService provaService;

	private Acervo acervo;
	private Prova prova;
	private Prova provaSelecionada;
	private String descricao;

	private List<Prova> provasFiltradas;

	public ProvasPesquisaBean() {
		prova = new Prova();
	}

	@PostConstruct
	public void inicializar() {
		professorSistema = (ProfessorSistema) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		acervo = acervoService.findByProfessor(professorSistema.getProfessor());
	}

	public void pesquisar() {
		provasFiltradas = provaService.filter(descricao, acervo);
	}

	public void excluir() {
		try {
			provaService.delete(provaSelecionada);
			FacesUtil.addInfoMessage("Prova de " + provaSelecionada.getDisciplina().getDescricao() + " excluído com sucesso!");

		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage(
					"Erro ao excluir a prova de " + provaSelecionada.getDisciplina().getDescricao() + "!");
		}
	}

	public void doubleClick(SelectEvent event) throws IOException {
		if (event != null) {
			Prova prova = (Prova) event.getObject();

			Util.redirecionarObjeto("/prova/Cadastro.xhtml?prova=" + prova.getId().toString());
		} else
			throw new NegocioException("Não foi possível verificar esta prova.");
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Prova getProva() {
		return prova;
	}

	public List<Prova> getProvasFiltradas() {
		return provasFiltradas;
	}

	public Prova getProvaSelecionada() {
		return provaSelecionada;
	}

	public void setProvaSelecionada(Prova provaSelecionada) {
		this.provaSelecionada = provaSelecionada;
	}

}
