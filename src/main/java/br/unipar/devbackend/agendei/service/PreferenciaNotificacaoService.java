package br.unipar.devbackend.agendei.service;

import br.unipar.devbackend.agendei.DTO.create.PreferenciaNotificacaoCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.PreferenciaNotificacaoResponseDTO;
import br.unipar.devbackend.agendei.entity.PreferenciaNotificacao;
import br.unipar.devbackend.agendei.entity.Usuario;
import br.unipar.devbackend.agendei.repository.PreferenciaNotificacaoRepository;
import br.unipar.devbackend.agendei.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreferenciaNotificacaoService {
    @Autowired
    private PreferenciaNotificacaoRepository preferenciaNotificacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public PreferenciaNotificacaoResponseDTO salvarPreferencias(PreferenciaNotificacaoCreateDTO dto){

        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        PreferenciaNotificacao preferencia = new PreferenciaNotificacao();

        preferencia.setUsuario(usuario);
        preferencia.setEmailCancelamento(dto.getEmailCancelamento());
        preferencia.setAntecedenciaLembrete(dto.getAntecedenciaLembrete());
        preferencia.setEmailLembrete(dto.getEmailLembrete());
        preferencia.setEmailConfirmacao(dto.getEmailConfirmacao());
        preferencia.setWhatsAppNotificacao(dto.getWhatAppNotificacao());

        preferenciaNotificacaoRepository.save(preferencia);


        return new PreferenciaNotificacaoResponseDTO(
                preferencia.getId(),
                preferencia.getUsuario() != null ? preferencia.getUsuario().getId() : null,
                preferencia.getEmailConfirmacao(),
                preferencia.getEmailLembrete(),
                preferencia.getEmailCancelamento(),
                preferencia.getWhatsAppNotificacao(),
                preferencia.getAntecedenciaLembrete()
        );
    }

    public PreferenciaNotificacaoResponseDTO minhasPreferencias(Long id){
        PreferenciaNotificacao preferencia = preferenciaNotificacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dados não encontrado"));

        return new PreferenciaNotificacaoResponseDTO(
                preferencia.getId(),
                preferencia.getUsuario() != null ? preferencia.getUsuario().getId() : null,
                preferencia.getEmailConfirmacao(),
                preferencia.getEmailLembrete(),
                preferencia.getEmailCancelamento(),
                preferencia.getWhatsAppNotificacao(),
                preferencia.getAntecedenciaLembrete()
        );
    }

}
