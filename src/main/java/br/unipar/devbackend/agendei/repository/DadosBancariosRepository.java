package br.unipar.devbackend.agendei.repository;

import br.unipar.devbackend.agendei.entity.DadosBancarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DadosBancariosRepository extends JpaRepository<DadosBancarios, Long> {
}
