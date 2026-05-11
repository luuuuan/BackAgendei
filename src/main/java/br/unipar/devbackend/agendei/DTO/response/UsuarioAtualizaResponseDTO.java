package br.unipar.devbackend.agendei.DTO.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioAtualizaResponseDTO {
    private Long id;

    private String nome;

    private String email;

    private String telefone;

    private String cpf;
}
