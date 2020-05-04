package com.profacil.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.exception.ConstraintViolationException;

import com.profacil.model.Grupo;
import com.profacil.model.Professor;
import com.profacil.model.ProfessorGrupo;
import com.profacil.repository.Grupos;
import com.profacil.repository.ProfessorGrupos;
import com.profacil.service.CadastroGrupoService;
import com.profacil.service.CadastroProfessorGrupoService;
import com.profacil.service.NegocioException;
import com.profacil.service.ProfessorService;
import com.profacil.util.CriptografiaBCrypt;
import com.profacil.util.Util;
import com.profacil.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ProfessorCadastroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Grupos grupos;

	@Inject
	private ProfessorGrupos professorGrupos;

	@Inject
	private ProfessorService cadastroProfessorService;

	@Inject
	private CadastroGrupoService cadastroGrupoService;

	@Inject
	private CadastroProfessorGrupoService cadastroProfessorGrupoService;;

	private Professor professor;
	private ProfessorGrupo professorGrupo;

	private Grupo grupoSelecionado;

	private List<Grupo> listaGrupos = new ArrayList<>();
	private List<Grupo> gruposProfessor = new ArrayList<>();

	public ProfessorCadastroBean() {
		limpar();
	}

	public void inicializar() {
		if (this.professor == null)
			limpar();

		if (FacesUtil.isNotPostback())
			carregarPermissoes();
	}

	public void carregarPermissoes() {

		if (professor.getId() != null) {

			List<ProfessorGrupo> permissoes = professorGrupos.findByProfessor(professor);

			for (ProfessorGrupo permissao : permissoes)
				gruposProfessor.add(permissao.getGrupo());

			listaGrupos = grupos.findAll();
			listaGrupos.removeAll(gruposProfessor);

		} else
			listaGrupos = grupos.findAll();
	}

	private void limpar() {
		professor = new Professor();
		professorGrupo = new ProfessorGrupo();
	}

	public void listar() throws IOException {
		Util.redirecionarObjeto("/usuario/PesquisaUsuarios.xhtml");
	}

	private void limparPermissoes() {
		gruposProfessor.clear();
		professorGrupo = new ProfessorGrupo();
		carregarPermissoes();
	}

	public void adicionarPermissao() {

		if (this.professor.getId() != null && professorGrupo.getGrupo() != null) {

			professorGrupo.setProfessor(professor);
			cadastroProfessorGrupoService.createNew(professorGrupo);

			FacesUtil
					.addInfoMessage("Permissão de " + professorGrupo.getGrupo().getNome() + " adicionada com sucesso!");
		} else
			throw new NegocioException("Salve o formulário de Professor antes de adicionar uma permissão!");

		limparPermissoes();
	}

	public void excluirPermissao() {

		try {
			professorGrupo.setProfessor(professor);
			professorGrupo.setGrupo(cadastroGrupoService.findByNome(grupoSelecionado).get(0));
			cadastroProfessorGrupoService.delete(professorGrupo);

			FacesUtil.addInfoMessage("Permissão de " + grupoSelecionado.getNome() + " removida com sucesso!");

			limparPermissoes();

		} catch (Exception e) {
			e.printStackTrace();
			FacesUtil.addErrorMessage("Erro ao excluir o " + grupoSelecionado.getNome() + "!");
		}
	}

	public void salvar() throws IOException {

		String senhaCriptografada = "";

		if (this.professor.getId() == null) {

			senhaCriptografada = this.professor.getSenha();
			this.professor.setSenha(CriptografiaBCrypt.criptografar(senhaCriptografada));

			cadastroProfessorService.createNew(this.professor);
			FacesUtil.addInfoMessage("Professor salvo com sucesso!");
			listar();

		} else {

			senhaCriptografada = this.professor.getSenha();
			this.professor.setSenha(CriptografiaBCrypt.criptografar(senhaCriptografada));

			cadastroProfessorService.update(this.professor);
			FacesUtil.addInfoMessage("Professor atualizado com sucesso!");
			listar();
		}

		limpar();
	}

	public void excluir() {
		try {
			cadastroProfessorService.delete(this.professor);
			FacesUtil.addInfoMessage("Professor " + this.professor.getNome() + " excluído com sucesso!");

		} catch (ConstraintViolationException e) {
			FacesUtil.addErrorMessage("Não foi possível excluir o " + this.professor.getNome() + "!");
		}
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor Professor) {
		this.professor = Professor;
	}

	public ProfessorGrupo getProfessorGrupo() {
		return professorGrupo;
	}

	public void setProfessorGrupo(ProfessorGrupo professorGrupo) {
		this.professorGrupo = professorGrupo;
	}

	public Grupo getGrupoSelecionado() {
		return grupoSelecionado;
	}

	public void setGrupoSelecionado(Grupo grupoSelecionado) {
		this.grupoSelecionado = grupoSelecionado;
	}

	public List<Grupo> getListaGrupos() {
		return listaGrupos;
	}

	public void setListaGrupos(List<Grupo> listaGrupos) {
		this.listaGrupos = listaGrupos;
	}

	public List<Grupo> getGruposProfessor() {
		return gruposProfessor;
	}

	public boolean isEditando() {
		return this.professor.getId() != null;
	}
}
