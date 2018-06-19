package br.com.reprografia.prod.web;

import br.com.reprografia.prod.common.enumerator.RoleEnum;
import br.com.reprografia.prod.model.entity.SegurancaAPI;
import br.com.reprografia.prod.service.security.SegurancaServico;
import br.com.reprografia.prod.service.security.anotacoes.Privado;
import br.com.reprografia.prod.service.security.anotacoes.Publico;
import br.com.reprografia.prod.web.util.SuperController;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/seguranca")
public class SegurancaController extends SuperController {

    @Autowired
    private SegurancaServico segurancaServico;

    @Publico
    @ResponseBody
    @RequestMapping(value = "/logar", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String logar(HttpServletRequest request) {
        OAuthResponse response = segurancaServico.logarOAuth(request);
        return response.getBody();
    }

    @Privado(role = RoleEnum.ROLE_GERAL)
    @ResponseBody
    @RequestMapping(value = "/usuario/logado", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public SegurancaAPI retornarUsuarioLogado(HttpServletRequest request) {
        return segurancaServico.getUsuarioLogado();
    }

    @Privado(role = RoleEnum.ROLE_GERAL)
    @ResponseBody
    @RequestMapping(value = "/sair", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void sair(@RequestHeader("Authorization") String token) {
        segurancaServico.sair(token);
    }
}
