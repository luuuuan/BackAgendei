package br.unipar.devbackend.agendei.DTO.create;

import br.unipar.devbackend.agendei.entity.Endereco;
import br.unipar.devbackend.agendei.enums.UserTipo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UsuarioCreateDTO {

    @NotNull(message = "Nome é obrigatório!")
    private String nome;

    @Email(message = "E-mail inválido!")
    @NotNull(message = "E-mail deve ser informado!")
    private String email;

    @Size(min = 8, message = "Senha inválido!")
    @NotNull(message = "Senha deve ser informada!")
    private String senha;

    @NotNull(message = "CPF/CNPJ deve ser informado!")
    private String cpf;

    @NotNull(message = "Número de telefone deve ser informado!")
    @Size(min = 11)
    private String telefone;

    private LocalDate dataNascimento;

    @NotNull(message = "Endereço deve ser informado")
    private Long enderecoId;

    @NotNull(message = "Tipo de usuario deve ser informado")
    private UserTipo tipoUsuario;


    private Long prestadorId;

}
