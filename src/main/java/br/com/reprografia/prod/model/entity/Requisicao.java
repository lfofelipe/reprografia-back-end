package br.com.reprografia.prod.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "requisicao")
public class Requisicao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
    @JoinColumn(name = "professor_disciplina_id", referencedColumnName = "id", nullable = false)
	private ProfessorDisciplina professorDisciplina;

    @ManyToOne
    @JoinColumn(name = "custo_id", referencedColumnName = "id", nullable = false)
    private CentroDeCusto centroDeCusto;

	@Column(name = "duplex", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean duplex;

	@Column(name = "grampeada", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean grampeada;

	@Column(name = "colorida", nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean colorida;

	@Column(name = "numero_de_paginas")
	private Integer numeroDePaginas;

	@Column(name = "numero_de_copias")
	private Integer numeroDeCopias;

	@Column(name = "custo_da_requisicao")
	private BigDecimal custoDaRequisicao;
    
	@Transient
	private byte[] arquivo;

	@Column(name = "arquivo")
	private String nomeArquivo;

	@Column(name = "tamanho_arquivo")
    private Long tamanhoArquivo;

	@Column(name = "data_abertura")
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	private Calendar dataAbertura;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="requisicao_id")
    @JsonIgnoreProperties("requisicao")
	private List<Andamento> historico;

    @Transient
    @JsonIgnoreProperties("requisicao")
	private Andamento andamentoVigente;

    @Column(name = "data_conclusao")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Calendar dataConclusao;

	public Requisicao() {
	}

    public Requisicao(ProfessorDisciplina professorDisciplina, Boolean duplex, Boolean grampeada, Boolean colorida, Integer numeroDePaginas, Integer numeroDeCopias, BigDecimal custoDaRequisicao, byte[] arquivo, Calendar dataAbertura) {
        this.professorDisciplina = professorDisciplina;
        this.duplex = duplex;
        this.grampeada = grampeada;
        this.colorida = colorida;
        this.numeroDePaginas = numeroDePaginas;
        this.numeroDeCopias = numeroDeCopias;
        this.custoDaRequisicao = custoDaRequisicao;
        this.arquivo = arquivo;
        this.dataAbertura = dataAbertura;
    }

    public Requisicao(ProfessorDisciplina professorDisciplina, Boolean duplex, Boolean grampeada, Boolean colorida, Integer numeroDePaginas, Integer numeroDeCopias, BigDecimal custoDaRequisicao, byte[] arquivo, String nomeArquivo, Calendar dataAbertura, List<Andamento> historico, Andamento andamentoVigente) {
        this.professorDisciplina = professorDisciplina;
        this.duplex = duplex;
        this.grampeada = grampeada;
        this.colorida = colorida;
        this.numeroDePaginas = numeroDePaginas;
        this.numeroDeCopias = numeroDeCopias;
        this.custoDaRequisicao = custoDaRequisicao;
        this.arquivo = arquivo;
        this.nomeArquivo = nomeArquivo;
        this.dataAbertura = dataAbertura;
        this.historico = historico;
        this.andamentoVigente = andamentoVigente;
    }

    public Requisicao(ProfessorDisciplina professorDisciplina, CentroDeCusto centroDeCusto, Boolean duplex, Boolean grampeada, Boolean colorida, Integer numeroDePaginas, Integer numeroDeCopias, BigDecimal custoDaRequisicao, byte[] arquivo, String nomeArquivo, Long tamanhoArquivo, Calendar dataAbertura, List<Andamento> historico, Andamento andamentoVigente, Calendar dataConclusao) {
        this.professorDisciplina = professorDisciplina;
        this.centroDeCusto = centroDeCusto;
        this.duplex = duplex;
        this.grampeada = grampeada;
        this.colorida = colorida;
        this.numeroDePaginas = numeroDePaginas;
        this.numeroDeCopias = numeroDeCopias;
        this.custoDaRequisicao = custoDaRequisicao;
        this.arquivo = arquivo;
        this.nomeArquivo = nomeArquivo;
        this.tamanhoArquivo = tamanhoArquivo;
        this.dataAbertura = dataAbertura;
        this.historico = historico;
        this.andamentoVigente = andamentoVigente;
        this.dataConclusao = dataConclusao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProfessorDisciplina getProfessorDisciplina() {
        return professorDisciplina;
    }

    public void setProfessorDisciplina(ProfessorDisciplina professorDisciplina) {
        this.professorDisciplina = professorDisciplina;
    }

    public CentroDeCusto getCentroDeCusto() {
        return centroDeCusto;
    }

    public void setCentroDeCusto(CentroDeCusto centroDeCusto) {
        this.centroDeCusto = centroDeCusto;
    }

    public Andamento getAndamentoVigente() {
        return andamentoVigente;
    }

    public void setAndamentoVigente(Andamento andamentoVigente) {
        this.andamentoVigente = andamentoVigente;
    }

    public Boolean getDuplex() {
        return duplex;
    }

    public void setDuplex(Boolean duplex) {
        this.duplex = duplex;
    }

    public Boolean getGrampeada() {
        return grampeada;
    }

    public void setGrampeada(Boolean grampeada) {
        this.grampeada = grampeada;
    }

    public Boolean getColorida() {
        return colorida;
    }

    public void setColorida(Boolean colorida) {
        this.colorida = colorida;
    }

    public Integer getNumeroDePaginas() {
        return numeroDePaginas;
    }

    public void setNumeroDePaginas(Integer numeroDePaginas) {
        this.numeroDePaginas = numeroDePaginas;
    }

    public Integer getNumeroDeCopias() {
        return numeroDeCopias;
    }

    public void setNumeroDeCopias(Integer numeroDeCopias) {
        this.numeroDeCopias = numeroDeCopias;
    }

    public BigDecimal getCustoDaRequisicao() {
        return custoDaRequisicao;
    }

    public void setCustoDaRequisicao(BigDecimal custoDaRequisicao) {
        this.custoDaRequisicao = custoDaRequisicao;
    }

    @JsonIgnore
    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public Long getTamanhoArquivo() {
        return tamanhoArquivo;
    }

    public void setTamanhoArquivo(Long tamanhoArquivo) {
        this.tamanhoArquivo = tamanhoArquivo;
    }

    public Calendar getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Calendar dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public List<Andamento> getHistorico() {
        return historico;
    }

    public void setHistorico(List<Andamento> historico) {
        this.historico = historico;
    }

    public Calendar getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(Calendar dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    @Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Requisicao that = (Requisicao) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id);
	}
}
