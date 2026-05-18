package br.unipar.devbackend.agendei.DTO.create;

import br.unipar.devbackend.agendei.entity.Profissional;
import br.unipar.devbackend.agendei.enums.LocalAtendimento;
import br.unipar.devbackend.agendei.enums.ServicoExecucaoStatus;
import br.unipar.devbackend.agendei.enums.StatusServico;
import br.unipar.devbackend.agendei.enums.TipoCobranca;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
public class ServicoCreateDTO {


    @NotNull(message = "Tempo de duração do serviço deve ser informado")
    private Integer duracaoMinutos;

    private Integer tempoBuffer;

    private StatusServico statusServico;

    private ServicoExecucaoStatus statusExecucaoServico;

//    @NotNull(message = "Valor do serviço deve ser informado!")
//    private BigDecimal valor;

    @NotNull(message = "Profissional deve ser informado")
    private Long profissionalId;

    @NotNull(message = "Nome do serviço deve ser informado")
    private String nome;

    private LocalDate dataCriacao;

    @NotNull(message = "Adicione uma descrição ao serviço")
    private String descricao;

    private TipoCobranca tipoCobranca;

    private LocalAtendimento localAtendimento;

    @NotNull(message = "Valor do serviço deve ser informado!")
    private BigDecimal ValorServico;
}
