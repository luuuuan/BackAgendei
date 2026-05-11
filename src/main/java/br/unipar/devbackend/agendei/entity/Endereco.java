package br.unipar.devbackend.agendei.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private String cidade;

    private String estado;

    @Column(nullable = false)
    private String cep;

    private Double latitude;

    private Double longitude;

    private Long usuarioId;

}
