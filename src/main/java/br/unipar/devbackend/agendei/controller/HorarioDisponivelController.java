package br.unipar.devbackend.agendei.controller;


import br.unipar.devbackend.agendei.DTO.create.HorarioDisponivelDTO;
import br.unipar.devbackend.agendei.DTO.response.HorarioDisponivelResponseDTO;
import br.unipar.devbackend.agendei.service.HorarioDisponivelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/horarioDisponivel")
public class HorarioDisponivelController {

    @Autowired
    private HorarioDisponivelService horarioDisponivelService;

    @GetMapping("/disponibilidade")
    public ResponseEntity<List<String>> disponibilidadeHoras(
            @RequestParam Long profissionalId,
            @RequestParam LocalDate dataAgendamento
            ){
        List<String> horarioDisponivel =
                horarioDisponivelService.disponibilidade(profissionalId, dataAgendamento);

        return ResponseEntity.ok(horarioDisponivel);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<HorarioDisponivelResponseDTO> cadastrarHorario(
            @Valid @RequestBody HorarioDisponivelDTO horarioDisponivelDTO
    ){
        HorarioDisponivelResponseDTO horarioDisponivelResponseDTO =
                horarioDisponivelService.cadastrarHorario(horarioDisponivelDTO);

        return ResponseEntity.ok(horarioDisponivelResponseDTO);
    }
}
