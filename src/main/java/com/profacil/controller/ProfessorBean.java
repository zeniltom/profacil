package com.profacil.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.el.ValueExpression;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.profacil.model.Preferencia;
import com.profacil.model.Professor;
import com.profacil.security.ProfessorSistema;
import com.profacil.service.PreferenciaService;
import com.profacil.service.ProfessorService;
import com.profacil.util.CriptografiaBCrypt;
import com.profacil.util.Util;
import com.profacil.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ProfessorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private ProfessorSistema professorSistema;

	@Inject
	private ProfessorService professorService;

	@Inject
	private PreferenciaService preferenciaService;

	private Professor professor;
	private Preferencia preferencia;

	public ProfessorBean() {
		limpar();
	}

	public void inicializar() throws IOException {
		if (this.professor == null) {

			limpar();
			Util.redirecionarObjeto("/Home.xhtml");

		} else {
			professorSistema = (ProfessorSistema) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (!(this.professor.getId().equals(this.professorSistema.getProfessor().getId())))
				Util.redirecionarObjeto(
						"/professor/Professor.xhtml?professor=" + professorSistema.getProfessor().getId().toString());

			if (professorSistema.getPreferencia() != null)
				this.preferencia = professorSistema.getPreferencia();
			else
				this.preferencia = new Preferencia();
		}
	}

	public void limpar() {
		this.professor = new Professor();
		this.preferencia = new Preferencia();
	}

	public void salvar() throws IOException {

		String senhaCriptografada = "";

		if (this.professor.getId() == null) {

			senhaCriptografada = this.professor.getSenha();
			this.professor.setSenha(CriptografiaBCrypt.criptografar(senhaCriptografada));

			professorService.createNew(this.professor);

			preferencia.setProfessor(this.professor);

			preferenciaService.createNew(this.preferencia);

			FacesUtil.addInfoMessage("Professor salvo com sucesso!");
		} else {
			
			senhaCriptografada = this.professor.getSenha();
			this.professor.setSenha(CriptografiaBCrypt.criptografar(senhaCriptografada));
			professorService.update(this.professor);

			if (preferencia.getId() != null) {
				preferencia.setProfessor(this.professor);
				preferenciaService.update(this.preferencia);

			} else {

				preferencia.setProfessor(this.professor);
				preferenciaService.createNew(this.preferencia);
			}

			FacesUtil.addInfoMessage("Professor atualizado com sucesso!");

			Util.redirecionarObjeto("/professor/Professor.xhtml?professor=" + this.professor.getId().toString());
		}

		atualizarSessaoProfessor();

		limpar();
	}

	private void atualizarSessaoProfessor() {
		// Atualiza o objeto Professor na Session
		FacesContext context = FacesContext.getCurrentInstance();
		ValueExpression ve = context.getApplication().getExpressionFactory()
				.createValueExpression(context.getELContext(), "#{professor}", ProfessorBean.class);

		// Atribui o novo valor 'Professor' para a sessão
		ve.setValue(context.getELContext(), this.professor);

		professorSistema.setProfessor(this.professor);

		// Recupera a sessao do usuário logado do Spring Security e depois atualiza
		Authentication authentication = new UsernamePasswordAuthenticationToken(professorSistema,
				professorSistema.getPassword(), professorSistema.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor Professor) {
		this.professor = Professor;
	}

	public Preferencia getPreferencia() {
		return preferencia;
	}

	public void setPreferencia(Preferencia preferencia) {
		this.preferencia = preferencia;
	}

	public boolean isEditando() {
		return this.professor.getId() != null;
	}
}
