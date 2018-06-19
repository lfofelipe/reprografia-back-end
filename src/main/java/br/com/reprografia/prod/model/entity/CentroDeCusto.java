package br.com.reprografia.prod.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.type.CurrencyType;
import org.hibernate.type.descriptor.java.CurrencyTypeDescriptor;
import org.hibernate.validator.constraints.Currency;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "centro_custo")
public class CentroDeCusto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(name = "cota_preta")
	private Integer cota;

    @Column(name = "custo_fixo_preta")
	private BigDecimal custoFixoPreta;

    @Column(name = "custo_fixo_colorida")
	private BigDecimal custoFixoColorida;

    @Column(name = "valor_exc_preta")
	private BigDecimal valorExcPreta;

    @Column(name = "valor_exc_colorida")
	private BigDecimal valorExcColorida;

    @Column(name = "data_inicio")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	private Calendar dataInicio;

    @Column(name = "data_fim")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	private Calendar dataFim;

    @Column(name= "total_preto_impresso")
    private Integer totalPretoImpresso;

    @Column(name= "total_colorido_impresso")
    private Integer totalColoridoImpresso;

	public CentroDeCusto() {
	}

    public CentroDeCusto(Integer cota, BigDecimal custoFixoPreta, BigDecimal custoFixoColorida, BigDecimal valorExcPreta, BigDecimal valorExcColorida, Calendar dataInicio, Calendar dataFim) {
        this.cota = cota;
        this.custoFixoPreta = custoFixoPreta;
        this.custoFixoColorida = custoFixoColorida;
        this.valorExcPreta = valorExcPreta;
        this.valorExcColorida = valorExcColorida;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public CentroDeCusto(Integer cota, BigDecimal custoFixoPreta, BigDecimal custoFixoColorida, BigDecimal valorExcPreta, BigDecimal valorExcColorida, Calendar dataInicio, Calendar dataFim, Integer totalPretoImpresso, Integer totalColoridoImpresso) {
        this.cota = cota;
        this.custoFixoPreta = custoFixoPreta;
        this.custoFixoColorida = custoFixoColorida;
        this.valorExcPreta = valorExcPreta;
        this.valorExcColorida = valorExcColorida;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.totalPretoImpresso = totalPretoImpresso;
        this.totalColoridoImpresso = totalColoridoImpresso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCota() {
        return cota;
    }

    public void setCota(Integer cota) {
        this.cota = cota;
    }

    public BigDecimal getCustoFixoPreta() {
        return custoFixoPreta;
    }

    public void setCustoFixoPreta(BigDecimal custoFixoPreta) {
        this.custoFixoPreta = custoFixoPreta;
    }

    public BigDecimal getCustoFixoColorida() {
        return custoFixoColorida;
    }

    public void setCustoFixoColorida(BigDecimal custoFixoColorida) {
        this.custoFixoColorida = custoFixoColorida;
    }

    public BigDecimal getValorExcPreta() {
        return valorExcPreta;
    }

    public void setValorExcPreta(BigDecimal valorExcPreta) {
        this.valorExcPreta = valorExcPreta;
    }

    public BigDecimal getValorExcColorida() {
        return valorExcColorida;
    }

    public void setValorExcColorida(BigDecimal valorExcColorida) {
        this.valorExcColorida = valorExcColorida;
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

    public Integer getTotalPretoImpresso() {
        return totalPretoImpresso;
    }

    public void setTotalPretoImpresso(Integer totalPretoImpresso) {
        this.totalPretoImpresso = totalPretoImpresso;
    }

    public Integer getTotalColoridoImpresso() {
        return totalColoridoImpresso;
    }

    public void setTotalColoridoImpresso(Integer totalColoridoImpresso) {
        this.totalColoridoImpresso = totalColoridoImpresso;
    }

    @Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CentroDeCusto that = (CentroDeCusto) o;
		return Objects.equals(id, that.id);
	}

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
