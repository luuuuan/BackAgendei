package br.unipar.devbackend.agendei.DTO.response;


import br.unipar.devbackend.agendei.entity.Avaliacao;
import br.unipar.devbackend.agendei.entity.Prestador;
import br.unipar.devbackend.agendei.entity.Servico;
import br.unipar.devbackend.agendei.entity.Usuario;
import br.unipar.devbackend.agendei.enums.StatusProfissional;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProfissionalResponseDTO {
    private Long id;

    private String nome;

    private Long usuarioId;

    private String descricao;

    //private Avaliacao avaliacao;

    //private Integer totalAvaliacao;

    private Long prestadorId;

    private List<Long> servicoId;

    private Boolean atendeADomicilio;


}
