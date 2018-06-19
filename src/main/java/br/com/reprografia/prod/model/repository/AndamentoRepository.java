package br.com.reprografia.prod.model.repository;

import br.com.reprografia.prod.model.entity.Andamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AndamentoRepository extends JpaRepository<Andamento, Long> {

    @Query("SELECT a FROM Andamento a WHERE a.id = :id")
    Andamento findAndamentoById(@Param("id") Long id);
}
