package br.unipar.devbackend.agendei.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PrestadorResponseDTO {
    private Long id;

    private String especialidade;

    private List<Long> servicosId;

    private Long usuarioId;


}
