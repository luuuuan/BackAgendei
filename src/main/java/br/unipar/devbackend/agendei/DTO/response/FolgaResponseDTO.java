package br.unipar.devbackend.agendei.DTO.response;

import br.unipar.devbackend.agendei.entity.Profissional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@AllArgsConstructor
@NoArgsConstructor

public class FolgaResponseDTO {
    private Long id;

    private LocalDate data;

    private Long profissional;

    private Boolean diaInteiro;

    private LocalTime horaInicio;

    private LocalTime horaFim;

    private String motivo;

}
