package br.unipar.devbackend.agendei.controller;


import br.unipar.devbackend.agendei.DTO.create.AvaliacaoCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.AgendamentoResponseDTO;
import br.unipar.devbackend.agendei.DTO.response.AvaliacaoResponseDTO;
import br.unipar.devbackend.agendei.service.AvaliacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<AvaliacaoResponseDTO> avaliarAgendamento(
          @Valid @RequestBody AvaliacaoCreateDTO avaliacaoCreateDTO){
        AvaliacaoResponseDTO avaliacaoResponseDTO = avaliacaoService.avaliarServico(avaliacaoCreateDTO);

        return ResponseEntity.ok(avaliacaoResponseDTO);
    }

}
