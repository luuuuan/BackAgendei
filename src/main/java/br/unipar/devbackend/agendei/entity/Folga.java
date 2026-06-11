package br.unipar.devbackend.agendei.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Folga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private Profissional profissional;

    @ManyToOne
    @JoinColumn(name = "prestador_id")
    private Prestador prestador;

    private Boolean diaInteiro;

    private LocalTime horaInicio;

    private LocalTime horaFim;

    private String motivo;

    private Boolean ativo;

    private LocalDate inicio;

    private LocalDate fim;

}
