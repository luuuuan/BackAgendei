package br.unipar.devbackend.agendei.controller;

import br.unipar.devbackend.agendei.DTO.create.PreferenciaNotificacaoCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.PreferenciaNotificacaoResponseDTO;
import br.unipar.devbackend.agendei.entity.PreferenciaNotificacao;
import br.unipar.devbackend.agendei.service.PreferenciaNotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/preferenciasNotificacao")
public class PreferenciaNotificacaoController {
    @Autowired
    private PreferenciaNotificacaoService preferenciaNotificacaoService;



    @PostMapping("/usuario/{id}")
    public ResponseEntity<PreferenciaNotificacaoResponseDTO> notificacoes(
            @RequestBody PreferenciaNotificacaoCreateDTO preferenciaNotificacaoCreateDTO){

        PreferenciaNotificacaoResponseDTO preferenciaNotificacaoResponseDTO =
                preferenciaNotificacaoService.salvarPreferencias(preferenciaNotificacaoCreateDTO);

        return ResponseEntity.ok(preferenciaNotificacaoResponseDTO);
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<PreferenciaNotificacaoResponseDTO> dadosNotificacoes(
            @PathVariable Long id){

        PreferenciaNotificacaoResponseDTO dto = preferenciaNotificacaoService.minhasPreferencias(id);

        return ResponseEntity.ok(dto);
    }

}
