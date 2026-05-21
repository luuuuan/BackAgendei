package br.unipar.devbackend.agendei.controller;


import br.unipar.devbackend.agendei.DTO.create.FolgaCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.FolgaResponseDTO;
import br.unipar.devbackend.agendei.entity.GradeTrabalho;
import br.unipar.devbackend.agendei.service.FolgaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/folga")
public class FolgaController {

    @Autowired
    private FolgaService folgaService;

    @PostMapping("/cadastrar")
    public ResponseEntity<FolgaResponseDTO> cadastroFolga(
            @Valid @RequestBody FolgaCreateDTO folgaCreateDTO){
        FolgaResponseDTO folgaResponseDTO = folgaService.criarFolga(folgaCreateDTO);

        return ResponseEntity.ok(folgaResponseDTO);
    }

    @GetMapping("/profissional/{profissionalId}")
    public ResponseEntity<List<FolgaResponseDTO>> folgaProfissional(
            @PathVariable Long profissionalId){
        List<FolgaResponseDTO> listaFolga = folgaService.buscaFolga(profissionalId);

        return ResponseEntity.ok(listaFolga);
    }

    @PatchMapping("/desativar/{id}")
    public ResponseEntity<?> excluirFolga(
            @PathVariable Long id)
            {
        folgaService.atualizaFolga(id);

        return ResponseEntity.ok(Map.of("mensagem", "Folga atualizada!"));
    }


}
