package br.com.reprografia.prod.common.exception;

public class UsuarioOuSenhaInvalidaException extends OAuthException {

        public UsuarioOuSenhaInvalidaException(String message) {
                super(message);
        }

        public UsuarioOuSenhaInvalidaException(Throwable cause) {
                super(cause);
        }
        
}
