package br.unipar.devbackend.agendei.controller;


import br.unipar.devbackend.agendei.DTO.create.AgendamentoCreateDTO;
import br.unipar.devbackend.agendei.DTO.create.AgendamentoPesquisaDTO;
import br.unipar.devbackend.agendei.DTO.response.AgendamentoResponseDTO;
import br.unipar.devbackend.agendei.enums.StatusAgendamento;
import br.unipar.devbackend.agendei.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/agendamento")

public class AgendamentoController {


    @Autowired
    private AgendamentoService agendamentoService;

    @PostMapping("/criarAgendamento")
    public ResponseEntity<AgendamentoResponseDTO> criar(
            @Valid @RequestBody AgendamentoCreateDTO agendamentoCreateDTO) {
        AgendamentoResponseDTO agendamentoResponseDTO = agendamentoService.criarAgendamento(agendamentoCreateDTO);


        return ResponseEntity.ok(agendamentoResponseDTO);
    }

    @GetMapping("/consultaAgendamento")
    public ResponseEntity<List<AgendamentoResponseDTO>> agendamentos(
            @RequestParam LocalDate dataCriacao) {



        AgendamentoPesquisaDTO agendamentoPesquisaDTO = new AgendamentoPesquisaDTO();
        agendamentoPesquisaDTO.setDataCriacao(dataCriacao);

        List<AgendamentoResponseDTO> agendamentoResponseDTO =
                agendamentoService.buscaAgendamento(agendamentoPesquisaDTO);
        
        return ResponseEntity.ok(agendamentoResponseDTO);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<AgendamentoResponseDTO>> todos(
            @PathVariable Long usuarioId) {

        AgendamentoPesquisaDTO agendamentoPesquisaDTO = new AgendamentoPesquisaDTO();
        agendamentoPesquisaDTO.setUsuarioId(usuarioId);

        List<AgendamentoResponseDTO> agendamentoResponseDTO =
                agendamentoService.buscarPorUsuario(agendamentoPesquisaDTO);

        return ResponseEntity.ok(agendamentoResponseDTO);
    }

    @GetMapping("/disponibilidade")
    public ResponseEntity<List<String>> disponibilidade(
            @RequestParam Long profissionalId,
            @RequestParam LocalDate dataAgendamento) {


        List<String> agendamentoResponseDTO =
                agendamentoService.buscarHorariosDisponiveis(profissionalId, dataAgendamento);

        return ResponseEntity.ok(agendamentoResponseDTO);
    }

    @PatchMapping("/atualizar-status/{id}")
    public ResponseEntity<AgendamentoResponseDTO> statusAgendamento(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
            ){
         AgendamentoResponseDTO agendamentoResponseDTO =
                agendamentoService.atualizaStatus(id, body.get("status"));

        return ResponseEntity.ok(agendamentoResponseDTO);
    }

}
