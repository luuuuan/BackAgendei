package br.unipar.devbackend.agendei.controller;


import br.unipar.devbackend.agendei.DTO.create.FolgaCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.FolgaResponseDTO;
import br.unipar.devbackend.agendei.entity.Folga;
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
        List<FolgaResponseDTO> listaFolgaProfissional = folgaService.buscaFolgaProfissional(profissionalId);

        return ResponseEntity.ok(listaFolgaProfissional);
    }

    @GetMapping("prestador/{prestadorId}")
    public ResponseEntity<List<FolgaResponseDTO>> folgaPrestador(
            @PathVariable Long prestadorId){
        List<FolgaResponseDTO> listaFolga = folgaService.buscaFolgaPrestador(prestadorId);

        return ResponseEntity.ok(listaFolga);
    }


    @PatchMapping("/desativar/{id}/{data}")
    public ResponseEntity<?> excluirFolga(
            @PathVariable Long id,
            @PathVariable LocalDate data)
            {
        FolgaResponseDTO folgaResponseDTO = folgaService.atualizaFolga(id, data);

        return ResponseEntity.ok(Map.of("mensagem", "Folga atualizada!"));
    }

    @GetMapping("/prestador/{prestadorId}/mes")
    public ResponseEntity<List<FolgaResponseDTO>> folgaMes(
            @PathVariable Long prestadorId,
            @RequestParam String mes){
        List<FolgaResponseDTO> folgasMes = folgaService.buscaFolgaMesPrestador(prestadorId, mes);

        return ResponseEntity.ok(folgasMes);
    }

}
