package com.profacil.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.profacil.model.Preferencia;
import com.profacil.repository.Preferencias;
import com.profacil.security.ProfessorSistema;

@Named
@ApplicationScoped
public class TemasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	Preferencias preferencias;

	private Authentication authentication;
	private ProfessorSistema professorSistema;

	private List<String> temas;

	public TemasBean() {
		authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null)
			professorSistema = (ProfessorSistema) authentication.getPrincipal();
	}

	public List<String> getTemas() {
		if (temas == null) {
			temas = new ArrayList<String>();
			temas.add("afterdark");
			temas.add("afternoon");
			temas.add("afterwork");
			temas.add("aristo");
			temas.add("black-tie");
			temas.add("blitzer");
			temas.add("bluesky");
			temas.add("bootstrap");
			temas.add("casablanca");
			temas.add("cruze");
			temas.add("cupertino");
			temas.add("dark-hive");
			temas.add("delta");
			temas.add("dot-luv");
			temas.add("eggplant");
			temas.add("excite-bike");
			temas.add("flick");
			temas.add("glass-x");
			temas.add("home");
			temas.add("hot-sneaks");
			temas.add("humanity");
			temas.add("le-frog");
			temas.add("midnight");
			temas.add("mint-choc");
			temas.add("overcast");
			temas.add("pepper-grinder");
			temas.add("redmond");
			temas.add("rocket");
			temas.add("sam");
			temas.add("smoothness");
			temas.add("south-street");
			temas.add("start");
			temas.add("sunny");
			temas.add("swanky-purse");
			temas.add("trontastic");
			temas.add("ui-darkness");
			temas.add("ui-lightness");
			temas.add("vader");
		}

		temaUsuario();

		return temas;
	}

	private void temaUsuario() {
		if (professorSistema != null && professorSistema.getProfessor() != null) {
			Preferencia preferencia = preferencias.findByProfessor(professorSistema.getProfessor());

			if (preferencia != null) {
				for (int i = 0; i < temas.size(); i++) {
					if (temas.get(i).equals(preferencia.getTema()))
						temas.set(i, preferencia.getTema());
				}
			}
		}
	}

	public void setTemas(List<String> temas) {
		this.temas = temas;
	}
}
