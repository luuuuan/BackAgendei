package br.unipar.devbackend.agendei.DTO.response;

import br.unipar.devbackend.agendei.entity.Endereco;
import br.unipar.devbackend.agendei.enums.UserTipo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {
    private Long id;

    private String nome;

    private String email;

    private String cpf;

    private String telefone;

    private LocalDate dataNascimento;

    private Long enderecoId;

    private UserTipo tipoUsuario;

    private Long prestadorId;

    private Boolean ativo;
}
