package br.com.reprografia.prod.service.business;

import br.com.reprografia.prod.common.enumerator.BusinessExceptionEnum;
import br.com.reprografia.prod.common.exception.BusinessException;
import br.com.reprografia.prod.common.util.FormatadorUtil;
import br.com.reprografia.prod.common.util.Validator;
import br.com.reprografia.prod.model.entity.Perfil;
import br.com.reprografia.prod.model.entity.Pessoa;
import br.com.reprografia.prod.model.entity.Professor;
import br.com.reprografia.prod.model.repository.PerfilRepository;
import br.com.reprografia.prod.model.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PessoaBusiness {

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    PerfilRepository perfilRepository;


    public Pessoa cadastrarPessoa(Pessoa pessoa) {
        Pessoa pessoaPersisted = null;
        if(validarDadosPreenchidos(pessoa)){
            pessoa.setSenha(FormatadorUtil.encryptMD5(pessoa.getSenha()));
            Perfil perfil = perfilRepository.findPerfilById(pessoa.getPerfil().getId());
            pessoa.setPerfil(perfil);
            try{
                if(perfil.getId() == 2L){
                    Professor professor = new Professor();
                    professor.setPerfil(pessoa.getPerfil());
                    professor.setAtivo(pessoa.getAtivo());
                    professor.setSenha(pessoa.getSenha());
                    professor.setEmail(pessoa.getEmail());
                    professor.setLogin(pessoa.getLogin());
                    professor.setNome(pessoa.getNome());

                    pessoaPersisted =  pessoaRepository.save(professor);
                } else {
                    pessoaPersisted =  pessoaRepository.save(pessoa);
                }

            } catch (DataIntegrityViolationException exception){
                throw new BusinessException(BusinessExceptionEnum.LOGIN_INDISPONIVEL);
            }
            return pessoaPersisted;
        }
        throw new BusinessException(BusinessExceptionEnum.DADOS_NAO_PREENCHIDOS);
    }
    public Pessoa alterarPessoa(Pessoa pessoa) {
        if(validarDadosPreenchidos(pessoa) && !Validator.isNullOrEmpty(pessoa.getId())){
            Pessoa buscaPessoa = pessoaRepository.findPessoaById(pessoa.getId());

            pessoa.setSenha(FormatadorUtil.encryptMD5(pessoa.getSenha()));
            Perfil perfil = perfilRepository.findPerfilById(pessoa.getPerfil().getId());

            buscaPessoa.setPerfil(perfil);
            buscaPessoa.setAtivo(pessoa.getAtivo());
            buscaPessoa.setSenha(pessoa.getSenha());
            buscaPessoa.setEmail(pessoa.getEmail());
            buscaPessoa.setLogin(pessoa.getLogin());
            buscaPessoa.setNome(pessoa.getNome());


            if (!Validator.isNullOrEmpty(buscaPessoa)) {
                return pessoaRepository.save(buscaPessoa);
            }
            throw new BusinessException(BusinessExceptionEnum.ID_NAO_ENCONTRADO);
        }
        throw new BusinessException(BusinessExceptionEnum.DADOS_NAO_PREENCHIDOS);

    }

    public List<Pessoa> listarTodos() {
        return pessoaRepository.findAll();
    }

    public Pessoa inativar(Long id) {
        Pessoa pessoa = pessoaRepository.findPessoaById(id);
        if(!Validator.isNullOrEmpty(pessoa)){
            pessoa.setAtivo(false);
            return pessoaRepository.save(pessoa);
        } else {
            throw new BusinessException(BusinessExceptionEnum.ID_NAO_ENCONTRADO);
        }
    }

    public Pessoa ativar(Long id) {
        Pessoa pessoa = pessoaRepository.findPessoaById(id);
        if(!Validator.isNullOrEmpty(pessoa)){
            pessoa.setAtivo(true);
            return pessoaRepository.save(pessoa);
        } else {
            throw new BusinessException(BusinessExceptionEnum.ID_NAO_ENCONTRADO);
        }
    }

    private boolean validarDadosPreenchidos(Pessoa pessoa) {
        if (pessoa.getPerfil()==null){
            return false;
        } else {
            Perfil perfil = pessoa.getPerfil();

            if(Validator.isNullOrEmpty(perfil.getId())){
                return false;
            }
        }
        if(Validator.isNullOrEmpty(pessoa.getLogin())){
            return false;
        }
        if(Validator.isNullOrEmpty(pessoa.getNome())){
            return false;
        }
        if(Validator.isNullOrEmpty(pessoa.getSenha())){
            return false;
        }
        if(Validator.isNullOrEmpty(pessoa.getEmail())){
            return false;
        }
        return true;
    }
}
