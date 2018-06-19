package br.com.reprografia.prod.web;

import br.com.reprografia.prod.common.enumerator.RoleEnum;
import br.com.reprografia.prod.common.enumerator.StatusEnum;
import br.com.reprografia.prod.model.entity.Andamento;
import br.com.reprografia.prod.model.entity.Disciplina;
import br.com.reprografia.prod.model.entity.ProfessorDisciplina;
import br.com.reprografia.prod.model.entity.Requisicao;
import br.com.reprografia.prod.service.business.ProfessorDisciplinaBusiness;
import br.com.reprografia.prod.service.business.RequisicaoBusiness;
import br.com.reprografia.prod.service.security.anotacoes.Privado;
import br.com.reprografia.prod.web.util.SuperController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/geral")
public class GeralController extends SuperController {

    @Autowired
    RequisicaoBusiness requisicaoBusiness;

    @Privado(role={
            RoleEnum.ROLE_REPROGRAFIA,
            RoleEnum.ROLE_COORDENADOR,
            RoleEnum.ROLE_GERENTE,
            RoleEnum.ROLE_DIRETOR})
    @RequestMapping(
            value="/requisicao/listar/porstatus",
            method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Requisicao> listarRequisicaoPorStatus(@RequestParam("idStatus") Integer idStatus) {
        return requisicaoBusiness.listarTodoPorStatus(idStatus);
    }

    @Privado(role={RoleEnum.ROLE_REPROGRAFIA, RoleEnum.ROLE_COORDENADOR, RoleEnum.ROLE_PROFESSOR})
    @RequestMapping(
            value="/requisicao/alterar",
            method=RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Requisicao alterarAndamento(@RequestHeader("Authorization") String token,
                                       @RequestBody Andamento andamento){
        return requisicaoBusiness.alterarStatusRequisicao(token, andamento);
    }

    @Privado(role={RoleEnum.ROLE_GERAL})
    @RequestMapping(
            value="/requisicao/obter",
            method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Requisicao obterRequisicao(@RequestParam("id")Long id){
        return requisicaoBusiness.obter(id);
    }

}
