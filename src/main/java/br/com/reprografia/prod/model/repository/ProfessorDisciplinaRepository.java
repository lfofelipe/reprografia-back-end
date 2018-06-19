package br.com.reprografia.prod.model.repository;

import br.com.reprografia.prod.model.entity.ProfessorDisciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfessorDisciplinaRepository extends JpaRepository<ProfessorDisciplina, Long> {

    @Query("SELECT p FROM ProfessorDisciplina p WHERE p.id = :id")
    ProfessorDisciplina findProfessorDisciplinaById(@Param("id") Long id);
}
