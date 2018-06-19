package br.com.reprografia.prod.common.exception;

public class TokenExpiradoException extends OAuthException {

        public TokenExpiradoException(String message) {
                super(message);
        }

        public TokenExpiradoException(Throwable cause) {
                super(cause);
        }
        
}
