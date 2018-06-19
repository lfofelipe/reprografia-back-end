package br.com.reprografia.prod.common.enumerator;

import br.com.reprografia.prod.model.entity.Status;

public enum StatusEnum {

	EM_ELABORACAO(1, "Em Elaboração"),
	EM_AVALIACAO(2, "Em Avaliação"),
	APROVADA(3, "Aprovada"),
	EM_IMPRESSAO(4, "Em Impressão"),
	CANCELADA(5, "Cancelada"),
	CONCLUIDA(6, "Concluída");

	private int id;
	private String descricao;

	StatusEnum(int id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
	public static StatusEnum getValueById(int id){
		for(StatusEnum status: StatusEnum.values()){
			if (status.id == id){
				return status;
			}
		}
		return null;
	}

	public static Status get(int id){
		for(StatusEnum statusCur: StatusEnum.values()){
			if (statusCur.id == id){
				Status status = new Status();
				status.setId(Long.valueOf(statusCur.getId()));
				status.setDescricao(statusCur.getDescricao());
				return status;
			}
		}
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
