package br.unipar.devbackend.agendei.DTO.create;

import br.unipar.devbackend.agendei.enums.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DadosBancariosCreateDTO {
    private String agencia;

    private Long bancoId;

    private String conta;

    private String cpfTitular;


    private String digitoConta;

    private String nomeTitular;

    private Long prestadorId;

    private TipoConta tipoConta;

}
