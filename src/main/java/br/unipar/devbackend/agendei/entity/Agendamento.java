package br.unipar.devbackend.agendei.entity;


import br.unipar.devbackend.agendei.enums.StatusAgendamento;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private BigDecimal valorServico;

    private LocalDate dataAgendamento;

    private LocalDate dataCriacao;

    private LocalTime horaInicio;

    private LocalTime horaFim;

    private LocalDate dataConfirmacao;

    private LocalDate dataCancelamento;

    @Enumerated(EnumType.STRING)
    private StatusAgendamento statusAgendamento;

    private String motivoCancelamento;

    @Column(nullable = false)
    private BigDecimal taxaPlataforma;

    private BigDecimal valorTotal;

    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name ="profissional_id")
    private Profissional profissional;

    

    @ManyToMany
    @JoinTable(
            name = "agendamento_servico",
            joinColumns = @JoinColumn(name = "agendamento_id"),
            inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    private List<Servico> servicos;


    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
}
