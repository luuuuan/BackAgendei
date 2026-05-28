package br.unipar.devbackend.agendei.repository;

import br.unipar.devbackend.agendei.entity.Agendamento;
import br.unipar.devbackend.agendei.enums.StatusAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByDataAgendamento(LocalDate dataCriacao);

    List<Agendamento> findByUsuarioId(Long usuarioId);

    List<Agendamento> findByPrestadorId(Long prestaodorId);

    List<Agendamento> findByProfissionalIdAndDataAgendamento(Long profissionalId, LocalDate data);

    List<Agendamento> findByPrestadorIdAndDataAgendamento(Long prestadorId, LocalDate data);

    Boolean existsByUsuarioIdAndPrestadorId(Long usuarioId, Long prestadorId);
	
	List<Agendamento> findByDataAgendamentoBetweenAndNotificacaoEnviadaFalse(LocalDateTime inicio, LocalDateTime fim);

    List<Agendamento> findByStatusAgendamento(StatusAgendamento statusAgendamento);
}
