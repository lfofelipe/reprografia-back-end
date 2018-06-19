package br.com.reprografia.prod.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "andamento")
public class Andamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "data")
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	private Calendar data;

	@ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
	private Status status;

	@ManyToOne
    @JoinColumn(name = "pessoa_id", referencedColumnName = "id", nullable = false)
	@JsonIgnoreProperties("disciplinas")
	@JsonProperty(value = "responsavel")
	private Pessoa pessoa;

	@Column(name = "observacao")
	private String observacao;

	@ManyToOne
    @JoinColumn(name = "requisicao_id", referencedColumnName = "id", nullable = false)
	@JsonIgnoreProperties("historico")
	private Requisicao requisicao;

	public Andamento() {
	}

	public Andamento(Calendar data, Status status, Pessoa pessoa, String observacao) {
		this.data = data;
		this.status = status;
		this.pessoa = pessoa;
		this.observacao = observacao;
	}

    public Andamento(Calendar data, Status status, Pessoa pessoa, String observacao, Requisicao requisicao) {
        this.data = data;
        this.status = status;
        this.pessoa = pessoa;
        this.observacao = observacao;
        this.requisicao = requisicao;
    }

    public Requisicao getRequisicao() {
        return requisicao;
    }

    public void setRequisicao(Requisicao requisicao) {
        this.requisicao = requisicao;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}


	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Andamento andamento = (Andamento) o;
		return Objects.equals(id, andamento.id);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id);
	}
}
