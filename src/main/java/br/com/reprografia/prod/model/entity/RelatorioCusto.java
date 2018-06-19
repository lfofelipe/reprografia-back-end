package br.com.reprografia.prod.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RelatorioCusto {

    @JsonIgnoreProperties("historico")
    private List<Requisicao> requisicoes = new ArrayList<>();

    private BigDecimal somatorioCustoTotal;

    private BigDecimal custoTotalImpressoesColoridas;

    private BigDecimal custoTotalImpressoesPretas;

    private Integer quantidadeImpressoesColoridas;

    private Integer quantidadeImpressoesPretas;

    private ProfessorDisciplina professorDisciplina;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Calendar dataInicio;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
    private Calendar dataFim;

    public RelatorioCusto() {
    }

    public RelatorioCusto(List<Requisicao> requisicoes, BigDecimal somatorioCustoTotal, BigDecimal custoTotalImpressoesColoridas, BigDecimal custoTotalImpressoesPretas, Integer quantidadeImpressoesColoridas, Integer quantidadeImpressoesPretas, ProfessorDisciplina professorDisciplina) {
        this.requisicoes = requisicoes;
        this.somatorioCustoTotal = somatorioCustoTotal;
        this.custoTotalImpressoesColoridas = custoTotalImpressoesColoridas;
        this.custoTotalImpressoesPretas = custoTotalImpressoesPretas;
        this.quantidadeImpressoesColoridas = quantidadeImpressoesColoridas;
        this.quantidadeImpressoesPretas = quantidadeImpressoesPretas;
        this.professorDisciplina = professorDisciplina;
    }

    public List<Requisicao> getRequisicoes() {
        return requisicoes;
    }

    public void setRequisicoes(List<Requisicao> requisicoes) {
        this.requisicoes = requisicoes;
    }

    public BigDecimal getSomatorioCustoTotal() {
        return somatorioCustoTotal;
    }

    public void setSomatorioCustoTotal(BigDecimal somatorioCustoTotal) {
        this.somatorioCustoTotal = somatorioCustoTotal;
    }

    public BigDecimal getCustoTotalImpressoesColoridas() {
        return custoTotalImpressoesColoridas;
    }

    public void setCustoTotalImpressoesColoridas(BigDecimal custoTotalImpressoesColoridas) {
        this.custoTotalImpressoesColoridas = custoTotalImpressoesColoridas;
    }

    public BigDecimal getCustoTotalImpressoesPretas() {
        return custoTotalImpressoesPretas;
    }

    public void setCustoTotalImpressoesPretas(BigDecimal custoTotalImpressoesPretas) {
        this.custoTotalImpressoesPretas = custoTotalImpressoesPretas;
    }

    public Integer getQuantidadeImpressoesColoridas() {
        return quantidadeImpressoesColoridas;
    }

    public void setQuantidadeImpressoesColoridas(Integer quantidadeImpressoesColoridas) {
        this.quantidadeImpressoesColoridas = quantidadeImpressoesColoridas;
    }

    public Integer getQuantidadeImpressoesPretas() {
        return quantidadeImpressoesPretas;
    }

    public void setQuantidadeImpressoesPretas(Integer quantidadeImpressoesPretas) {
        this.quantidadeImpressoesPretas = quantidadeImpressoesPretas;
    }

    public ProfessorDisciplina getProfessorDisciplina() {
        return professorDisciplina;
    }

    public void setProfessorDisciplina(ProfessorDisciplina professorDisciplina) {
        this.professorDisciplina = professorDisciplina;
    }

    public Calendar getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Calendar dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Calendar getDataFim() {
        return dataFim;
    }

    public void setDataFim(Calendar dataFim) {
        this.dataFim = dataFim;
    }
}
