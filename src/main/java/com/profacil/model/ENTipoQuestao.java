package com.profacil.model;

public enum ENTipoQuestao {

	MUE("Múltipla Escolha"), PAB("Pergunta Aberta"), VOF("Verdadeiro ou Falso");

	private final String descricao;

	public String getDescricao() {
		return descricao;
	}

	public String toString() {
		return descricao;
	}

	ENTipoQuestao(String descricao) {
		this.descricao = descricao;
	}
}
