package br.com.reprografia.prod.model.repository;

import br.com.reprografia.prod.model.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    @Query("SELECT p FROM Professor p WHERE p.id = :id")
    Professor findProfessorById(@Param("id") Long id);
}
