package br.unipar.devbackend.agendei.entity;


import br.unipar.devbackend.agendei.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer duracaoMinutos;

    private Integer tempoBuffer;

    @Enumerated(EnumType.STRING)
    private StatusServico statusServico;

    @Enumerated(EnumType.STRING)
    private ServicoExecucaoStatus statusExecucaoServico;

    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private Profissional profissional;

    private String nome;

    private LocalDate dataCriacao;

    private String descricao;

    private BigDecimal valorServico;

    @Enumerated(EnumType.STRING)
    private TipoCobranca tipoCobranca;

    @Enumerated(EnumType.STRING)
    private LocalAtendimento localAtendimento;

    @ManyToOne
    @JoinColumn(name = "prestador_id")
    private Prestador prestador;

    private String nomePrestador;
}
