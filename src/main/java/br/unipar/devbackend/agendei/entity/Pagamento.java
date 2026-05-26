package br.unipar.devbackend.agendei.entity;

import br.unipar.devbackend.agendei.enums.FormaPgto;
import br.unipar.devbackend.agendei.enums.StatusPagamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Valor deve ser informado")
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;

    @Enumerated(EnumType.STRING)
    private FormaPgto formaPgto;

    private String idTransacaoStripe;

    private String idReembolsoStripe;

    private LocalDateTime dataPgto;

    private LocalDateTime dataReembolso;

    @NotNull(message = "Agendamento deve ser informado")
    @ManyToOne
    private Agendamento agendamento;
}
