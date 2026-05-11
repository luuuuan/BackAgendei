package br.unipar.devbackend.agendei.DTO.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoResponseDTO {

    private Long id;

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private String cidade;

    private String estado;

    private String cep;

    private Long usuarioId;
}
