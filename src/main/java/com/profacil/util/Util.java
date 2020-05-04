package com.profacil.util;

import java.io.IOException;

import javax.faces.context.FacesContext;

import org.omnifaces.util.Faces;

public class Util {

	public static String contexto() {
		FacesContext context = FacesContext.getCurrentInstance();
		String url = context.getExternalContext().getRequestContextPath();
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

		return url;
	}

	public static void redirecionarObjeto(String url) throws IOException {
		Faces.redirect(Util.contexto() + url);
	}

}
