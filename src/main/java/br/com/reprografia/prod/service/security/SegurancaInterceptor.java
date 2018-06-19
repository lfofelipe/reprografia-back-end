package br.com.reprografia.prod.service.security;

import br.com.reprografia.prod.common.enumerator.RoleEnum;
import br.com.reprografia.prod.common.exception.OAuthException;
import br.com.reprografia.prod.model.entity.SegurancaAPI;
import br.com.reprografia.prod.service.security.anotacoes.Privado;
import br.com.reprografia.prod.service.security.anotacoes.Publico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SegurancaInterceptor extends HandlerInterceptorAdapter {

        @Autowired
        private SegurancaServico segurancaServico;
        
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                if(handler instanceof HandlerMethod){
                        HandlerMethod met = (HandlerMethod) handler;
                        
                        //acesso publico, libera
                        Publico annotPublico = met.getMethodAnnotation(Publico.class);
                        if(annotPublico!=null){
                                return true;
                        }
                        
                        //acesso privado, verifica as roles do usuario com a role configurada no metodo.
                        Privado annotPrivado = met.getMethodAnnotation(Privado.class);
                        if(annotPrivado!=null){
                                segurancaServico.verificaValidadeTokenAdicionandoNoContexto(request);
                                
                                RoleEnum[] roleConfigurada = annotPrivado.role();
                                SegurancaAPI usuarioLogado = segurancaServico.getUsuarioLogado();
                                if( usuarioLogado.getPessoa().getPerfil().contemRoleOuAdmin(roleConfigurada) ){
                                        return true;
                                } else {
                                        throw new OAuthException("Sem acesso ao recurso.");
                                }
                        }
                        
                        //se nao eh publico nem privado, nega por padrao.
                        return this.negarAcesso(response);
                }else{
                        return super.preHandle(request, response, handler);
                }
        }
        
        private boolean negarAcesso(HttpServletResponse response) throws IOException{
                response.getWriter().println("Sem acesso ao recurso.");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
        }
        
}
