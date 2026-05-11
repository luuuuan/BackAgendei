package br.unipar.devbackend.agendei.controller;

import br.unipar.devbackend.agendei.DTO.create.CadastroCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.UsuarioLoginResponseDTO;
import br.unipar.devbackend.agendei.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioLoginResponseDTO> cadastrar(
            @Valid @RequestBody CadastroCreateDTO dto) {

        return ResponseEntity.ok(authService.cadastrar(dto));
    }
}
