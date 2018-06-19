package br.com.reprografia.prod.model.repository;

import br.com.reprografia.prod.model.entity.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

    @Query("SELECT d FROM Disciplina d WHERE d.id = :id")
    Disciplina findDisciplinaById(@Param("id") Long id);
}
