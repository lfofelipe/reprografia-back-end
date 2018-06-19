package br.com.reprografia.prod.model.repository;

import br.com.reprografia.prod.model.entity.CentroDeCusto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Calendar;

public interface CentroDeCustoRepository extends JpaRepository<CentroDeCusto, Long> {

    @Query("SELECT c FROM CentroDeCusto c WHERE c.id = :id")
    CentroDeCusto findCentroDeCustoById(@Param("id") Long id);

    @Query("SELECT c FROM CentroDeCusto c WHERE :dataAbertura BETWEEN c.dataInicio AND c.dataFim")
    CentroDeCusto findCentroDeCustoVigente(@Param("dataAbertura")Calendar dataAbertura);

}
