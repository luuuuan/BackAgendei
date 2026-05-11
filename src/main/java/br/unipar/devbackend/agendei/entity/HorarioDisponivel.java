package br.unipar.devbackend.agendei.entity;



import br.unipar.devbackend.agendei.enums.StatusHorario;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter @Setter
@Builder

@AllArgsConstructor
@NoArgsConstructor
public class HorarioDisponivel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;

    private LocalTime horaInicio;

    private LocalTime horaFim;

    @Enumerated(EnumType.STRING)
    private StatusHorario statusHorario;

    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private Profissional profissional;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private Servico servico;


}
