package com.profacil.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.profacil.model.Grupo;
import com.profacil.model.Preferencia;
import com.profacil.model.Professor;
import com.profacil.repository.Preferencias;
import com.profacil.repository.Professores;
import com.profacil.util.cdi.CDIServiceLocator;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Professores professores = CDIServiceLocator.getBean(Professores.class);
		Preferencias preferencias = CDIServiceLocator.getBean(Preferencias.class);
		Professor professor = professores.findByEmail(email);
		Preferencia preferencia = preferencias.findByProfessor(professor);

		ProfessorSistema user = null;

		if (professor != null)
			user = new ProfessorSistema(professor, preferencia, getGrupos(professor));
		else
			throw new UsernameNotFoundException("Usuário não encontrado.");

		return user;
	}

	private Collection<? extends GrantedAuthority> getGrupos(Professor professor) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (Grupo grupo : professor.getGrupos())
			authorities.add(new SimpleGrantedAuthority("ROLE_" + grupo.getNome().toUpperCase()));

		return authorities;
	}
}
