package br.unipar.devbackend.agendei.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PagamentoIntentResponseDTO {
    private String clientSecret;
    private Long agendamentoId;


}
