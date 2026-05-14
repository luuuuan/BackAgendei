package br.unipar.devbackend.agendei.DTO.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class AvaliacaoResponseDTO {
    private Long id;

    private Integer nota;

    private String comentario;

    private Long agendamentoId;

    private Long profissionalId;

    private Long usuarioId;
}
