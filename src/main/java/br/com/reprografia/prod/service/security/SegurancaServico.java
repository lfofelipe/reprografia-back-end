package br.com.reprografia.prod.service.security;

import br.com.reprografia.prod.common.enumerator.BusinessExceptionEnum;
import br.com.reprografia.prod.common.exception.*;
import br.com.reprografia.prod.common.util.DateUtils;
import br.com.reprografia.prod.common.util.FormatadorUtil;
import br.com.reprografia.prod.common.util.Validator;
import br.com.reprografia.prod.model.entity.Pessoa;
import br.com.reprografia.prod.model.entity.Professor;
import br.com.reprografia.prod.model.entity.SegurancaAPI;
import br.com.reprografia.prod.model.repository.PerfilRepository;
import br.com.reprografia.prod.model.repository.PessoaRepository;
import br.com.reprografia.prod.model.repository.SegurancaRepository;
import br.com.reprografia.prod.service.security.util.SegurancaAPIThreadLocal;
import org.apache.commons.lang.StringUtils;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError.CodeResponse;
import org.apache.oltu.oauth2.common.error.OAuthError.ResourceResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Contexto de seguranca.
 */
@Service
public class SegurancaServico {

        private final String APP_CLIENT_ID = "reprografiaapi";
        private final String APP_CLIENT_PASSWD = "9834ba657bb2c60b5bb53de6f4201905";

        @Autowired
        private SegurancaRepository segurancaRepository;

        @Autowired
        private PessoaRepository pessoaRepository;

        @Autowired
        private PerfilRepository perfilRepository;

        private SegurancaAPI retornarPorToken(String token) {
                return this.segurancaRepository.findByToken(token);
        }

        private SegurancaAPI retornarPorUsuario(Pessoa pessoa) {
                return this.segurancaRepository.findByPessoa(pessoa);
        }

        @Transactional
        public synchronized void verificaValidadeTokenAdicionandoNoContexto(HttpServletRequest request) throws TokenExpiradoException, TokenInvalidoException {
                try {
                        OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.HEADER);
                        String token = oauthRequest.getAccessToken();

                        if (StringUtils.isBlank(token)) {
                                throw new TokenInvalidoException("Token vazio.");
                        }

                        SegurancaAPI segurancaAPI = this.retornarPorToken(token);
                        if (segurancaAPI == null) {
                                throw new TokenInvalidoException("Token invalido.");
                        }

                        Pessoa pessoa = segurancaAPI.getPessoa();
                        if (pessoa == null) {
                                throw new TokenInvalidoException("Problema interno no retorno do usuario: nulo.");
                        }

                        if (segurancaAPI.getToken().contains(token)) {
                                if (segurancaAPI.expirado()) {
                                        segurancaAPI.expirarToken();
                                        this.segurancaRepository.save(segurancaAPI);
                                        throw new TokenExpiradoException("Token de acesso expirado. Gere um novo token e tente novamente.");
                                } else {
                                        if (segurancaAPI.getPessoa() instanceof Professor){
                                                Professor professor = (Professor) segurancaAPI.getPessoa();
                                                Hibernate.initialize(professor.getPerfil().getPermissoes());//force! :/
                                                Hibernate.initialize(professor.getDisciplinas());
                                        } else {
                                                Hibernate.initialize(segurancaAPI.getPessoa().getPerfil().getPermissoes());
                                        }
//                                        segurancaAPI.getPessoa().setPerfil(this.perfilRepository.findPerfilEPermissoesByIdPerfil(segurancaAPI.getPessoa().getPerfil().getId()));
                                        SegurancaAPIThreadLocal.setSegurancaAPI(segurancaAPI);
                                }
                        } else {
                                throw new TokenInvalidoException("Token invalido. Tente novamente.");
                        }
                } catch (OAuthProblemException e) {
                        throw new TokenInvalidoException("Login invalido. Tente novamente.");
                } catch (OAuthSystemException ex) {
                        throw new RuntimeException(ex);
                }
        }

        private synchronized void atualizarToken(Pessoa pessoa, String token, Date proximaDataExpiracao) throws TokenInvalidoException, TokenExpiradoException, TokenCriadoException {
                if (pessoa == null) {
                        throw new TokenInvalidoException("Problema interno ao criar token: usuario nulo.");
                }
                if (StringUtils.isBlank(token)) {
                        throw new TokenInvalidoException("Problema interno ao criar token: token vazio.");
                }
                if (proximaDataExpiracao == null) {
                        throw new TokenInvalidoException("Problema interno ao criar token: proximaDataExpiracao nula.");
                }

                SegurancaAPI segurancaAPI = this.retornarPorUsuario(pessoa);
                if (segurancaAPI == null) {
                        segurancaAPI = new SegurancaAPI(token, proximaDataExpiracao, pessoa);
                } else {
                        segurancaAPI.atualizarToken(token, proximaDataExpiracao);
                }

                segurancaAPI = this.segurancaRepository.save(segurancaAPI);
                
                if (segurancaAPI.isSalvo()) {
                        if (segurancaAPI.expirado()) {
                                throw new TokenExpiradoException("Token de acesso expirado. Gere um novo token e tente novamente.");
                        }
                        else {
                                throw new TokenCriadoException(segurancaAPI);
                        }
                }
        }

        private Date retornarProximaDataExpiracao() {
                Date agora = new Date();
                int dia = DateUtils.retornaUnidade(agora, DateUtils.DIA);
                int mes = DateUtils.retornaUnidade(agora, DateUtils.MES);
                int ano = DateUtils.retornaUnidade(agora, DateUtils.ANO);
                return DateUtils.retornaData(dia + "/" + mes + "/" + ano + " 23:59:59", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"));
        }

        private void validarAcessoAplicativo(String appClientId, String appClientSecret) throws TokenInvalidoException {
                if (StringUtils.isBlank(appClientId)) {
                        throw new TokenInvalidoException("Atributo clientId nulo.");
                }
                if (StringUtils.isBlank(appClientId)) {
                        throw new TokenInvalidoException("Atributo clientSecret nulo.");
                }
                if (!appClientId.equalsIgnoreCase(APP_CLIENT_ID) && !appClientSecret.equalsIgnoreCase(APP_CLIENT_PASSWD)) {
                        throw new TokenInvalidoException("Seguranca: aplicativo nao autorizado.");
                }
        }
        
        private Pessoa retornarPorLoginESenha(String login, String senha) throws UsuarioOuSenhaInvalidaException {
                Pessoa  pessoa = this.pessoaRepository.findByLoginAndSenha(login, FormatadorUtil.encryptMD5(senha));
                if(pessoa == null){
                        throw new UsuarioOuSenhaInvalidaException("Usuário não encontrado.");
                }
                return pessoa;
        }
        @Transactional
        public OAuthResponse logarOAuth(HttpServletRequest request) {
                try {
                        OAuthTokenRequest oauthRequest = new OAuthTokenRequest(request);
                        String appClientId = oauthRequest.getClientId();
                        String appClientSecret = oauthRequest.getClientSecret();

                        try {
                                this.validarAcessoAplicativo(appClientId, appClientSecret);
                        } catch (TokenInvalidoException e) {
                                return this.retornarErroOAuth(HttpServletResponse.SC_UNAUTHORIZED, CodeResponse.UNAUTHORIZED_CLIENT, e);
                        }
                        
                        String senha = oauthRequest.getPassword();
                        String login = oauthRequest.getUsername();
                        Pessoa pessoa = null;
                        
                        try {
                                pessoa = this.retornarPorLoginESenha(login, senha);
                        } catch (UsuarioOuSenhaInvalidaException e) {
                                return this.retornarErroOAuth(HttpServletResponse.SC_UNAUTHORIZED, CodeResponse.UNAUTHORIZED_CLIENT, e);
                        }

                        if(!pessoa.getAtivo()){
                            throw new OAuthException("Usuário bloqueado.");
                        }
                        String accessToken = new OAuthIssuerImpl(new MD5Generator()).accessToken();
                        Date proximaDataExpiracao = this.retornarProximaDataExpiracao();
                        
                        try {
                                this.atualizarToken(pessoa, accessToken, proximaDataExpiracao);
                        } catch (TokenExpiradoException e) {
                                return this.retornarErroOAuth(HttpServletResponse.SC_UNAUTHORIZED, ResourceResponse.EXPIRED_TOKEN, e);
                        } catch (TokenInvalidoException e) {
                                return this.retornarErroOAuth(HttpServletResponse.SC_BAD_REQUEST, ResourceResponse.INVALID_TOKEN, e);
                        } catch (TokenCriadoException e) {
                                //token jah criado anteriormente, somente retorna.
                                proximaDataExpiracao = e.getSegurancaAPI().getExpiracaoToken();
                        }
                        pessoa = pessoaRepository.findPessoaById(pessoa.getId());
                        Hibernate.initialize(pessoa.getPerfil().getPermissoes());
                        return OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK).setAccessToken(accessToken).setExpiresIn(
                                this.transformarProximaDataExpiracaoEmSegundos(new Date(), proximaDataExpiracao))
                                .setParam("nome", pessoa.getNome())
                                .setParam("login", pessoa.getLogin())
                                .setParam("perfil", pessoa.getPerfil().getNome())
                                .setParam("privilegios", pessoa.getPerfil().getPermissoes().toString())
                                .buildJSONMessage();

                } catch (OAuthProblemException e) {
                        return this.retornarErroOAuth(HttpServletResponse.SC_UNAUTHORIZED, CodeResponse.INVALID_REQUEST, e);
                } catch (Exception e) {
                    e.printStackTrace();
                    return this.retornarErroOAuth(HttpServletResponse.SC_BAD_REQUEST, CodeResponse.SERVER_ERROR, e);
                }
        }

        public OAuthResponse retornarErroOAuth(int errorCode, String error, Exception e) {
                try {
                        String descricao = e.getMessage();
                        return OAuthASResponse.errorResponse(errorCode).setError(error + (descricao != null ? " - " + descricao : "")).setErrorDescription(descricao).buildJSONMessage();
                } catch (OAuthSystemException ex) {
                        throw new RuntimeException(ex);
                }
        }

        private String transformarProximaDataExpiracaoEmSegundos(Date atual, Date proxima) {
                int horas = DateUtils.getDiferencaHoras(atual, proxima);
                return "" + (horas * 60 * 60);
        }

        public SegurancaAPI getUsuarioLogado() throws TokenInvalidoException {
                SegurancaAPI seg = SegurancaAPIThreadLocal.getSegurancaAPI();
                if (seg == null) {
                        throw new TokenInvalidoException("Usuário não logado.");
                } else {
                        return seg;
                }
        }

        public void sair(String token){
                if(!Validator.isNullOrEmpty(token)){
                        SegurancaAPI segurancaAPI = segurancaRepository.findByToken(token.split(" ")[1]);
                        if(!Validator.isNullOrEmpty(segurancaAPI)){
                               segurancaRepository.deleteById(segurancaAPI.getId());
                        } else {
                                throw new BusinessException(BusinessExceptionEnum.REGISTROS_INEXISTENTES);
                        }
                } else {
                        throw new BusinessException(BusinessExceptionEnum.DADOS_NAO_PREENCHIDOS);
                }
        }

}
