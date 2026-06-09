package br.unipar.devbackend.agendei.controller;


import br.unipar.devbackend.agendei.DTO.create.EnderecoCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.EnderecoResponseDTO;
import br.unipar.devbackend.agendei.service.EnderecoService;
import br.unipar.devbackend.agendei.service.ProfissionalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    private ProfissionalService profissionalService;

    @PostMapping("/enderecoUser")
    public ResponseEntity<EnderecoResponseDTO> endereco(
            @Valid @RequestBody EnderecoCreateDTO enderecoCreateDTO){
        EnderecoResponseDTO enderecoResponseDTO = enderecoService.criarEndereco(enderecoCreateDTO);

        return ResponseEntity.ok(enderecoResponseDTO);
    }

    @GetMapping("/enderecoCadastrado/{enderecoId}")
    public ResponseEntity<EnderecoResponseDTO> enderecoCadastrado(
            @PathVariable Long enderecoId){

        EnderecoResponseDTO enderecoResponseDTO = enderecoService.meuEndereco(enderecoId);
        return ResponseEntity.ok(enderecoResponseDTO);
    }

    @PutMapping("/atualizarEndereco")
    public ResponseEntity<EnderecoResponseDTO> atualizaEndereco(
    @RequestBody EnderecoCreateDTO enderecoCreateDTO){
        EnderecoResponseDTO enderecoResponseDTO = enderecoService.atualizaEndereco(enderecoCreateDTO);

        return ResponseEntity.ok(enderecoResponseDTO);
    }
}
