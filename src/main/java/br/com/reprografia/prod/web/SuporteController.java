package br.com.reprografia.prod.web;

import br.com.reprografia.prod.common.enumerator.RoleEnum;
import br.com.reprografia.prod.model.entity.*;
import br.com.reprografia.prod.service.business.*;
import br.com.reprografia.prod.service.security.anotacoes.Privado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/suporte")
public class SuporteController {

    @Autowired
    PessoaBusiness pessoaBusiness;

    @Autowired
    PerfilBusiness perfilBusiness;

    @Autowired
    DisciplinaBusiness disciplinaBusiness;

    @Autowired
    ProfessorDisciplinaBusiness professorDisciplinaBusiness;

    @Autowired
    ProfessorBusiness professorBusiness;

    @Privado(role=RoleEnum.ROLE_TI)
    @RequestMapping(value = "/acesso/ti", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String acessoAdmin() {
        return "TI: Acesso Permitido!";
    }

    @Privado(role=RoleEnum.ROLE_TI)
    @RequestMapping(
            value="/pessoa/cadastrar",
            method=RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Pessoa cadastrarPessoa(@RequestBody Pessoa pessoa) {
        return pessoaBusiness.cadastrarPessoa(pessoa);
    }
    @Privado(role=RoleEnum.ROLE_TI)
    @RequestMapping(
            value="/pessoa/alterar",
            method=RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Pessoa alterarPessoa(@RequestBody Pessoa pessoa) {
        return pessoaBusiness.alterarPessoa(pessoa);
    }

    @Privado(role=RoleEnum.ROLE_TI)
    @RequestMapping(
            value="/pessoa/listar",
            method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Pessoa> listarPessoas() {
        return pessoaBusiness.listarTodos();
    }
    @Privado(role=RoleEnum.ROLE_TI)
    @RequestMapping(
            value="/pessoa/{id}/inativar",
            method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Pessoa inativarPessoa(@PathVariable("id") long id) {
        return pessoaBusiness.inativar(id);
    }
    @Privado(role=RoleEnum.ROLE_TI)
    @RequestMapping(
            value="/pessoa/{id}/ativar",
            method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Pessoa ativarPessoa(@PathVariable("id") long id) {
        return pessoaBusiness.ativar(id);
    }

    @Privado(role=RoleEnum.ROLE_TI)
    @RequestMapping(
            value="/perfil/listar",
            method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Perfil> listarPerfis() {
        return perfilBusiness.listarTodos();
    }
    @Privado(role={RoleEnum.ROLE_TI, RoleEnum.ROLE_DIRETOR})
    @RequestMapping(
            value="/disciplina/listar",
            method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Disciplina> listarDisciplinas() {
        return disciplinaBusiness.listarTodos();
    }

    @Privado(role=RoleEnum.ROLE_TI)
    @RequestMapping(
            value="/disciplina/cadastrar",
            method=RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Disciplina cadastrarDisciplina(@RequestBody Disciplina disciplina) {
        return disciplinaBusiness.cadastrarDisciplina(disciplina);
    }

    @Privado(role=RoleEnum.ROLE_TI)
    @RequestMapping(
            value="/disciplina/alterar",
            method=RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Disciplina alterarDisciplina(@RequestBody Disciplina disciplina) {
        return disciplinaBusiness.alterarDisciplina(disciplina);
    }
    @Privado(role=RoleEnum.ROLE_TI)
    @RequestMapping(
            value="/disciplina/{id}/inativar",
            method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Disciplina inativarDisciplina(@PathVariable("id") long id) {
        return disciplinaBusiness.inativar(id);
    }
    @Privado(role=RoleEnum.ROLE_TI)
    @RequestMapping(
            value="/disciplina/{id}/ativar",
            method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Disciplina ativarDisciplina(@PathVariable("id") long id) {
        return disciplinaBusiness.ativar(id);
    }

    @Privado(role={RoleEnum.ROLE_TI, RoleEnum.ROLE_GERENTE, RoleEnum.ROLE_DIRETOR})
    @RequestMapping(
            value="/professor/listar",
            method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Professor> listarProfessor() {
        return professorBusiness.listarTodos();
    }


    @Privado(role=RoleEnum.ROLE_TI)
    @RequestMapping(
            value="/professordisciplina/associar",
            method=RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProfessorDisciplina associarProfessorDisciplina(@RequestBody ProfessorDisciplina professorDisciplina) {
        return professorDisciplinaBusiness.associarProfessorDisciplina(professorDisciplina);
    }
    @Privado(role=RoleEnum.ROLE_TI)
    @RequestMapping(
            value="/professordisciplina/desassociar",
            method=RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void desassociarProfessorDisciplina(@RequestBody ProfessorDisciplina professorDisciplina) {
         professorDisciplinaBusiness.desassociarProfessorDisciplina(professorDisciplina);
    }

    @Privado(role=RoleEnum.ROLE_TI)
    @RequestMapping(
            value="/professordisciplina/listar",
            method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProfessorDisciplina> listarProfessorDisciplina() {
        return professorDisciplinaBusiness.listarTodos();
    }
}
