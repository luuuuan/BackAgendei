package br.unipar.devbackend.agendei.controller;

import br.unipar.devbackend.agendei.DTO.create.DadosBancariosCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.DadosBancariosResponseDTO;
import br.unipar.devbackend.agendei.service.DadosBancariosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dadosBancarios")
public class DadosBancariosController {

    @Autowired
    private DadosBancariosService dadosBancariosService;


    @PostMapping("/cadastrar")
  public ResponseEntity<DadosBancariosResponseDTO> cadastro(
        @Valid @RequestBody DadosBancariosCreateDTO dadosBancariosCreateDTO){

        DadosBancariosResponseDTO dadosBancariosResponseDTO =
                dadosBancariosService.cadastrarDadosBancarios(dadosBancariosCreateDTO);

        return ResponseEntity.ok(dadosBancariosResponseDTO);
    }

    @PatchMapping("/atualizar/{id}")

    public ResponseEntity<DadosBancariosResponseDTO> atualizar(
            @Valid @RequestBody DadosBancariosCreateDTO dadosBancariosCreateDTO,
            @PathVariable Long id) {

        DadosBancariosResponseDTO dadosBancariosResponseDTO = dadosBancariosService.atualizaConta(dadosBancariosCreateDTO, id);

        return ResponseEntity.ok(dadosBancariosResponseDTO);
    }

    @GetMapping("/prestador/{id}")
    public ResponseEntity<DadosBancariosResponseDTO> prestador(
            @PathVariable Long id
    ){
        DadosBancariosResponseDTO dadosBancariosResponseDTO = dadosBancariosService.buscarConta(id);

        return ResponseEntity.ok(dadosBancariosResponseDTO);
    }


}
