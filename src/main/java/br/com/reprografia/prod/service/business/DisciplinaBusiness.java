package br.com.reprografia.prod.service.business;


import br.com.reprografia.prod.common.enumerator.BusinessExceptionEnum;
import br.com.reprografia.prod.common.exception.BusinessException;
import br.com.reprografia.prod.common.util.Validator;
import br.com.reprografia.prod.model.entity.Disciplina;
import br.com.reprografia.prod.model.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DisciplinaBusiness {

    @Autowired
    DisciplinaRepository disciplinaRepository;

    public List<Disciplina> listarTodos() {
        return disciplinaRepository.findAll();
    }

    public Disciplina cadastrarDisciplina(Disciplina disciplina) {
        if(validarDadosPreenchidos(disciplina)){
            return disciplinaRepository.save(disciplina);
        }
        throw new BusinessException(BusinessExceptionEnum.DADOS_NAO_PREENCHIDOS);
    }
    public Disciplina alterarDisciplina(Disciplina disciplina) {
        if(validarDadosPreenchidos(disciplina) && !Validator.isNullOrEmpty(disciplina.getId())){

            Disciplina buscaDisciplina = disciplinaRepository.findDisciplinaById(disciplina.getId());
            if(!Validator.isNullOrEmpty(buscaDisciplina)){
                return disciplinaRepository.save(disciplina);
            }
            throw new BusinessException(BusinessExceptionEnum.ID_NAO_ENCONTRADO);
        }
        throw new BusinessException(BusinessExceptionEnum.DADOS_NAO_PREENCHIDOS);
    }

    public Disciplina inativar(Long id) {
        Disciplina disciplina = disciplinaRepository.findDisciplinaById(id);
        if(!Validator.isNullOrEmpty(disciplina)){
            disciplina.setAtivo(false);
            return disciplinaRepository.save(disciplina);
        } else {
            throw new BusinessException(BusinessExceptionEnum.ID_NAO_ENCONTRADO);
        }
    }

    public Disciplina ativar(Long id) {
        Disciplina disciplina = disciplinaRepository.findDisciplinaById(id);
        if(!Validator.isNullOrEmpty(disciplina)){
            disciplina.setAtivo(true);
            return disciplinaRepository.save(disciplina);
        } else {
            throw new BusinessException(BusinessExceptionEnum.ID_NAO_ENCONTRADO);
        }
    }

    private boolean validarDadosPreenchidos(Disciplina disciplina) {
        if(Validator.isNullOrEmpty(disciplina.getDescricao())) {
            return false;
        }
        if(Validator.isNullOrEmpty(disciplina.getSegmento())) {
            return false;
        }
        if(Validator.isNullOrEmpty(disciplina.getSerie())) {
            return false;
        }
        if(Validator.isNullOrEmpty(disciplina.getAtivo())) {
            return false;
        }
        return true;
    }
}
