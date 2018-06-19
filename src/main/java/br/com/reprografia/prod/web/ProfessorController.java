package br.com.reprografia.prod.web;

import br.com.reprografia.prod.common.enumerator.BusinessExceptionEnum;
import br.com.reprografia.prod.common.enumerator.RoleEnum;
import br.com.reprografia.prod.common.enumerator.StatusEnum;
import br.com.reprografia.prod.common.exception.BusinessException;
import br.com.reprografia.prod.common.util.Constantes;
import br.com.reprografia.prod.common.util.FileUtil;
import br.com.reprografia.prod.model.entity.ProfessorDisciplina;
import br.com.reprografia.prod.model.entity.Requisicao;
import br.com.reprografia.prod.service.business.ProfessorDisciplinaBusiness;
import br.com.reprografia.prod.service.business.RequisicaoBusiness;
import br.com.reprografia.prod.service.security.anotacoes.Privado;
import br.com.reprografia.prod.web.util.SuperController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping(value = "/professor")
public class ProfessorController extends SuperController {


    @Autowired
    RequisicaoBusiness requisicaoBusiness;

    @Autowired
    ProfessorDisciplinaBusiness professorDisciplinaBusiness;

    @Privado(role=RoleEnum.ROLE_PROFESSOR)
    @RequestMapping(
            value="/requisicao/cadastrar",
            method=RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = {"multipart/form-data"}
            )
    public Requisicao cadastrarRequisicao(@RequestPart("meta-part")@Valid Requisicao requisicao,
                                          @RequestPart(name = "arquivo-part", required = false) MultipartFile arquivo) {
        if (arquivo == null) {
            throw new BusinessException(BusinessExceptionEnum.ARQUIVO_INEXISTENTE);
        }
        try {
            requisicao.setArquivo(arquivo.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        requisicao.setNomeArquivo(arquivo.getOriginalFilename());
        if(arquivo.getSize()> Constantes.TAMANHO_LIMITE_ANEXO){
            throw new BusinessException(BusinessExceptionEnum.ANEXO_TAMANHO_NAO_SUPORTADO);
        }
        requisicao.setDataAbertura(Calendar.getInstance());
        requisicao.setTamanhoArquivo(arquivo.getSize());

        String mimeType = FileUtil.getMimeType(requisicao.getArquivo());
        List<String> tiposSuportados = Arrays.asList(Constantes.MIMETYPE.JPG, Constantes.MIMETYPE.JPGE, Constantes.MIMETYPE.PNG, Constantes.MIMETYPE.TXT, Constantes.MIMETYPE.PDF, Constantes.MIMETYPE.BMP, Constantes.MIMETYPE.XLS,
                Constantes.MIMETYPE.XLSX, Constantes.MIMETYPE.DOC, Constantes.MIMETYPE.DOCX, Constantes.MIMETYPE.TIFF);
        if(!tiposSuportados.contains(mimeType)){
            throw new BusinessException(BusinessExceptionEnum.ANEXO_EXTENSAO_NAO_SUPORTADA);
        }

        return requisicaoBusiness.cadastrarRequisicao(requisicao);
    }
    @Privado(role=RoleEnum.ROLE_PROFESSOR)
    @RequestMapping(
            value="/requisicao/alterar",
            method=RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = {"multipart/form-data"}
            )
    public Requisicao alterarRequisicao(@RequestPart("meta-part")@Valid Requisicao requisicao, @RequestPart(name = "arquivo-part", required = false) MultipartFile arquivo) {
        if (arquivo == null) {
            throw new BusinessException(BusinessExceptionEnum.ARQUIVO_INEXISTENTE);
        }
        try {
            requisicao.setArquivo(arquivo.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        requisicao.setNomeArquivo(arquivo.getOriginalFilename());
        if(arquivo.getSize()> Constantes.TAMANHO_LIMITE_ANEXO){
            throw new BusinessException(BusinessExceptionEnum.ANEXO_TAMANHO_NAO_SUPORTADO);
        }
        requisicao.setDataAbertura(Calendar.getInstance());
        requisicao.setTamanhoArquivo(arquivo.getSize());

        String mimeType = FileUtil.getMimeType(requisicao.getArquivo());
        List<String> tiposSuportados = Arrays.asList(Constantes.MIMETYPE.JPG, Constantes.MIMETYPE.JPGE, Constantes.MIMETYPE.PNG, Constantes.MIMETYPE.TXT, Constantes.MIMETYPE.PDF, Constantes.MIMETYPE.BMP, Constantes.MIMETYPE.XLS,
                Constantes.MIMETYPE.XLSX, Constantes.MIMETYPE.DOC, Constantes.MIMETYPE.DOCX, Constantes.MIMETYPE.TIFF);
        if(!tiposSuportados.contains(mimeType)){
            throw new BusinessException(BusinessExceptionEnum.ANEXO_EXTENSAO_NAO_SUPORTADA);
        }

        return requisicaoBusiness.alteraRequisicaoPeloProfessor(requisicao);
    }


    @Privado(role=RoleEnum.ROLE_GERAL)
    @RequestMapping(
            path = "/requisicao/anexo/{id}",
            method = RequestMethod.GET,
            produces = { Constantes.MIMETYPE.JPG, Constantes.MIMETYPE.JPGE, Constantes.MIMETYPE.PNG, Constantes.MIMETYPE.TXT,
                Constantes.MIMETYPE.PDF, Constantes.MIMETYPE.BMP, Constantes.MIMETYPE.XLS, Constantes.MIMETYPE.XLSX,
                Constantes.MIMETYPE.DOC, Constantes.MIMETYPE.DOCX, Constantes.MIMETYPE.TIFF, MediaType.APPLICATION_JSON_VALUE })
    public byte[] obterAnexoInspecao(@PathVariable Long id){
        byte[] anexoAsByte = requisicaoBusiness.downloadAnexo(id);
        return anexoAsByte;
    }

    @Privado(role=RoleEnum.ROLE_PROFESSOR)
    @RequestMapping(
            value="/requisicao/listar",
            method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Requisicao> listarRequisicao(@RequestHeader("Authorization") String token) {

        return requisicaoBusiness.listarTodos(token);
    }

    @Privado(role=RoleEnum.ROLE_PROFESSOR)
    @RequestMapping(
            value="/requisicao/listar/porstatus",
            method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Requisicao> listarRequisicaoPorStatus(@RequestHeader("Authorization") String token, @RequestParam("idStatus") Integer idStatus) {
        return requisicaoBusiness.listarTodoPorStatusEProfessor(token, idStatus);
    }

    @Privado(role=RoleEnum.ROLE_PROFESSOR)
    @RequestMapping(
            value="/professordisciplina/listarporprofessor",
            method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProfessorDisciplina> listarProfessorDisciplina(@RequestHeader("Authorization") String token) {
        return professorDisciplinaBusiness.listarPorProfessor(token);
    }

}
