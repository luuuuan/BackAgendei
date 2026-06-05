package br.unipar.devbackend.agendei.controller;

import br.unipar.devbackend.agendei.DTO.create.PreferenciaNotificacaoCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.PreferenciaNotificacaoResponseDTO;
import br.unipar.devbackend.agendei.service.PreferenciaNotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/preferenciasNotificacao")
public class PreferenciaNotificacaoController {
    @Autowired
    private PreferenciaNotificacaoService preferenciaNotificacaoService;



    @PostMapping("/usuario/{id}")
    public ResponseEntity<PreferenciaNotificacaoCreateDTO> notificacoes(
            @RequestBody PreferenciaNotificacaoCreateDTO preferenciaNotificacaoCreateDTO){

        PreferenciaNotificacaoResponseDTO preferenciaNotificacaoResponseDTO =
                preferenciaNotificacaoService.salvarPreferencias(preferenciaNotificacaoCreateDTO);

        return ResponseEntity.ok(preferenciaNotificacaoResponseDTO);
    }

}
