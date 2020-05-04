package com.profacil.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import com.profacil.model.Curso;
import com.profacil.repository.Cursos;
import com.profacil.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Curso.class)
public class CursoConverter implements Converter {

	private Cursos cursos;

	public CursoConverter() {
		cursos = CDIServiceLocator.getBean(Cursos.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Curso retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = cursos.findById(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Curso curso = (Curso) value;
			return curso.getId() == null ? null : curso.getId().toString();
		}

		return "";
	}

}
