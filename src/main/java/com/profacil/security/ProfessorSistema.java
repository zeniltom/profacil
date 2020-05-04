package com.profacil.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.profacil.model.Preferencia;
import com.profacil.model.Professor;

public class ProfessorSistema extends User {

	private static final long serialVersionUID = 1L;

	private Professor professor;
	private Preferencia preferencia;

	public ProfessorSistema(Professor professor, Preferencia preferencia,
			Collection<? extends GrantedAuthority> authorities) {
		super(professor.getEmail(), professor.getSenha(), authorities);
		this.professor = professor;
		this.preferencia = preferencia;
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
}
