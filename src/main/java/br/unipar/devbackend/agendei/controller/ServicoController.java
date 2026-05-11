package br.unipar.devbackend.agendei.controller;

import br.unipar.devbackend.agendei.DTO.create.ServicoCreateDTO;
import br.unipar.devbackend.agendei.DTO.create.UsuarioCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.ServicoResponseDTO;
import br.unipar.devbackend.agendei.DTO.response.ServicoResultadoConsultaDTO;
import br.unipar.devbackend.agendei.DTO.response.UsuarioResponseDTO;
import br.unipar.devbackend.agendei.service.ServicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servico")
public class ServicoController {
    @Autowired
    private ServicoService servicoService;

    @PostMapping("/cadastroServicos")
    public ResponseEntity<ServicoResponseDTO> criar(
            @Valid @RequestBody ServicoCreateDTO servicoCreateDTO){
        ServicoResponseDTO servicoResponseDTO = servicoService.criarServico(servicoCreateDTO);


        return ResponseEntity.ok(servicoResponseDTO);
    }


    @GetMapping("/servicos")
    public ResponseEntity<List<ServicoResponseDTO>> listar(){
        List<ServicoResponseDTO> servicos = servicoService.listarServicos();


        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/servicosProfissional/{id}")
    public ResponseEntity<List<ServicoResultadoConsultaDTO>> listarProfissionais(
            @PathVariable("id") Long profissionalId){
        List<ServicoResultadoConsultaDTO> profissionaisServicos = servicoService.listarServicoProfissional(profissionalId);


        return ResponseEntity.ok(profissionaisServicos);

    }
}
