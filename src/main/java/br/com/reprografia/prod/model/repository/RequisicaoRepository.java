package br.com.reprografia.prod.model.repository;

import br.com.reprografia.prod.model.entity.Disciplina;
import br.com.reprografia.prod.model.entity.Professor;
import br.com.reprografia.prod.model.entity.Requisicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Calendar;
import java.util.List;

public interface RequisicaoRepository extends JpaRepository<Requisicao, Long> {

    @Query("SELECT r FROM Requisicao r WHERE r.id = :id")
    Requisicao findRequisicaoById(@Param("id") Long id);

    @Query("SELECT r FROM Requisicao r JOIN ProfessorDisciplina pd ON (pd = r.professorDisciplina AND pd.id = :id)")
    List<Requisicao> findRequisicaoByProfessorDisciplina(@Param("id") Long id);

    @Query("SELECT r FROM Requisicao r WHERE (r.dataConclusao BETWEEN :primeiroDia AND :ultimoDia) AND (r.centroDeCusto IS NOT NULL)")
    List<Requisicao> findByMonth(@Param("primeiroDia")Calendar primeiroDia, @Param("ultimoDia")Calendar ultimoDia);

    @Query("SELECT r " +
            "FROM Requisicao r " +
            "JOIN ProfessorDisciplina pd ON (pd = r.professorDisciplina AND pd.disciplina = :disciplina) WHERE (r.centroDeCusto IS NOT NULL)")
    List<Requisicao> findByDisciplina(@Param("disciplina")Disciplina disciplina);

    @Query("SELECT r " +
            "FROM Requisicao r " +
            "JOIN ProfessorDisciplina pd ON (pd = r.professorDisciplina AND pd.professor = :professor) WHERE (r.centroDeCusto IS NOT NULL)")
    List<Requisicao> findByProfessor(@Param("professor")Professor professor);
}
