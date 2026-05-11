package br.unipar.devbackend.agendei.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class FeriadoFolga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    private String tipo;

    private LocalDate dataInicio;

    private LocalDate dataFim;

    private boolean recorrente;


    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private Profissional profissional;


}
