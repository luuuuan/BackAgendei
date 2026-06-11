package br.unipar.devbackend.agendei.DTO.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioLoginResponseDTO {
    private Long usuarioId;
    private String email;
    private String tipoUsuario;
    private Long prestadorId;
    private String token;
}
