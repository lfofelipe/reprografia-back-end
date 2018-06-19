package br.com.reprografia.prod.service.business;

import br.com.reprografia.prod.common.enumerator.BusinessExceptionEnum;
import br.com.reprografia.prod.common.exception.BusinessException;
import br.com.reprografia.prod.common.util.Validator;
import br.com.reprografia.prod.model.entity.CentroDeCusto;
import br.com.reprografia.prod.model.repository.CentroDeCustoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CentroDeCustoBusiness {
    @Autowired
    CentroDeCustoRepository centroDeCustoRepository;

    public CentroDeCusto cadastrarCentroDeCusto(CentroDeCusto centroDeCusto) {
        if(validarDadosPreenchidos(centroDeCusto)){
            return centroDeCustoRepository.save(centroDeCusto);
        }
        throw new BusinessException(BusinessExceptionEnum.DADOS_NAO_PREENCHIDOS);
    }
    public CentroDeCusto alterarCentroDeCusto(CentroDeCusto centroDeCusto) {
        if(validarDadosPreenchidos(centroDeCusto) && !Validator.isNullOrEmpty(centroDeCusto.getId())){
            CentroDeCusto buscaCentroDeCusto = centroDeCustoRepository.findCentroDeCustoById(centroDeCusto.getId());
            if(!Validator.isNullOrEmpty(buscaCentroDeCusto)){
                return centroDeCustoRepository.save(centroDeCusto);
            }
            throw new BusinessException(BusinessExceptionEnum.ID_NAO_ENCONTRADO);
        }
        throw new BusinessException(BusinessExceptionEnum.DADOS_NAO_PREENCHIDOS);
    }
    public List<CentroDeCusto> listarTodos() {
        return centroDeCustoRepository.findAll();
    }
    private boolean validarDadosPreenchidos(CentroDeCusto centroDeCusto) {
        if(Validator.isNullOrEmpty(centroDeCusto.getDataFim())){
            return false;
        }
        if(Validator.isNullOrEmpty(centroDeCusto.getDataInicio())){
            return false;
        }
        if(centroDeCusto.getDataFim().before(centroDeCusto.getDataInicio())){
            return false;
        }
        if(Validator.isNullOrEmpty(centroDeCusto.getCota())){
            return false;
        }
        if(Validator.isNullOrEmpty(centroDeCusto.getCustoFixoColorida())){
            return false;
        }
        if(Validator.isNullOrEmpty(centroDeCusto.getCustoFixoPreta())){
            return false;
        }
        if(Validator.isNullOrEmpty(centroDeCusto.getValorExcColorida())){
            return false;
        }
        if(Validator.isNullOrEmpty(centroDeCusto.getValorExcPreta())){
            return false;
        }
        return true;
    }


}
