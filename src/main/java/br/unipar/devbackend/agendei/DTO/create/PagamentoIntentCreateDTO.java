package br.unipar.devbackend.agendei.DTO.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PagamentoIntentCreateDTO {
    private Long agendamentoId;
    private BigDecimal valor;
    private String clientSecret;
}
