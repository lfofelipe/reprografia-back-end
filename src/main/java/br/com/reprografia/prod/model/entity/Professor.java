package br.com.reprografia.prod.model.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "pessoa")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name="tipo_pessoa",
        discriminatorType=DiscriminatorType.STRING
)
@DiscriminatorValue("professor")
public class Professor extends Pessoa implements Serializable {

    private static final long serialVersionUID = 8725060059417187982L;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "professor_disciplina",
            joinColumns = @JoinColumn(name = "professor_id"),
            inverseJoinColumns = @JoinColumn(name = "disciplina_id"))
    @JsonIgnoreProperties("professores")
    private List<Disciplina> disciplinas = new ArrayList<>();

    @OneToMany(mappedBy = "professor", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ProfessorDisciplina> professorDisciplinas = new HashSet<>();

    public Professor() {
        super();
    }

    public Professor(Long id){
        super(id);
    }

    public Professor(Long id, String nome, String email, String login, String senha, boolean ativo, Perfil perfil, Set<ProfessorDisciplina> professorDisciplinas) {
        super(id, nome, email, login, senha, ativo, perfil);
        this.professorDisciplinas = professorDisciplinas;
    }

    public Professor(Long id, String nome, String email, String login, String senha, boolean ativo, Perfil perfil, List<Disciplina> disciplinas, Set<ProfessorDisciplina> professorDisciplinas) {
        super(id, nome, email, login, senha, ativo, perfil);
        this.disciplinas = disciplinas;
        this.professorDisciplinas = professorDisciplinas;
    }

    public Set<ProfessorDisciplina> getProfessorDisciplinas() {
        return professorDisciplinas;
    }

    public void setProfessorDisciplinas(Set<ProfessorDisciplina> professorDisciplinas) {
        this.professorDisciplinas = professorDisciplinas;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }
}
