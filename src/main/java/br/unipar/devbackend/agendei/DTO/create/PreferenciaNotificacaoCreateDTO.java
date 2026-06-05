package br.unipar.devbackend.agendei.DTO.create;

import br.unipar.devbackend.agendei.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreferenciaNotificacaoCreateDTO {
    private Long usuarioId;

    private Boolean emailConfirmacao;

    private Boolean emailLembrete;

    private Boolean emailCancelamento;

    private Boolean antecedenciaLembrete;
}
