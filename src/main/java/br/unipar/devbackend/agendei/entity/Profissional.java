package br.unipar.devbackend.agendei.entity;


import br.unipar.devbackend.agendei.enums.StatusProfissional;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Profissional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "avaliacao_id")
    private Avaliacao avaliacao;

    private Integer totalAvaliacao;

    private Double comissaoPercentual;

    @Enumerated(EnumType.STRING)
    private StatusProfissional statusProfissional;

    @ManyToOne
    @JoinColumn(name = "prestador_id")
    private Prestador prestador;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "profissional")
    private List<Servico> servicos;
}
