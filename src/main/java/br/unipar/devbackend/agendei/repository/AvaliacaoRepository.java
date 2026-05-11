package br.unipar.devbackend.agendei.repository;

import br.unipar.devbackend.agendei.entity.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
}
