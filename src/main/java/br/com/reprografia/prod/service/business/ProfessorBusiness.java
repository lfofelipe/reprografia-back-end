package br.com.reprografia.prod.service.business;

import br.com.reprografia.prod.model.entity.Professor;
import br.com.reprografia.prod.model.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProfessorBusiness {
    @Autowired
    ProfessorRepository professorRepository;


    public List<Professor> listarTodos() {
        return professorRepository.findAll();
    }
}
