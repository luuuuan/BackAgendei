package br.unipar.devbackend.agendei.DTO.response;

import br.unipar.devbackend.agendei.enums.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class DadosBancariosResponseDTO {

    private Long id;

    private Long bancoId;

    private String agencia;

    private String conta;

    private String digitoConta;

    private TipoConta tipoConta;

    private String cpfTitular;

    private String nomeTitular;

    private Long prestadorId;
}
