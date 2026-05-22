package br.unipar.devbackend.agendei.entity;

import br.unipar.devbackend.agendei.enums.TipoConta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor

public class DadosBancarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Banco banco;

    private String agencia;

    private String conta;

    private String digitoConta;

    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;

    private String cpfTitular;

    private String nomeTitular;

    @ManyToOne
    private Prestador prestador;

}
