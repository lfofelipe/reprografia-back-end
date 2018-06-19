package br.com.reprografia.prod.model.entity;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "disciplina")
public class Disciplina {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "segmento")
	private String segmento;

	@Column(name = "serie")
	private String serie;

	@Column(name = "descricao")
	private String descricao;

	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean ativo;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "professor_disciplina",
			joinColumns = @JoinColumn(name = "disciplina_id"),
			inverseJoinColumns = @JoinColumn(name = "professor_id"))
	@JsonIgnoreProperties("disciplinas")
	private List<Professor> professores = new ArrayList<>();

	@OneToMany(mappedBy = "disciplina", fetch = FetchType.LAZY)
	@JsonIgnore
	private Set<ProfessorDisciplina> professorDisciplinas = new HashSet<>();

	public Disciplina() {
	}

	public Disciplina(Long id) {
		this.id = id;
	}

	public Disciplina(String segmento, String serie, String descricao, boolean ativo, Set<ProfessorDisciplina> professorDisciplinas) {
		this.segmento = segmento;
		this.serie = serie;
		this.descricao = descricao;
		this.ativo = ativo;
		this.professorDisciplinas = professorDisciplinas;
	}

	public Disciplina(String segmento, String serie, String descricao, boolean ativo, List<Professor> professores, Set<ProfessorDisciplina> professorDisciplinas) {
		this.segmento = segmento;
		this.serie = serie;
		this.descricao = descricao;
		this.ativo = ativo;
		this.professores = professores;
		this.professorDisciplinas = professorDisciplinas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSegmento() {
		return segmento;
	}

	public void setSegmento(String segmento) {
		this.segmento = segmento;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Set<ProfessorDisciplina> getProfessorDisciplinas() {
		return professorDisciplinas;
	}

	public void setProfessorDisciplinas(Set<ProfessorDisciplina> professorDisciplinas) {
		this.professorDisciplinas = professorDisciplinas;
	}

	public List<Professor> getProfessores() {
		return professores;
	}

	public void setProfessores(List<Professor> professores) {
		this.professores = professores;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disciplina that = (Disciplina) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
