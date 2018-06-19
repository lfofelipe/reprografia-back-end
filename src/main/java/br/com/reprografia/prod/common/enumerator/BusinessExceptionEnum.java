package br.com.reprografia.prod.common.enumerator;

public enum BusinessExceptionEnum {
	ID_NAO_ENCONTRADO(1001,"Não foi encontrado nenhum registro para este identificador específico."),
	DADOS_NAO_PREENCHIDOS(1002,"Existem campos obrigatórios não preenchidos."),
	DADOS_INVALIDOS(1003,"Dados não preenchidos corretamente."),
	ARQUIVO_INEXISTENTE(1004,"Arquivo não encontrado, favor anexar um arquivo."),
	ANEXO_EXTENSAO_NAO_SUPORTADA(1005,"Extensão não suportada, favor selecionar outro arquivo."),
	ANEXO_TAMANHO_NAO_SUPORTADO(1006,"Por favor, selecione um arquivo com até 5 MB."),
	FALHA_CRIAR_NOVO_REGISTRO(1007,"Ocorreu uma falha ao executar esta funcionalidade, por favor, tente novamente mais tarde."),
	FALHA_GRAVAR_ARQUIVO(1008,"Ocorreu uma falha ao gravar o anexo no servidor, consulte o suporte técnico."),
	FALHA_RECUPERAR_ARQUIVO(1009,"Ocorreu uma falha ao recuperar o anexo no servidor, consulte o suporte técnico."),
	REGISTROS_INEXISTENTES(1010,"Não existem registros compatíveis com os dados informados."),
	LOGIN_INDISPONIVEL(1011,"O login informado já está em uso."),
	PROFESSOR_POSSUI_REQUISICOES(1012,"O Professor informado possui requisições em andamento.");



	private int codigoException;
	private String mensagem;


	BusinessExceptionEnum(int codigoException, String mensagem) {
		this.codigoException = codigoException;
		this.mensagem = mensagem;
	}
	public int getCodigoException() {
		return codigoException;
	}

	public void setCodigoException(int codigoException) {
		this.codigoException = codigoException;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
