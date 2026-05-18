package br.unipar.devbackend.agendei.controller;


import br.unipar.devbackend.agendei.DTO.create.FolgaCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.FolgaResponseDTO;
import br.unipar.devbackend.agendei.service.FolgaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/folga")
public class FolgaController {

    @Autowired
    private FolgaService folgaService;

    @PostMapping("/cadastro")
    public ResponseEntity<FolgaResponseDTO> cadastroFolga(
            @Valid @RequestBody FolgaCreateDTO folgaCreateDTO){
        FolgaResponseDTO folgaResponseDTO = folgaService.criarFolga(folgaCreateDTO);

        return ResponseEntity.ok(folgaResponseDTO);
    }

}
