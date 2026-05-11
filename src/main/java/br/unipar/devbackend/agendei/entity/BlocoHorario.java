package br.unipar.devbackend.agendei.entity;


import br.unipar.devbackend.agendei.enums.DiasSemana;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlocoHorario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private List<DiasSemana> diasSemana;

    private LocalTime horaInicio;

    private LocalTime horaFim;

    private boolean repetirSemanalmente;

    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "prestador_id")
    private Profissional profissional;
}
