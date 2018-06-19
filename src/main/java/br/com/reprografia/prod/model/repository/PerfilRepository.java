package br.com.reprografia.prod.model.repository;

import br.com.reprografia.prod.model.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    @Query("SELECT p FROM Perfil p WHERE p.id = :id")
    Perfil findPerfilById(@Param("id")Long id);

}
