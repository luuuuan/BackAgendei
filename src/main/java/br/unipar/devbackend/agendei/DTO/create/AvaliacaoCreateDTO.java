package br.unipar.devbackend.agendei.DTO.create;

import br.unipar.devbackend.agendei.entity.Profissional;
import br.unipar.devbackend.agendei.entity.Usuario;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoCreateDTO {
    @NotNull(message = "Nota deve ser informada")
    private Integer nota;

    private String comentario;

    private Long agendamentoId;

    @NotNull(message = "Profissional deve ser informado")
    private Long profissionalId;

    private Long usuarioId;


}
