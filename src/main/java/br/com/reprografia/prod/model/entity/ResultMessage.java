package br.com.reprografia.prod.model.entity;

import br.com.reprografia.prod.common.enumerator.BusinessExceptionEnum;

public class ResultMessage {
    private String exceptionType;
    private Integer codigo;
    private String mensagem;

    public ResultMessage() {
    }

    public ResultMessage(String exceptionType, Integer codigo, String mensagem) {
        this.exceptionType = exceptionType;
        this.codigo = codigo;
        this.mensagem = mensagem;
    }
    public ResultMessage(String exceptionType, BusinessExceptionEnum businessExceptionEnum) {
        this.exceptionType = exceptionType;
        this.codigo = businessExceptionEnum.getCodigoException();
        this.mensagem = businessExceptionEnum.getMensagem();
    }


    public String getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
