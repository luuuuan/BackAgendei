package br.unipar.devbackend.agendei.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class HorarioDisponivelResponseDTO {
    private Long id;

    private LocalDate data;

    private Long profissionalId;
}
