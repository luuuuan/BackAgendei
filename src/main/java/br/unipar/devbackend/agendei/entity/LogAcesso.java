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

public class LogAcesso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String acao;

    private String ip;

    private String userAgent;

    private LocalDateTime dataAcesso;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
