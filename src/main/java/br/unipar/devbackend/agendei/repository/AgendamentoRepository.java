package br.unipar.devbackend.agendei.repository;

import br.unipar.devbackend.agendei.entity.Agendamento;
import br.unipar.devbackend.agendei.entity.HorarioDisponivel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByDataCriacao(LocalDate dataCriacao);

    List<Agendamento> findByUsuarioId(Long usuarioId);

}
