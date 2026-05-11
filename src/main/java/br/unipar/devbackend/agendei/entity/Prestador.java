package br.unipar.devbackend.agendei.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Prestador{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "prestador_servico",
            joinColumns = @JoinColumn(name = "prestador_id"),
            inverseJoinColumns = @JoinColumn(name = "servico_id")
    )
    private List<Servico> servico;

    private String especialidade;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
