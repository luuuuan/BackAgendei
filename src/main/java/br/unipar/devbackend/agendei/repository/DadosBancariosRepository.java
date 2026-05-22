package br.unipar.devbackend.agendei.repository;

import br.unipar.devbackend.agendei.entity.DadosBancarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DadosBancariosRepository extends JpaRepository<DadosBancarios, Long> {

    Optional<DadosBancarios> findByPrestadorId(Long id);

}
