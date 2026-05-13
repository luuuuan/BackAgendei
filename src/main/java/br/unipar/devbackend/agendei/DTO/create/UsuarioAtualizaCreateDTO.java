package br.unipar.devbackend.agendei.DTO.create;

import br.unipar.devbackend.agendei.enums.UserTipo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioAtualizaCreateDTO {

    //private Long id;

    private String nome;

    @Email(message = "E-mail inválido!")
    private String email;

    @Size(min = 9, message = "Telefone inválido!")
    private String telefone;

    @Size(min = 11, message = "CPF/CNPJ inválido!")
    private String cpf;



//    private LocalDate dataNascimento;
//
//    @NotNull(message = "Endereço deve ser informado")
//    private Long enderecoId;
//
//    @NotNull(message = "Tipo de usuario deve ser informado")
//    private UserTipo tipoUsuario;
//
//
//    private Long prestadorId;
}
