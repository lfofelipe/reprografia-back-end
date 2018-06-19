package br.com.reprografia.prod.common.exception;

import br.com.reprografia.prod.common.enumerator.BusinessExceptionEnum;

public class BusinessException extends RuntimeException {
    private int codigoException;

    public BusinessException(){
        super();
    }

    public BusinessException(BusinessExceptionEnum exceptionEnum){
        super(exceptionEnum.getMensagem());
        this.codigoException = exceptionEnum.getCodigoException();
    }
    public BusinessException(int codigo,  String msg){
        super(msg);
        this.codigoException = codigo;
    }
    public int getCodigoException() {
        return codigoException;
    }

}
