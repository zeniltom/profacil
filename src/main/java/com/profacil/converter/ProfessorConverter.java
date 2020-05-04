package com.profacil.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.profacil.model.Professor;
import com.profacil.repository.Professores;

@FacesConverter(forClass = Professor.class)
public class ProfessorConverter implements Converter {

	@Inject
	private Professores professores;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Professor retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			retorno = this.professores.findById(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Professor professor = (Professor) value;
			return professor.getId() == null ? null : professor.getId().toString();
		}
		return "";
	}

}