package br.unipar.devbackend.agendei.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double nota;

    private String comentario;

    private Integer totalAvaliacao;

    @ManyToOne
    private Usuario cliente;

    @ManyToOne
    private Profissional profissional;
}
