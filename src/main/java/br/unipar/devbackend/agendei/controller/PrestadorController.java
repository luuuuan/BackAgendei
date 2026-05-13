package br.unipar.devbackend.agendei.controller;

import br.unipar.devbackend.agendei.DTO.create.PrestadorCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.PrestadorResponseDTO;
import br.unipar.devbackend.agendei.repository.PrestadorRepository;
import br.unipar.devbackend.agendei.service.PrestadorService;
import br.unipar.devbackend.agendei.service.ProfissionalService;
import br.unipar.devbackend.agendei.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/prestador")
public class PrestadorController {

    @Autowired
    private PrestadorService prestadorService;

    @Autowired
    private PrestadorRepository prestadorRepository;

    @Autowired
    private ProfissionalService profissionalService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastroPrestador")
    public ResponseEntity<PrestadorResponseDTO> createPrestador(
            @Valid @RequestBody PrestadorCreateDTO prestadorCreateDTO) {
        PrestadorResponseDTO prestadorResponseDTO = prestadorService.cadastroPrestador(prestadorCreateDTO);

        return ResponseEntity.ok(prestadorResponseDTO);
    }



    @PostMapping("/vincular")
    public ResponseEntity<?> vincular(@RequestBody Map<String, Long> body){
        profissionalService.vincularProfissional(body.get("profissionalId"), body.get("prestadorId"));

        return ResponseEntity.ok(Map.of("mensagem", "Profissional vinculado com sucesso"));
    }


}
