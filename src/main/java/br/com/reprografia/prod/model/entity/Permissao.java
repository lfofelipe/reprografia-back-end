package br.com.reprografia.prod.model.entity;

import br.com.reprografia.prod.common.enumerator.RoleEnum;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "permissao")
public class Permissao implements Serializable {
        
        private static final long serialVersionUID = 8765060059417187982L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

        @Column(name = "nome")
        private String nome;

        @Column(name = "descricao")
        private String descricao;

        @Enumerated(EnumType.STRING)
        private RoleEnum papel;

    public Permissao() {
    }

    public Permissao(String nome, String descricao, RoleEnum papel) {
        this.nome = nome;
        this.descricao = descricao;
        this.papel = papel;
    }

    public Permissao(Long id, String nome, String descricao, RoleEnum papel) {
                this.id = id;
                this.nome = nome;
                this.descricao = descricao;
                this.papel = papel;
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

        public String getDescricao() {
                return descricao;
        }

        public void setDescricao(String descricao) {
                this.descricao = descricao;
        }

        public RoleEnum getPapel() {
                return papel;
        }

        public void setPapel(RoleEnum papel) {
                this.papel = papel;
        }

    @Override
    public String toString() {
        return papel.toString();
    }
}
