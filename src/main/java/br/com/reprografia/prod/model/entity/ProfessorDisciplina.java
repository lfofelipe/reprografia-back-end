package br.com.reprografia.prod.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "professor_disciplina")
public class ProfessorDisciplina implements Serializable {

    private static final long serialVersionUID = 8735060059417187982L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "professor_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties("disciplinas")
    private Professor professor;

    @ManyToOne
    @JoinColumn(name = "disciplina_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties("professores")
    private Disciplina disciplina;

    public ProfessorDisciplina() {
    }

    public ProfessorDisciplina(Long id) {
        this.id = id;
    }

    public ProfessorDisciplina(Long id, Professor professor, Disciplina disciplina) {
        this.id = id;
        this.professor = professor;
        this.disciplina = disciplina;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfessorDisciplina that = (ProfessorDisciplina) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
