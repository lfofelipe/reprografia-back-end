package br.com.reprografia.prod.web;

import br.com.reprografia.prod.common.enumerator.BusinessExceptionEnum;
import br.com.reprografia.prod.common.enumerator.RoleEnum;
import br.com.reprografia.prod.common.exception.BusinessException;
import br.com.reprografia.prod.common.util.Validator;
import br.com.reprografia.prod.model.entity.CentroDeCusto;
import br.com.reprografia.prod.model.entity.Professor;
import br.com.reprografia.prod.model.entity.RelatorioCusto;
import br.com.reprografia.prod.model.entity.Requisicao;
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
@RequestMapping(value = "/gerente")
public class GerenteController extends SuperController {
        
    @Autowired
    CentroDeCustoBusiness centroDeCustoBusiness;

    @Autowired
    RequisicaoBusiness requisicaoBusiness;

    @Privado(role=RoleEnum.ROLE_GERENTE)
    @RequestMapping(
            value="/centrodecusto/cadastrar",
            method=RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public CentroDeCusto cadastrarCentroDeCusto(@RequestBody CentroDeCusto centroDeCusto) {
        return centroDeCustoBusiness.cadastrarCentroDeCusto(centroDeCusto);
    }
    @Privado(role=RoleEnum.ROLE_GERENTE)
    @RequestMapping(
            value="/centrodecusto/alterar",
            method=RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public CentroDeCusto alterarCentroDeCusto(@RequestBody CentroDeCusto centroDeCusto) {
        return centroDeCustoBusiness.alterarCentroDeCusto(centroDeCusto);
    }

    @Privado(role=RoleEnum.ROLE_GERENTE)
    @RequestMapping(
            value="/centrodecusto/listar",
            method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CentroDeCusto> listarCentroDeCustos() {
        return centroDeCustoBusiness.listarTodos();
    }

    @Privado(role={RoleEnum.ROLE_GERENTE})
    @RequestMapping(
            value="/relatorio/custopormes",
            method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RelatorioCusto gerarRelatorioCustoMensal(@RequestParam("data") Date data) {
        return requisicaoBusiness.gerarRelatorioCustoMensal(data);

    }
    @Privado(role={RoleEnum.ROLE_GERENTE})
    @RequestMapping(
            value="/relatorio/custoporprofessor",
            method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public RelatorioCusto gerarRelatorioCustoPorProfessor(@RequestParam("idProfessor") Long idProfessor) {
        if(Validator.isNullOrEmpty(idProfessor)){
            throw new BusinessException(BusinessExceptionEnum.DADOS_NAO_PREENCHIDOS);
        }
        Professor professor = new Professor(idProfessor);
        return requisicaoBusiness.gerarRelatorioCustoPorProfessor(professor);

    }
}
