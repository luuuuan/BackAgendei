package br.unipar.devbackend.agendei.repository;

import br.unipar.devbackend.agendei.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
    List<Servico> findByProfissionalId(Long profissionalId);

    List<Servico> findByPrestadorId(Long prestadorId);
}
