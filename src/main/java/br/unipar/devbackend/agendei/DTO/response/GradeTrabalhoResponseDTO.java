package br.unipar.devbackend.agendei.DTO.response;

import br.unipar.devbackend.agendei.entity.Profissional;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class GradeTrabalhoResponseDTO {
    private Long id;

    private Long profissionalId;

    private DayOfWeek diaInicio;

    private DayOfWeek diaFim;

    private LocalTime horaInicio;

    private LocalTime horaFim;

    private LocalTime inicioIntervalo;

    private LocalTime fimIntervalo;

    private Long prestadorId;

}
