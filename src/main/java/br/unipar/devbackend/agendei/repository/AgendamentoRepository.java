package br.unipar.devbackend.agendei.repository;

import br.unipar.devbackend.agendei.entity.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByDataCriacao(LocalDate dataCriacao);

    List<Agendamento> findByUsuarioId(Long usuarioId);

    List<Agendamento> findByPrestadorId(Long prestaodorId);

    List<Agendamento> findByProfissionalIdAndDataAgendamento(Long profissionalId, LocalDate data);
}
