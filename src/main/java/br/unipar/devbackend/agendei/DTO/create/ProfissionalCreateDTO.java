package br.unipar.devbackend.agendei.DTO.create;

import br.unipar.devbackend.agendei.entity.Avaliacao;
import br.unipar.devbackend.agendei.entity.Prestador;
import br.unipar.devbackend.agendei.entity.Servico;
import br.unipar.devbackend.agendei.entity.Usuario;
import br.unipar.devbackend.agendei.enums.StatusProfissional;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;
@Getter

public class ProfissionalCreateDTO {

    private Long usuarioId;

    private String descricao;


    private double comissaoPercentual;

    private StatusProfissional statusProfissional;

    @JoinColumn(name = "prestador_id")
    private Long prestadorId;
//private Long avaliacaoId;

    //private Integer totalAvaliacao;

    private List<Long> servicosIds;
}
