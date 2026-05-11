package br.unipar.devbackend.agendei.controller;

import br.unipar.devbackend.agendei.DTO.create.ProfissionalCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.ProfissionalResponseDTO;
import br.unipar.devbackend.agendei.service.ProfissionalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/profissional")
public class ProfissionalController {

    @Autowired
    private ProfissionalService profissionalService;

    @PostMapping("/cadastroProfissional")
    public ResponseEntity<ProfissionalResponseDTO> criar(
            @Valid @RequestBody ProfissionalCreateDTO profissionalCreateDTO) {
        ProfissionalResponseDTO profissionalResponseDTO = profissionalService.criarProfissional(profissionalCreateDTO);

        return ResponseEntity.ok(profissionalResponseDTO);
    }

    @GetMapping("/profissionaisCadastrados")
    public ResponseEntity<List<ProfissionalResponseDTO>> listar(
            @RequestParam(required = false) Long prestadorId) {

        List<ProfissionalResponseDTO> profissionalResponseDTO =
                profissionalService.listarProfissionais(prestadorId);

        return ResponseEntity.ok(profissionalResponseDTO);
    }

    @GetMapping("/profissionalServico/{servicoId}")
    public ResponseEntity<List<ProfissionalResponseDTO>> listarProfissionalServico(
            @PathVariable Long servicoId){

        List<ProfissionalResponseDTO> profissionalResponseDTO =
                profissionalService.listarProfissionaisServico(servicoId);

        return ResponseEntity.ok(profissionalResponseDTO);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<ProfissionalResponseDTO> buscarPorUsuario(
            @PathVariable Long usuarioId){

        ProfissionalResponseDTO profissionalResponseDTO = profissionalService.buscarPorUsuarioId(usuarioId);

        return ResponseEntity.ok(profissionalResponseDTO);
    }

    @PostMapping("/vincular")
    public ResponseEntity<?> vincular(@RequestBody Map<String, Long> body){
        profissionalService.vincularProfissional(body.get("profissionalId"), body.get("prestadorId"));

        return ResponseEntity.ok(Map.of("mensagem", "Profissional vinculado com sucesso"));
    }
}