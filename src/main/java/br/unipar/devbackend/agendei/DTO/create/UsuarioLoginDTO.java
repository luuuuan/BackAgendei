package br.unipar.devbackend.agendei.DTO.create;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioLoginDTO {
    private String email;
    private String cpfCnpj;
    private String senha;
    private Long prestadorId;
}
