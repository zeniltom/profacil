package com.profacil.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import com.profacil.model.Prova;
import com.profacil.repository.Provas;
import com.profacil.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Prova.class)
public class ProvaConverter implements Converter {

	private Provas provas;

	public ProvaConverter() {
		provas = CDIServiceLocator.getBean(Provas.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Prova retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = provas.findById(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Prova prova = (Prova) value;
			return prova.getId() == null ? null : prova.getId().toString();
		}

		return "";
	}

}
