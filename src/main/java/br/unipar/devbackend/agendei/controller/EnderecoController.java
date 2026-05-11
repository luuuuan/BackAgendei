package br.unipar.devbackend.agendei.controller;


import br.unipar.devbackend.agendei.DTO.create.EnderecoCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.EnderecoResponseDTO;
import br.unipar.devbackend.agendei.service.EnderecoService;
import br.unipar.devbackend.agendei.service.ProfissionalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
