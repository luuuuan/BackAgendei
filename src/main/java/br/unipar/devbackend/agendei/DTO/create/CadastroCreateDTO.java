package br.unipar.devbackend.agendei.DTO.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CadastroCreateDTO {

    private UsuarioCreateDTO usuario;
    private PrestadorCreateDTO prestador; // opcional
}
