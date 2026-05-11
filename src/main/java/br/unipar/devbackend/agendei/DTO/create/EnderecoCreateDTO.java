package br.unipar.devbackend.agendei.DTO.create;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoCreateDTO {
    private Long id;

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private String cidade;

    private String estado;

    @Column(nullable = false)
    private String cep;

    private Double latitude;

    private Double longitude;

    private Long usuarioId;
}
