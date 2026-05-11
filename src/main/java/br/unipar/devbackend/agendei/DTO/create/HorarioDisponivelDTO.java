package br.unipar.devbackend.agendei.DTO.create;

import br.unipar.devbackend.agendei.entity.Profissional;
import br.unipar.devbackend.agendei.entity.Servico;
import br.unipar.devbackend.agendei.enums.StatusHorario;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class HorarioDisponivelDTO {

    private LocalDate data;

    private LocalTime horaInicio;

    private LocalTime horaFim;

    private StatusHorario statusHorario;

    private Profissional profissional;

    private Servico servico;
}
