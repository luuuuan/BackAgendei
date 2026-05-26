package br.unipar.devbackend.agendei.DTO.response;

import br.unipar.devbackend.agendei.enums.FormaPgto;
import br.unipar.devbackend.agendei.enums.StatusPagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoResponseDTO {
    private Long id;

    private BigDecimal valor;

    private StatusPagamento status;

    private FormaPgto metodo;

    private String idTransacaoStripe;

    private LocalDateTime dataPagamento;

    private LocalDateTime dataReembolso;

    private Long agendamentoId;
}
