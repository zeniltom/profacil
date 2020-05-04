package com.profacil.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import com.profacil.model.Resposta;
import com.profacil.repository.Respostas;
import com.profacil.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Resposta.class)
public class RespostaConverter implements Converter {

	private Respostas respostas;

	public RespostaConverter() {
		respostas = CDIServiceLocator.getBean(Respostas.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Resposta retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = respostas.findById(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Resposta resposta = (Resposta) value;
			return resposta.getId() == null ? null : resposta.getId().toString();
		}

		return "";
	}

}
