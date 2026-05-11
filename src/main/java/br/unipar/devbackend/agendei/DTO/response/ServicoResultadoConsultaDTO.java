package br.unipar.devbackend.agendei.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter

public class ServicoResultadoConsultaDTO {

    private Integer duracaoMinutos;

    private BigDecimal valor;

    private Long profissionalId;

    private String nome;

    private String descricao;
}
