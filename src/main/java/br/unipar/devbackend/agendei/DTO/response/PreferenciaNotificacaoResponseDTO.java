package br.unipar.devbackend.agendei.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PreferenciaNotificacaoResponseDTO {
    private Long id;

    private Long usuarioId;

    private Boolean emailConfirmacao;

    private Boolean emailLembrete;

    private Boolean emailCancelamento;

    private Boolean whatsAppNotificacao;

    private int antecedenciaLembrete;
}
