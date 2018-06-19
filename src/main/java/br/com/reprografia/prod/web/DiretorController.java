package br.com.reprografia.prod.web;

import br.com.reprografia.prod.common.enumerator.BusinessExceptionEnum;
import br.com.reprografia.prod.common.enumerator.RoleEnum;
import br.com.reprografia.prod.common.exception.BusinessException;
import br.com.reprografia.prod.common.util.Validator;
import br.com.reprografia.prod.model.entity.CentroDeCusto;
import br.com.reprografia.prod.model.entity.Disciplina;
import br.com.reprografia.prod.model.entity.RelatorioCusto;
import br.com.reprografia.prod.service.business.CentroDeCustoBusiness;
import br.com.reprografia.prod.service.business.RequisicaoBusiness;
import br.com.reprografia.prod.service.security.anotacoes.Privado;
import br.com.reprografia.prod.web.util.SuperController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/diretor")
public class DiretorController extends SuperController {
        
    @Autowired
    CentroDeCustoBusiness centroDeCustoBusiness;

    @Autowired
    RequisicaoBusiness requisicaoBusiness;

    @Privado(role={RoleEnum.ROLE_DIRETOR})
    @RequestMapping(
            value="/relatorio/custopordisciplina",
            method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RelatorioCusto gerarRelatorioCustoPorDisciplina(@RequestParam("idDisciplina") Long idDisciplina) {
        if(Validator.isNullOrEmpty(idDisciplina)){
            throw new BusinessException(BusinessExceptionEnum.DADOS_NAO_PREENCHIDOS);
        }
        Disciplina disciplina = new Disciplina(idDisciplina);
        return requisicaoBusiness.gerarRelatorioCustoPorDisciplina(disciplina);
    }
}
