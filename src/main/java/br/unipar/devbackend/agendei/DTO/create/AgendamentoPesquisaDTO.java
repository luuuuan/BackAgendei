package br.unipar.devbackend.agendei.DTO.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgendamentoPesquisaDTO {

    private LocalDate dataAgendamento;

    private LocalDate dataCriacao;

    private LocalTime horaInicio;

    private LocalTime horaFim;

    private String statusAgendamento;

    private BigDecimal valorTotal;

    private String observacoes;

    private Long usuarioId;

    private Long profissionalId;

    private List<Long> servicoId;;
}
