package br.unipar.devbackend.agendei.repository;

import br.unipar.devbackend.agendei.entity.Avaliacao;
import br.unipar.devbackend.agendei.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    boolean existsByAgendamentoIdAndUsuarioId(Long agendamentoId, Long usuarioId);
}
