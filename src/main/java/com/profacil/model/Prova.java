package com.profacil.model;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "prova")
public class Prova implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 150)
	@NotBlank(message = "é obrigatório")
	private String descricao;

	@Column(length = 150)
	@NotBlank(message = "é obrigatória")
	private String turma;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "é obrigatório")
	private ENTurnoEscolar turno;

	@Temporal(TemporalType.DATE)
	@NotNull(message = "é obrigatório")
	private Date dataProva;

	@NotNull(message = "é obrigatório")
	@Column(precision = 2, columnDefinition = "double NOT NULL default 1")
	private Double valor;

	@NotNull(message = "é obrigatório")
	@Column(columnDefinition = "int NOT NULL default 1")
	private Integer nQuestoes;

	@ManyToOne
	@JoinColumn(name = "acervo_id", nullable = false)
	private Acervo acervo;

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

	public String getTurma() {
		return turma;
	}

	public void setTurma(String turma) {
		this.turma = turma;
	}

	public ENTurnoEscolar getTurno() {
		return turno;
	}

	public void setTurno(ENTurnoEscolar turno) {
		this.turno = turno;
	}

	public Date getDataProva() {
		return dataProva;
	}

	public void setDataProva(Date dataProva) {
		this.dataProva = dataProva;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Integer getnQuestoes() {
		return nQuestoes;
	}

	public void setnQuestoes(Integer nQuestoes) {
		this.nQuestoes = nQuestoes;
	}

	public Acervo getAcervo() {
		return acervo;
	}

	public void setAcervo(Acervo acervo) {
		this.acervo = acervo;
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
		Prova other = (Prova) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
