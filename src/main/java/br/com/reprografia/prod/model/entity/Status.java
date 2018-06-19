package br.com.reprografia.prod.model.entity;

import br.com.reprografia.prod.common.enumerator.StatusEnum;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "status")
public class Status {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "descricao")
	private String descricao;

	public Status() {
	}

	public Status(Long id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public Status(StatusEnum statusEnum){
        this.id = (long) statusEnum.getId();
        this.descricao = statusEnum.getDescricao();
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Status status = (Status) o;
		return Objects.equals(id, status.id);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id);
	}
}
