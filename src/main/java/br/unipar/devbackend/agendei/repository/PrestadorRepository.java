package br.unipar.devbackend.agendei.repository;

import br.unipar.devbackend.agendei.entity.Prestador;
import br.unipar.devbackend.agendei.entity.Profissional;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PrestadorRepository extends CrudRepository<Prestador, Long> {

    Optional<Profissional> findByProfissionalId(Long profissionalId);

}
