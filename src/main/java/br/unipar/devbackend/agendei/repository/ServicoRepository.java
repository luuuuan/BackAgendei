package br.unipar.devbackend.agendei.repository;

import br.unipar.devbackend.agendei.entity.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
    List<Servico> findByProfissionalId(Long profissionalId);

}
