package br.com.reprografia.prod.model.entity;

import br.com.reprografia.prod.common.enumerator.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "perfil")
public class Perfil{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @JsonIgnore //nao eh bom retornar as permissoes em tela...
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "perfil_permissao",
            joinColumns = @JoinColumn(name = "perfil_id"),
            inverseJoinColumns = @JoinColumn(name = "permissoes_id"))
    private List<Permissao> permissoes= new ArrayList<>();


    public boolean contemRoleOuAdmin(RoleEnum[] roleConfigurada) {
        if(permissoes!=null && !permissoes.isEmpty()){
            for(RoleEnum role: roleConfigurada){
                for(Permissao perm: permissoes){
                    if(perm.getPapel().getPermissao().equalsIgnoreCase(role.getPermissao())){
                        return true;
                    }

                }
            }
//            if (permissoes.stream().anyMatch(
//                    (perm) -> (
//                            for()
//                            perm.getPapel().getPermissao().equalsIgnoreCase(roleConfigurada.getPermissao())))) {
//                return true;
//            }
        }
        return false;
    }

    public Perfil() {
    }
    public Perfil(Long id) {
        this.id = id;
    }
    public Perfil(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Perfil(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Perfil perfil = (Perfil) o;
        return Objects.equals(id, perfil.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
