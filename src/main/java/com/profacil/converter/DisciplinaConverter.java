package com.profacil.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import com.profacil.model.Disciplina;
import com.profacil.repository.Disciplinas;
import com.profacil.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Disciplina.class)
public class DisciplinaConverter implements Converter {

	private Disciplinas disciplinas;

	public DisciplinaConverter() {
		disciplinas = CDIServiceLocator.getBean(Disciplinas.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Disciplina retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = disciplinas.findById(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Disciplina disciplina = (Disciplina) value;
			return disciplina.getId() == null ? null : disciplina.getId().toString();
		}

		return "";
	}

}
