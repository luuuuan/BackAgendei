package br.unipar.devbackend.agendei.DTO.response;

import br.unipar.devbackend.agendei.enums.StatusHorario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class HorarioDisponivelResponseDTO {
    private Long id;

    private LocalDate data;

    private LocalTime horaInicio;

    private LocalTime horaFim;

    private StatusHorario statusHorario;

    private Long profissionalId;

    private  Long servicoId;

    //private StatusHorario statusHorario;
}
