package br.unipar.devbackend.agendei.repository;

import br.unipar.devbackend.agendei.entity.AuditoriaAgendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriaAgendamentoRepository extends JpaRepository<AuditoriaAgendamento, Long> {
}
