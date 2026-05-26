package br.unipar.devbackend.agendei.DTO.create;

import br.unipar.devbackend.agendei.enums.FormaPgto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoConfirmaCreateDTO {
    private Long agendamentoId;

    private String paymentIntentId;

    private BigDecimal valor;

    private FormaPgto formaPgto;
}
