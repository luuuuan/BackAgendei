package br.unipar.devbackend.agendei.DTO.create;


import br.unipar.devbackend.agendei.entity.Profissional;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FolgaCreateDTO {

    private LocalDate data;

    @NotNull(message = "Profissional deve ser informado")
    private Long profissionalId;

    private Boolean diaInteiro;

    private LocalTime horaInicio;

    private LocalTime horaFim;

    private String motivo;
}
