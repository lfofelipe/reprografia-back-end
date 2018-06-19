package br.com.reprografia.prod.service.security.anotacoes;


import br.com.reprografia.prod.common.enumerator.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Privado {
        public RoleEnum[] role();
}
