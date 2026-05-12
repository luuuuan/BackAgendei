package br.unipar.devbackend.agendei.DTO.response;

import br.unipar.devbackend.agendei.enums.StatusAgendamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AgendamentoResponseDTO {
    private Long id;

    private LocalDate dataAgendamento;

    private LocalDate dataCriacao;

    private LocalTime horaInicio;

    private LocalTime horaFim;

    private LocalDate dataConfirmacao;

    private StatusAgendamento statusAgendamento;

    private BigDecimal valorTotal;

    private String observacoes;

    private Long usuarioId;

    private Long profissionalId;

    private List<Long> servicoId;

    private Long enderecoId;

    private Long prestadorId;


}
