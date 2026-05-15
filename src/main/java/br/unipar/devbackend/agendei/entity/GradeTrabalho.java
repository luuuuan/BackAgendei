package br.unipar.devbackend.agendei.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class GradeTrabalho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Profissional profissional;

    private DayOfWeek diaInicio;

    private DayOfWeek diaFim;

    private LocalTime horarioInicio;

    private LocalTime horarioFim;

    private LocalTime inicioIntervalo;

    private LocalTime fimIntervalo;

    private Boolean ativo;
}
