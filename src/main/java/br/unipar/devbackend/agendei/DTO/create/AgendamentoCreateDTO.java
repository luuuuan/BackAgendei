package br.unipar.devbackend.agendei.DTO.create;


import br.unipar.devbackend.agendei.enums.StatusAgendamento;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class AgendamentoCreateDTO {



    @NotNull(message = "Data do agendamento deve ser informada!")
    private LocalDate dataAgendamento;

    private LocalDate dataCriacao;

    @NotNull(message = "Selecione um horário")
    private LocalTime horaInicio;

    private LocalTime horaFim;

    private LocalDate dataConfirmacao;

    @NotNull(message = "Status não definido")
    private StatusAgendamento statusAgendamento;

    private BigDecimal taxaPlataforma;

    private BigDecimal valorTotal;

    private String observacoes;

    private Long usuarioId;

    private Long profissionalId;

    private List<Long> servicos;

    private Long enderecoId;
}
