package br.com.reprografia.prod.service.business;

import br.com.reprografia.prod.common.enumerator.BusinessExceptionEnum;
import br.com.reprografia.prod.common.enumerator.StatusEnum;
import br.com.reprografia.prod.common.exception.BusinessException;
import br.com.reprografia.prod.common.util.Validator;
import br.com.reprografia.prod.model.entity.*;
import br.com.reprografia.prod.model.repository.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class RequisicaoBusiness {
    private final int EM_ELABORACAO = 1;
    private final int EM_AVALIACAO = 2;
    private final int APROVADA = 3;
    private final int EM_IMPRESSAO = 4;
    private final int CANCELADA = 5;
    private final int CONCLUIDA = 6;
    @Autowired
    RequisicaoRepository requisicaoRepository;

    @Autowired
    ProfessorDisciplinaRepository professorDisciplinaRepository;

    @Autowired
    CentroDeCustoRepository centroDeCustoRepository;

    @Autowired
    AndamentoRepository andamentoRepository;

    @Autowired
    SegurancaRepository segurancaRepository;

    @Transactional
    public Requisicao cadastrarRequisicao(Requisicao requisicao) {
        if (validarDadosPreenchidos(requisicao)) {
            Long idProfessorDisciplina = requisicao.getProfessorDisciplina().getId();
            ProfessorDisciplina professorDisciplina = professorDisciplinaRepository.findProfessorDisciplinaById(idProfessorDisciplina);
            return getRequisicao(requisicao, professorDisciplina);
        } else {
            throw new BusinessException(BusinessExceptionEnum.DADOS_NAO_PREENCHIDOS);
        }
    }
    public Requisicao alterarStatusRequisicao(String token, Andamento andamentoParam) {
        SegurancaAPI segurancaAPI = segurancaRepository.findByToken(token.split(" ")[1]);
        if (!Validator.isNullOrEmpty(segurancaAPI)){
            if (!Validator.isNullOrEmpty(andamentoParam.getRequisicao().getId())) {
                Requisicao requisicaoBusca = requisicaoRepository.findRequisicaoById(andamentoParam.getRequisicao().getId());

                if (!Validator.isNullOrEmpty(requisicaoBusca)) {
                    Andamento andamento = new Andamento();
                    andamento.setPessoa(segurancaAPI.getPessoa());
                    switch (andamentoParam.getStatus().getId().intValue()){
                        case EM_ELABORACAO:{
                            andamento.setStatus(new Status(StatusEnum.EM_ELABORACAO));
                            break;
                        }
                        case EM_AVALIACAO:{
                            andamento.setStatus(new Status(StatusEnum.EM_AVALIACAO));
                            break;
                        }
                        case APROVADA:{
                            andamento.setStatus(new Status(StatusEnum.APROVADA));
                            break;
                        }
                        case EM_IMPRESSAO:{
                            andamento.setStatus(new Status(StatusEnum.EM_IMPRESSAO));
                            break;
                        }
                        case CANCELADA:{
                            andamento.setStatus(new Status(StatusEnum.CANCELADA));
                            break;
                        }
                        case CONCLUIDA:{
                            CentroDeCusto centroDeCusto = centroDeCustoRepository.findCentroDeCustoVigente(Calendar.getInstance());
                            if(!Validator.isNullOrEmpty(centroDeCusto)){
                                requisicaoBusca.setCentroDeCusto(centroDeCusto);
                                requisicaoBusca.setCustoDaRequisicao(calculaCusto(centroDeCusto, requisicaoBusca));
                            }
                            requisicaoBusca.setDataConclusao(Calendar.getInstance());
                            andamento.setStatus(new Status(StatusEnum.CONCLUIDA));
                            break;
                        }
                        default:{
                            throw new BusinessException(BusinessExceptionEnum.DADOS_NAO_PREENCHIDOS);
                        }
                    }
                    andamento.setData(Calendar.getInstance());
                    if(!Validator.isNullOrEmpty(andamentoParam.getObservacao())){
                        andamento.setObservacao(andamentoParam.getObservacao());
                    }
                    andamento.setRequisicao(requisicaoBusca);
                    andamentoRepository.save(andamento);
                    requisicaoRepository.save(requisicaoBusca);
                    requisicaoBusca = requisicaoRepository.findRequisicaoById(requisicaoBusca.getId());

                    getRequisicaoWithStatusVigente(requisicaoBusca);

                    return requisicaoBusca;

                } else {
                    throw new BusinessException(BusinessExceptionEnum.ID_NAO_ENCONTRADO);
                }
            }
        }
        throw new BusinessException(BusinessExceptionEnum.ID_NAO_ENCONTRADO);
    }

    private BigDecimal calculaCusto(CentroDeCusto centroDeCusto, Requisicao requisicao) {
        BigDecimal valorTotal = new BigDecimal(0);
        BigDecimal numeroPaginas = new BigDecimal(requisicao.getNumeroDePaginas());
        BigDecimal numeroDeCopias = new BigDecimal(requisicao.getNumeroDeCopias());
        BigDecimal totalImpressoes = new BigDecimal(0);
        totalImpressoes = numeroDeCopias.multiply(numeroPaginas);
        if(requisicao.getColorida()){
            centroDeCusto.setTotalColoridoImpresso(centroDeCusto.getTotalColoridoImpresso() + totalImpressoes.intValue());
            valorTotal = totalImpressoes.multiply(centroDeCusto.getValorExcColorida());
        }else {
            centroDeCusto.setTotalPretoImpresso(centroDeCusto.getTotalPretoImpresso() + totalImpressoes.intValue());
            if(centroDeCusto.getTotalPretoImpresso() >= centroDeCusto.getCota()){
                valorTotal = totalImpressoes.multiply(centroDeCusto.getValorExcPreta());
            }
        }

        centroDeCustoRepository.save(centroDeCusto);
        return valorTotal;
    }

    public byte[] downloadAnexo(Long id){
        if(!Validator.isNullOrEmpty(id)){
            Requisicao requisicao = requisicaoRepository.findRequisicaoById(id);
            if(!Validator.isNullOrEmpty(requisicao)){
                return lerArquivo(requisicao);
            }
        }
        throw new BusinessException(BusinessExceptionEnum.ID_NAO_ENCONTRADO);
    }

    public Requisicao alteraRequisicaoPeloProfessor(Requisicao requisicao) {
        if (validarDadosPreenchidos(requisicao) && !Validator.isNullOrEmpty(requisicao.getId())) {
            Requisicao requisicaoBusca = requisicaoRepository.findRequisicaoById(requisicao.getId());
            Long idProfessorDisciplina = requisicao.getProfessorDisciplina().getId();
            requisicaoBusca.setNumeroDePaginas(requisicao.getNumeroDePaginas());
            requisicaoBusca.setTamanhoArquivo(requisicao.getTamanhoArquivo());
            requisicaoBusca.setNomeArquivo(requisicao.getNomeArquivo());
            requisicaoBusca.setColorida(requisicao.getColorida());
            requisicaoBusca.setDuplex(requisicao.getDuplex());
            requisicaoBusca.setNumeroDeCopias(requisicao.getNumeroDeCopias());
            requisicaoBusca.setGrampeada(requisicao.getGrampeada());
            requisicaoBusca.setArquivo(requisicao.getArquivo());

            return getRequisicao(requisicaoBusca, requisicaoBusca.getProfessorDisciplina());
        } else {
            throw new BusinessException(BusinessExceptionEnum.DADOS_NAO_PREENCHIDOS);
        }
    }

    private Requisicao getRequisicao(Requisicao requisicao, ProfessorDisciplina professorDisciplina) {
        if (!Validator.isNullOrEmpty(professorDisciplina)) {
            requisicao.setProfessorDisciplina(professorDisciplina);
            requisicao = requisicaoRepository.save(requisicao);
            if (!Validator.isNullOrEmpty(requisicao.getId())) {
                Andamento andamento = new Andamento();
                andamento.setData(Calendar.getInstance());
                andamento.setRequisicao(requisicao);
                andamento.setStatus(new Status(StatusEnum.EM_ELABORACAO));
                andamento.setPessoa(professorDisciplina.getProfessor());
                Andamento andamentoBusca = andamentoRepository.save(andamento);
                if (!Validator.isNullOrEmpty(andamentoBusca.getId())) {
                    requisicao.setAndamentoVigente(andamentoBusca);
                    gravarArquivo(requisicao);
                } else {
                    throw new BusinessException(BusinessExceptionEnum.FALHA_CRIAR_NOVO_REGISTRO);
                }
                return requisicao;
            } else {
                throw new BusinessException(BusinessExceptionEnum.FALHA_CRIAR_NOVO_REGISTRO);
            }
        } else {
            throw new BusinessException(BusinessExceptionEnum.ID_NAO_ENCONTRADO);
        }
    }


    public List<Requisicao> listarTodos(String token) {
        SegurancaAPI segurancaAPI = segurancaRepository.findByToken(token.split(" ")[1]);
        List<Requisicao> minhasRequisicoes = new ArrayList<>();
        if (segurancaAPI.getPessoa() instanceof Professor) {
            Professor professor = (Professor) segurancaAPI.getPessoa();
            List<Requisicao> requisicoes = requisicaoRepository.findAll();

            for (Requisicao requisicao : requisicoes) {
                if (requisicao.getProfessorDisciplina().getProfessor().equals(professor)) {
                    minhasRequisicoes.add(requisicao);
                }
            }
            getRequisicoesWithStatusVigente(minhasRequisicoes);
        }
        if(minhasRequisicoes.size()==0){
            throw new BusinessException(BusinessExceptionEnum.REGISTROS_INEXISTENTES);
        }
        return minhasRequisicoes;
    }
    public List<Requisicao> listarTodoPorStatusEProfessor(String token, int statusEnum) {
        SegurancaAPI segurancaAPI = segurancaRepository.findByToken(token.split(" ")[1]);
        List<Requisicao> minhasRequisicoes = new ArrayList<>();
        if (segurancaAPI.getPessoa() instanceof Professor){
            Professor professor = (Professor) segurancaAPI.getPessoa();
            List<Requisicao> requisicoes = requisicaoRepository.findAll();

            for(Requisicao requisicao: requisicoes){
                if(requisicao.getProfessorDisciplina().getProfessor().equals(professor)){
                    minhasRequisicoes.add(requisicao);
                }
            }
            getRequisicoesWithStatusVigente(minhasRequisicoes);
            if(!Validator.isNullOrEmpty(minhasRequisicoes) || minhasRequisicoes.size()>0){
                for(Requisicao requisicao: minhasRequisicoes){
                    if(requisicao.getAndamentoVigente().getStatus().getId().intValue() != statusEnum){
                        minhasRequisicoes.remove(requisicao);
                    }
                    if(minhasRequisicoes.size()==0){
                        throw new BusinessException(BusinessExceptionEnum.REGISTROS_INEXISTENTES);
                    }
                }
            } else {
                throw new BusinessException(BusinessExceptionEnum.REGISTROS_INEXISTENTES);
            }
        }

        return minhasRequisicoes;
    }
    public List<Requisicao> listarTodoPorStatus(int statusId) {
        if(Validator.isNullOrEmpty(statusId)){
            throw new BusinessException(BusinessExceptionEnum.DADOS_NAO_PREENCHIDOS);
        }
        StatusEnum statusEnum = StatusEnum.getValueById(statusId);
        List<Requisicao> requisicoes = requisicaoRepository.findAll();
        List<Requisicao> porStatus = new ArrayList<>();
        getRequisicoesWithStatusVigente(requisicoes);
        if(!Validator.isNullOrEmpty(requisicoes) || requisicoes.size() != 0){
            for(Requisicao requisicao: requisicoes){
                if(requisicao.getAndamentoVigente().getStatus().getDescricao().equalsIgnoreCase(statusEnum.getDescricao())){
                    porStatus.add(requisicao);
                }
            }
        } else {
            throw new BusinessException(BusinessExceptionEnum.REGISTROS_INEXISTENTES);
        }
        if(Validator.isNullOrEmpty(porStatus) || porStatus.size() == 0){
            throw new BusinessException(BusinessExceptionEnum.REGISTROS_INEXISTENTES);
        }
        return porStatus;
    }

    private void getRequisicoesWithStatusVigente(List<Requisicao> requisicoes) {
        for(Requisicao requisicao: requisicoes){
            Andamento andamentoVigente = null;
            for(Andamento andamento: requisicao.getHistorico()){
                if(Validator.isNullOrEmpty(andamentoVigente)){
                    andamentoVigente = andamento;
                } else {
                    if(andamentoVigente.getId().compareTo(andamento.getId())<0){
                        andamentoVigente = andamento;
                    }
                }
            }
            requisicao.setAndamentoVigente(andamentoVigente);
        }
    }

    private void getRequisicaoWithStatusVigente(Requisicao requisicao){
        Andamento andamentoVigente = null;
        for(Andamento andamento: requisicao.getHistorico()){
            if(Validator.isNullOrEmpty(andamentoVigente)){
                andamentoVigente = andamento;
            } else {
                if(andamentoVigente.getId().compareTo(andamento.getId())<0){
                    andamentoVigente = andamento;
                }
            }
        }
        requisicao.setAndamentoVigente(andamentoVigente);
    }

    private void gravarArquivo(Requisicao requisicao) {
        String path = "";
        String osname = System.getProperty("os.name", "").toLowerCase();
        if (osname.startsWith("linux")) {
            path = "/home/tomcat/arquivos/" + requisicao.getId();
        } else if (osname.startsWith("windows")) {
            path = "c:/arquivos/" + requisicao.getId();
        }
        File file = new File(path);
        try {
            Path pathToFile = Paths.get(path);
            Files.createDirectories(pathToFile.getParent());
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
            FileUtils.writeByteArrayToFile(file, requisicao.getArquivo());
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(1001, path);
        }
    }

    private byte[] lerArquivo(Requisicao requisicao) {
        String path = "";
        String osname = System.getProperty("os.name", "").toLowerCase();
        if (osname.startsWith("linux")) {
            path = "/home/tomcat/arquivos/" + requisicao.getId();
        } else if (osname.startsWith("windows")) {
            path = "c:/arquivos/" + requisicao.getId();
        }
        File file = new File(path);

        if (file.exists()) {
            try {
                return FileUtils.readFileToByteArray(file);
            } catch (IOException e) {
                e.printStackTrace();
                throw new BusinessException(BusinessExceptionEnum.FALHA_RECUPERAR_ARQUIVO);
            }
        } else {
            throw new BusinessException(BusinessExceptionEnum.FALHA_RECUPERAR_ARQUIVO);
        }
    }

    private boolean validarDadosPreenchidos(Requisicao requisicao) {
        if (Validator.isNullOrEmpty(requisicao.getProfessorDisciplina())) {
            return false;
        } else {

            if (Validator.isNullOrEmpty(requisicao.getProfessorDisciplina().getId())) {
                return false;
            }
        }
        if (Validator.isNullOrEmpty(requisicao.getArquivo())) {
            return false;
        }
        if (Validator.isNullOrEmpty(requisicao.getDataAbertura())) {
            return false;
        }
        if (Validator.isNullOrEmpty(requisicao.getNomeArquivo())) {
            return false;
        }
        if (Validator.isNullOrEmpty(requisicao.getNumeroDeCopias())) {
            return false;
        }
        if (Validator.isNullOrEmpty(requisicao.getNumeroDePaginas())) {
            return false;
        }
        if (Validator.isNullOrEmpty(requisicao.getTamanhoArquivo())) {
            return false;
        }
        return true;
    }

    public Requisicao obter(Long id) {
        if(id!=null){
            Requisicao requisicao = requisicaoRepository.findRequisicaoById(id);
            getRequisicaoWithStatusVigente(requisicao);
            return requisicao;
        } else {
            throw new BusinessException(BusinessExceptionEnum.DADOS_NAO_PREENCHIDOS);
        }
    }
    public List<Requisicao> getByMonth(Calendar calendar){

        return null;
    }

    public RelatorioCusto gerarRelatorioCustoMensal(Date data) {
        RelatorioCusto relatorioCusto = new RelatorioCusto();
        Calendar primeiroDia = Calendar.getInstance();
        primeiroDia.setTime(data);
        primeiroDia.set(Calendar.DAY_OF_MONTH, 1);
        primeiroDia.getActualMaximum(Calendar.DAY_OF_MONTH);

        Calendar ultimoDia = Calendar.getInstance();
        ultimoDia.setTime(data);
        ultimoDia.set(Calendar.DAY_OF_MONTH, primeiroDia.getActualMaximum(Calendar.DAY_OF_MONTH));
        List<Requisicao> requisicoes = requisicaoRepository.findByMonth(primeiroDia, ultimoDia);

        if(requisicoes.size()==0){
            throw new BusinessException(BusinessExceptionEnum.REGISTROS_INEXISTENTES);
        } else {


            BigDecimal somatorioCustoTotal = new BigDecimal(0);

            BigDecimal custoTotalImpressoesColoridas = new BigDecimal(0);

            BigDecimal custoTotalImpressoesPretas = new BigDecimal(0);

            Integer quantidadeImpressoesColoridas = 0;

            Integer quantidadeImpressoesPretas = 0;

            for(Requisicao requisicao: requisicoes){
                somatorioCustoTotal = somatorioCustoTotal.add(requisicao.getCustoDaRequisicao());
                if(requisicao.getColorida()){
                    quantidadeImpressoesColoridas = quantidadeImpressoesColoridas + (requisicao.getNumeroDePaginas()*requisicao.getNumeroDeCopias());
                    custoTotalImpressoesColoridas = custoTotalImpressoesColoridas.add(requisicao.getCustoDaRequisicao());
                } else {
                    quantidadeImpressoesPretas = quantidadeImpressoesPretas + (requisicao.getNumeroDePaginas()*requisicao.getNumeroDeCopias());
                    custoTotalImpressoesPretas = custoTotalImpressoesPretas.add(requisicao.getCustoDaRequisicao());
                }
            }

            relatorioCusto.setCustoTotalImpressoesColoridas(custoTotalImpressoesColoridas);
            relatorioCusto.setCustoTotalImpressoesPretas(custoTotalImpressoesPretas);
            relatorioCusto.setQuantidadeImpressoesColoridas(quantidadeImpressoesColoridas);
            relatorioCusto.setQuantidadeImpressoesPretas(quantidadeImpressoesPretas);

            relatorioCusto.setSomatorioCustoTotal(somatorioCustoTotal);

            relatorioCusto.setDataInicio(primeiroDia);
            relatorioCusto.setDataFim(ultimoDia);

            relatorioCusto.setRequisicoes(requisicoes);
        }
        if(relatorioCusto.getRequisicoes().size()==0){
            throw new BusinessException(BusinessExceptionEnum.REGISTROS_INEXISTENTES);
        }
        return relatorioCusto;
    }

    public RelatorioCusto gerarRelatorioCustoPorDisciplina(Disciplina disciplina) {
        RelatorioCusto relatorioCusto = new RelatorioCusto();

        List<Requisicao> requisicoes = requisicaoRepository.findByDisciplina(disciplina);

        geraRelatorio(relatorioCusto, requisicoes);

        return relatorioCusto;
    }
    public RelatorioCusto gerarRelatorioCustoPorProfessor(Professor professor) {
        RelatorioCusto relatorioCusto = new RelatorioCusto();
        List<Requisicao> requisicoes = requisicaoRepository.findByProfessor(professor);

        geraRelatorio(relatorioCusto, requisicoes);

        return relatorioCusto;
    }

    private void geraRelatorio(RelatorioCusto relatorioCusto, List<Requisicao> requisicoes) {
        if(requisicoes.size()==0){
            throw new BusinessException(BusinessExceptionEnum.REGISTROS_INEXISTENTES);
        } else {


            BigDecimal somatorioCustoTotal = new BigDecimal(0);

            BigDecimal custoTotalImpressoesColoridas = new BigDecimal(0);

            BigDecimal custoTotalImpressoesPretas = new BigDecimal(0);

            Integer quantidadeImpressoesColoridas = 0;

            Integer quantidadeImpressoesPretas = 0;

            for(Requisicao requisicao: requisicoes){
                somatorioCustoTotal = somatorioCustoTotal.add(requisicao.getCustoDaRequisicao());
                if(requisicao.getColorida()){
                    quantidadeImpressoesColoridas = quantidadeImpressoesColoridas + (requisicao.getNumeroDePaginas()*requisicao.getNumeroDeCopias());
                    custoTotalImpressoesColoridas = custoTotalImpressoesColoridas.add(requisicao.getCustoDaRequisicao());
                } else {
                    quantidadeImpressoesPretas = quantidadeImpressoesPretas + (requisicao.getNumeroDePaginas()*requisicao.getNumeroDeCopias());
                    custoTotalImpressoesPretas = custoTotalImpressoesPretas.add(requisicao.getCustoDaRequisicao());
                }
            }

            relatorioCusto.setCustoTotalImpressoesColoridas(custoTotalImpressoesColoridas);
            relatorioCusto.setCustoTotalImpressoesPretas(custoTotalImpressoesPretas);
            relatorioCusto.setQuantidadeImpressoesColoridas(quantidadeImpressoesColoridas);
            relatorioCusto.setQuantidadeImpressoesPretas(quantidadeImpressoesPretas);

            relatorioCusto.setSomatorioCustoTotal(somatorioCustoTotal);
            relatorioCusto.setRequisicoes(requisicoes);
        }
    }
}
