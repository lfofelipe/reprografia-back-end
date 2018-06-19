package br.com.reprografia.prod.model.repository;

import br.com.reprografia.prod.model.entity.Pessoa;
import br.com.reprografia.prod.model.entity.SegurancaAPI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface SegurancaRepository extends JpaRepository<SegurancaAPI, Long> {
    @Query("SELECT s FROM SegurancaAPI s WHERE s.token = :token")
    SegurancaAPI findByToken(@Param("token") String token);

    SegurancaAPI findByPessoa(Pessoa pessoa);
}
