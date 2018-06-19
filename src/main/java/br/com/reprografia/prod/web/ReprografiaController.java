package br.com.reprografia.prod.web;

import br.com.reprografia.prod.common.enumerator.RoleEnum;
import br.com.reprografia.prod.model.entity.Andamento;
import br.com.reprografia.prod.model.entity.Requisicao;
import br.com.reprografia.prod.service.business.RequisicaoBusiness;
import br.com.reprografia.prod.service.security.anotacoes.Privado;
import br.com.reprografia.prod.web.util.SuperController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/reprografia")
public class ReprografiaController extends SuperController {

    @Autowired
    RequisicaoBusiness requisicaoBusiness;



}
