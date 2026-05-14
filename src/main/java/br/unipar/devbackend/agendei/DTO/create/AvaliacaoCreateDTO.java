package br.unipar.devbackend.agendei.DTO.create;

import br.unipar.devbackend.agendei.entity.Profissional;
import br.unipar.devbackend.agendei.entity.Usuario;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

public class AvaliacaoCreateDTO {
    @NotNull(message = "Nota deve ser informada")
    private Double nota;

    private String comentario;

    private Long agendamentoId;

    @NotNull(message = "Profissional deve ser informado")
    private Long profissionalId;

    private Long usuarioId;


}
