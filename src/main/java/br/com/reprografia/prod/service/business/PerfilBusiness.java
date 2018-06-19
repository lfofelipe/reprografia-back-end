package br.com.reprografia.prod.service.business;

import br.com.reprografia.prod.model.entity.Perfil;
import br.com.reprografia.prod.model.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PerfilBusiness {

    @Autowired
    PerfilRepository perfilRepository;

    public List<Perfil> listarTodos() {
        return perfilRepository.findAll();
    }
}
