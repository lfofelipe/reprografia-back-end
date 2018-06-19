package br.com.reprografia.prod.model.repository;

import br.com.reprografia.prod.model.entity.Pessoa;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @EntityGraph(value = "perfil.permissoes", type = EntityGraph.EntityGraphType.FETCH)
    Pessoa findByLogin(String login);

    @EntityGraph(value = "perfil.permissoes", type = EntityGraph.EntityGraphType.FETCH)
    Pessoa findByLoginAndSenha(String login, String senha);

//    @Query("UPDATE Perfil p SET p.ativo = false WHERE p.id = :id")
//    int inativarPessoaById(@Param("id")Long id);

    @Query("SELECT p FROM Pessoa p WHERE p.id = :id")
    Pessoa findPessoaById(@Param("id")Long id);
}
