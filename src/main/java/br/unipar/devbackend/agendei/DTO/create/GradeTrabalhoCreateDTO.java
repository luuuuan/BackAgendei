package br.unipar.devbackend.agendei.DTO.create;

import br.unipar.devbackend.agendei.entity.Profissional;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class GradeTrabalhoCreateDTO {

    private Long profissionalId;

    private DayOfWeek diaInicio;

    private DayOfWeek diaFim;

    private LocalTime horarioInicio;

    private LocalTime horarioFim;

    private LocalTime inicioIntervalo;

    private LocalTime fimIntervalo;

    private Boolean ativo;

}
