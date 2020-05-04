package com.profacil.model;

public enum ENTurnoEscolar {

	MAT("Matutino"), VEP("Vespertino"), NOT("Noturno"), DIU("Diurno");

	private final String descricao;

	public String getDescricao() {
		return descricao;
	}

	public String toString() {
		return descricao;
	}

	ENTurnoEscolar(String descricao) {
		this.descricao = descricao;
	}
}
