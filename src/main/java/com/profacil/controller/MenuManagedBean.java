
package com.profacil.controller;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.menubar.Menubar;
import org.primefaces.component.menuitem.UIMenuItem;
import org.primefaces.component.submenu.UISubmenu;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;
import org.springframework.security.core.GrantedAuthority;

import com.profacil.model.Componente;
import com.profacil.model.Grupo;
import com.profacil.model.Permissao;
import com.profacil.model.ProfessorGrupo;
import com.profacil.security.ProfessorLogado;
import com.profacil.security.ProfessorSistema;
import com.profacil.service.CadastroProfessorGrupoService;
import com.profacil.service.PermissaoService;

@Named
@RequestScoped
public class MenuManagedBean {

	private MenuModel menubar = new DefaultMenuModel();
	private Menubar menuBarTela;

	@Inject
	private PermissaoService permissaoService;

	@Inject
	private CadastroProfessorGrupoService cadastroProfessorGrupoService;

	@Inject
	@ProfessorLogado
	private ProfessorSistema professorLogado;

	private List<Permissao> permissoes = new ArrayList<>();
	private List<ProfessorGrupo> professorGrupos = new ArrayList<>();
	private List<Componente> componentes = new ArrayList<>();

	public void inicializar() {

		List<Grupo> grupos = professorLogado.getProfessor().getGrupos();

		System.out.println("\n=== GRUPOS ====");
		for (Grupo g : grupos) {
			System.out.println(g.toString());
		}

		permissoes = permissaoService.findComponenteByGrupo(grupos.get(0));

		professorGrupos = cadastroProfessorGrupoService.findAll();

		System.out.println("\n=== COMPONENTES ====");
		for (Componente c : componentes) {
			System.out.println(c.toString());
		}

		System.out.println("\n=== PROFESSORESGRUPOS ====");
		for (ProfessorGrupo ug : professorGrupos) {
			System.out.println(ug.toString());
		}

		System.out.println("\n=== PERMISSÕES ====");
		for (Permissao p : permissoes) {
			System.out.println(p.toString());

			componentes.add(p.getComponente());
		}

		for (Componente comp : componentes) {
			DefaultMenuItem element = new DefaultMenuItem(comp.getNome(), comp.getIcon(), comp.getUrl());
			this.menubar.addElement(element);
		}

		List<GrantedAuthority> authorities = new ArrayList<>();

		for (GrantedAuthority grupo : professorLogado.getAuthorities()) {
			authorities.add(grupo);
		}

		System.out.println("\n=== AUTORITIES ====");
		for (GrantedAuthority ga : authorities) {
			System.out.println(ga.toString());
		}

		@SuppressWarnings("unchecked")
		List<UIMenuItem> lista = menuBarTela.getElements();
		System.out.println("\n=== MENUS DO FRONT-END ====");

		for (Object componente : lista) {

			if (isMenuItem(componente)) {
				UIMenuItem menuItem = (UIMenuItem) componente;
				System.out.println(menuItem.getValue());
			} else {
				UISubmenu subMenu = (UISubmenu) componente;
				System.out.println(subMenu.getLabel());
			}
		}

		System.out.println("\n=== MENUS DO FRONT-END PERMITIDOS ====");
		for (Permissao permissao : permissoes) {
			for (Object componente : lista) {

				if (isMenuItem(componente)) {

					UIMenuItem menuItem = (UIMenuItem) componente;
					if (permissao.getComponente().getIdComponente().equals(menuItem.getId())) {
						menuItem.setRendered(true);
						System.out.println(menuItem.getValue() + " [OK]");
					}

				} else {

					UISubmenu subMenu = (UISubmenu) componente;
					if (permissao.getComponente().getIdComponente().equals(subMenu.getId())) {
						subMenu.setRendered(true);
						System.out.println(subMenu.getLabel() + " [OK]");

						subMenuComItens(subMenu);
					}
				}
			}
		}
	}

	private void subMenuComItens(UISubmenu subMenu) {
		@SuppressWarnings("rawtypes")
		List element = subMenu.getElements();

		for (Object obj : element) {
			if (isMenuItem(obj)) {
				UIMenuItem item = (UIMenuItem) obj;
				item.setRendered(true);
			} else {
				UISubmenu menu = (UISubmenu) obj;
				menu.setRendered(true);
			}
		}
	}

	private boolean isMenuItem(Object componente) {
		return componente.toString().startsWith("org.primefaces.component.menuitem.UIMenuItem");
	}

	public MenuModel getMenubar() {
		return menubar;
	}

	public void setMenubar(MenuModel menubar) {
		this.menubar = menubar;
	}

	public Menubar getMenuBarTela() {
		return menuBarTela;
	}

	public void setMenuBarTela(Menubar menuBarTela) {
		this.menuBarTela = menuBarTela;
	}

	public List<Componente> getComponentes() {
		return componentes;
	}
}
