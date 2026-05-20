package br.unipar.devbackend.agendei.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

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

    @ManyToOne
    private Prestador prestador;

    private DayOfWeek diaInicio;

    private DayOfWeek diaFim;

    private LocalTime horaInicio;

    private LocalTime horaFim;

    private LocalTime inicioIntervalo;

    private LocalTime fimIntervalo;

    private Boolean ativo;
}
