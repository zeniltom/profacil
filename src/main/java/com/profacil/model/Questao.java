package com.profacil.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "questao")
public class Questao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "é obrigatório")
	@NotBlank(message = "é obrigatório")
	@Length(max = 1000, message = "não pode ultrapassar de 1000 caracteres.")
	@Column(nullable = false)
	private String descricao;

	@Enumerated(EnumType.STRING)
	@NotNull
	private ENTipoQuestao tipoQuestao;

	@NotNull(message = "é obrigatório")
	@Column(precision = 2, columnDefinition = "double NOT NULL default 1")
	private Double pontuacao;

	@ManyToOne
	@NotNull(message = "é obrigatório")
	@JoinColumn(name = "disciplina_id", nullable = false)
	private Disciplina disciplina;

	@Override
	public String toString() {
		return descricao + " (" + id + ")";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public ENTipoQuestao getTipoQuestao() {
		return tipoQuestao;
	}

	public void setTipoQuestao(ENTipoQuestao tipoQuestao) {
		this.tipoQuestao = tipoQuestao;
	}

	public Double getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Double pontuacao) {
		this.pontuacao = pontuacao;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Questao other = (Questao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
