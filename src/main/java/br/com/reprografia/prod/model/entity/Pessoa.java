package br.com.reprografia.prod.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "pessoa")
@NamedEntityGraph(name = "perfil.permissoes", attributeNodes = @NamedAttributeNode(value = "perfil", subgraph = "permissoes"),
        subgraphs = @NamedSubgraph(name = "permissoes", attributeNodes = @NamedAttributeNode("permissoes")))
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name="tipo_pessoa",
        discriminatorType=DiscriminatorType.STRING
)

@DiscriminatorValue("normal")
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 8715060059417187982L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(name = "nome")
	private String nome;

    @Column(name = "email")
	private String email;

    @Column(name = "login")
	private String login;

    @Column(name = "senha")
	private String senha;

    @Column(nullable = false)
    @Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean ativo;

    @ManyToOne(optional = false)
	private Perfil perfil;

    public Pessoa() {
    }
    public Pessoa(Long id) {
        this.id = id;
    }
    public Pessoa(String nome, String email, String login, String senha, Perfil perfil) {
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }
    public Pessoa(Long id, String nome, String email, String login, String senha, boolean ativo, Perfil perfil) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.ativo = ativo;
        this.perfil = perfil;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @JsonIgnore
    public String getSenha() {
        return senha;
    }
    @JsonProperty
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public boolean isSalvo() {
        return getId() != null;
    }

    public boolean isNaoSalvo() {
        return !isSalvo();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(id, pessoa.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
