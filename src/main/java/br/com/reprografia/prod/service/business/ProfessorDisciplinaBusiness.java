package br.com.reprografia.prod.service.business;

import br.com.reprografia.prod.common.enumerator.BusinessExceptionEnum;
import br.com.reprografia.prod.common.exception.BusinessException;
import br.com.reprografia.prod.common.util.Validator;
import br.com.reprografia.prod.model.entity.*;
import br.com.reprografia.prod.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProfessorDisciplinaBusiness {

    @Autowired
    ProfessorDisciplinaRepository professorDisciplinaRepository;

    @Autowired
    ProfessorRepository professorRepository;

    @Autowired
    DisciplinaRepository disciplinaRepository;

    @Autowired
    RequisicaoRepository requisicaoRepository;

    @Autowired
    SegurancaRepository segurancaRepository;

    public List<ProfessorDisciplina> listarTodos() {
        return professorDisciplinaRepository.findAll();
    }

    @Transactional
    public ProfessorDisciplina associarProfessorDisciplina(ProfessorDisciplina professorDisciplina) {
        if(validarDadosPreenchidos(professorDisciplina)){
            Professor professor = professorRepository.findProfessorById(professorDisciplina.getProfessor().getId());
            Disciplina disciplina = disciplinaRepository.findDisciplinaById(professorDisciplina.getDisciplina().getId());
            if(!Validator.isNullOrEmpty(professor) && !Validator.isNullOrEmpty(disciplina)){
                return professorDisciplinaRepository.save(professorDisciplina);
            }
            throw new BusinessException(BusinessExceptionEnum.ID_NAO_ENCONTRADO);
        }
        throw new BusinessException(BusinessExceptionEnum.DADOS_NAO_PREENCHIDOS);
    }
    @Transactional
    public void desassociarProfessorDisciplina(ProfessorDisciplina professorDisciplina) {
        if(!Validator.isNullOrEmpty(professorDisciplina.getId())){
           List<Requisicao> requisicoes = requisicaoRepository.findRequisicaoByProfessorDisciplina(professorDisciplina.getId());
           ProfessorDisciplina professorDisciplinaBusca = professorDisciplinaRepository.findProfessorDisciplinaById(professorDisciplina.getId());
           if(Validator.isNullOrEmpty(professorDisciplinaBusca)){
               throw new BusinessException(BusinessExceptionEnum.ID_NAO_ENCONTRADO);
           }
            if(requisicoes.size()==0){
                professorDisciplinaRepository.deleteById(professorDisciplina.getId());
                return;
            } else {
                throw new BusinessException(BusinessExceptionEnum.PROFESSOR_POSSUI_REQUISICOES);
            }
        }
        throw new BusinessException(BusinessExceptionEnum.DADOS_NAO_PREENCHIDOS);
    }

    private boolean validarDadosPreenchidos(ProfessorDisciplina professorDisciplina) {
        if (!Validator.isNullOrEmpty(professorDisciplina.getProfessor()) && !Validator.isNullOrEmpty(professorDisciplina.getDisciplina())) {
            if(Validator.isNullOrEmpty(professorDisciplina.getProfessor().getId())){
                return false;
            }
            if(Validator.isNullOrEmpty(professorDisciplina.getDisciplina().getId())){
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    public List<ProfessorDisciplina> listarPorProfessor(String token) {
        SegurancaAPI segurancaAPI = segurancaRepository.findByToken(token.split(" ")[1]);
        List<ProfessorDisciplina> minhasDisciplinas = new ArrayList<>();
        if (segurancaAPI.getPessoa() instanceof Professor) {
            Professor professor = (Professor) segurancaAPI.getPessoa();
            List<ProfessorDisciplina> requisicoes = professorDisciplinaRepository.findAll();

            for (ProfessorDisciplina professorDisciplina : requisicoes) {
                if (professorDisciplina.getProfessor().equals(professor)) {
                    minhasDisciplinas.add(professorDisciplina);
                }
            }
        }
        if(minhasDisciplinas.size()==0){
            throw new BusinessException(BusinessExceptionEnum.REGISTROS_INEXISTENTES);
        } else {
            return minhasDisciplinas;
        }
    }
}
