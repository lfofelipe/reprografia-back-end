package br.com.reprografia.prod.model.entity;

import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "segurancaapi")
public class SegurancaAPI{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token")
	private String token = "(init)";

    @Temporal(TemporalType.TIMESTAMP)
	private Date expiracaoToken = new Date();

    @OneToOne
	private Pessoa pessoa;
        
    public SegurancaAPI(String token, Date expiracaoToken, Pessoa pessoa) {
            this.token = token;
            this.expiracaoToken = expiracaoToken;
            this.pessoa = pessoa;
    }
    public SegurancaAPI(Long id, String token, Date expiracaoToken, Pessoa pessoa) {
        this.id = id;
        this.token = token;
        this.expiracaoToken = expiracaoToken;
        this.pessoa = pessoa;
    }

    public SegurancaAPI() {
    }

    public void atualizarToken(String token, Date expiracaoToken) {
        this.token = token;
        this.expiracaoToken = expiracaoToken;
	}
        
    public void expirarToken() {
		this.atualizarToken("", new Date());
	}

	public boolean expirado() {
		return expiracaoToken != null && expiracaoToken.before(new Date());
	}
        
    public String getToken() {
            if(StringUtils.isBlank(token)){
                    return null;
            }
            return token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpiracaoToken(Date expiracaoToken) {
        this.expiracaoToken = expiracaoToken;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Date getExpiracaoToken() {
                return expiracaoToken;
        }

    public boolean isSalvo() {
        return getId() != null;
    }

    public boolean isNaoSalvo() {
        return !isSalvo();
    }
}
