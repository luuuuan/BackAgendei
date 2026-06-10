package br.unipar.devbackend.agendei.repository;

import br.unipar.devbackend.agendei.entity.HorarioDisponivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HorarioDisponivelRepository extends JpaRepository<HorarioDisponivel, Long> {
    List<HorarioDisponivel> findByProfissionalIdAndData(Long profissionalId, LocalDate dataAgendamento);

    List<HorarioDisponivel> findByPrestadorIdAndData(Long profissionalId, LocalDate dataAgendamento);

    List<HorarioDisponivel> findByProfissionalId(Long profissionalId);
}
