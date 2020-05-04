package com.profacil.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;

import com.profacil.model.Questao;
import com.profacil.repository.Questoes;
import com.profacil.util.cdi.CDIServiceLocator;

@FacesConverter(forClass = Questao.class)
public class QuestaoConverter implements Converter {

	private Questoes questoes;

	public QuestaoConverter() {
		questoes = CDIServiceLocator.getBean(Questoes.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Questao retorno = null;

		if (StringUtils.isNotEmpty(value)) {
			Long id = new Long(value);
			retorno = questoes.findById(id);
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Questao questao = (Questao) value;
			return questao.getId() == null ? null : questao.getId().toString();
		}

		return "";
	}

}
