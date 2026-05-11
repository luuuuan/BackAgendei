package br.unipar.devbackend.agendei.DTO.create;

import br.unipar.devbackend.agendei.entity.Servico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class PrestadorCreateDTO {

    private String especialidade;

    private List<Long> servicosId;

    private Long usuarioId;
}
