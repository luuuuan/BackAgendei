package br.unipar.devbackend.agendei.entity;

import br.unipar.devbackend.agendei.DTO.response.UsuarioResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PreferenciasNotificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private Boolean emailConfirmacao;

    private Boolean emailLembrete;

    private Boolean emailCancelamento;

    private Boolean antecedenciaLembrete;
}
