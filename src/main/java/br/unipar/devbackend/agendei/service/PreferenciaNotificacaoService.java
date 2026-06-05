package br.unipar.devbackend.agendei.service;

import br.unipar.devbackend.agendei.DTO.create.PreferenciaNotificacaoCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.PreferenciaNotificacaoResponseDTO;
import br.unipar.devbackend.agendei.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreferenciaNotificacaoService {
    @Autowired
    private PagamentoRepository pagamentoRepository;

    public PreferenciaNotificacaoResponseDTO salvarPreferencias(PreferenciaNotificacaoCreateDTO dto){

        

        return new PreferenciaNotificacaoResponseDTO();
    }
}
