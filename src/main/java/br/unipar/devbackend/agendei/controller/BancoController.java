package br.unipar.devbackend.agendei.controller;

import br.unipar.devbackend.agendei.DTO.response.BancoResponseDTO;
import br.unipar.devbackend.agendei.entity.Banco;
import br.unipar.devbackend.agendei.service.BancoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/banco")
public class BancoController {
    @Autowired
    private BancoService bancoService;

    @GetMapping("listar")
    public ResponseEntity<List<BancoResponseDTO>> findAll() {

        List<BancoResponseDTO> bancos = bancoService.buscarBancos();

        return ResponseEntity.ok(bancos);
    }
}
