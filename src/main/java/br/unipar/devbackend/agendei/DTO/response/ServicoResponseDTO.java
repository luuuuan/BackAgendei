package br.unipar.devbackend.agendei.DTO.response;

import br.unipar.devbackend.agendei.entity.Profissional;
import br.unipar.devbackend.agendei.enums.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ServicoResponseDTO {
    private Long id;

    private Integer duracaoMinutos;

    private Integer tempoBuffer;

    private StatusServico statusServico;

    private ServicoExecucaoStatus statusExecucaoServico;

    private Long profissionalId;

    private String nomeProfissional;

    private String nome;

    private String descricao;

    private TipoCobranca tipoCobranca;

    private LocalAtendimento localAtendimento;

    private BigDecimal valorServico;

}
