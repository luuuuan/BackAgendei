package br.unipar.devbackend.agendei.controller;

import br.unipar.devbackend.agendei.DTO.create.PagamentoConfirmaCreateDTO;
import br.unipar.devbackend.agendei.DTO.create.PagamentoIntentCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.PagamentoIntentResponseDTO;
import br.unipar.devbackend.agendei.DTO.response.PagamentoResponseDTO;
import br.unipar.devbackend.agendei.service.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {
    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping("/criar-intent")
    public ResponseEntity<PagamentoIntentResponseDTO> criarPagamento(
            @Valid @RequestBody PagamentoIntentCreateDTO pagamentoIntentCreateDTO){

        PagamentoIntentResponseDTO pagamentoIntentResponseDTO = pagamentoService.criarIntent(pagamentoIntentCreateDTO);

        return ResponseEntity.ok(pagamentoIntentResponseDTO);

    }

    @PostMapping("/confirmar")
    public ResponseEntity<PagamentoResponseDTO> confirmarPagamento(
            @Valid @RequestBody PagamentoConfirmaCreateDTO pagamentoConfirmaCreateDTO){

        PagamentoResponseDTO pagamentoResponseDTO = pagamentoService.confirmarPagamento(pagamentoConfirmaCreateDTO);

        return ResponseEntity.ok(pagamentoResponseDTO);
    }

    @PostMapping("/reembolso/{agendamentoId}")
    public ResponseEntity<PagamentoResponseDTO> reembolsoAgendamento(
            @PathVariable Long agendamentoId){

        PagamentoResponseDTO pagamentoResponseDTO = pagamentoService.reembolsoAgendamento(agendamentoId);

        return ResponseEntity.ok(pagamentoResponseDTO);
    }

    @GetMapping("/comprovante/{agendamentoId}")
    public ResponseEntity<byte[]> comprovante(
            @PathVariable Long agendamentoId){
        byte[] pdf = pagamentoService.gerarComprovante(agendamentoId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=comprovante-pagamento.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
