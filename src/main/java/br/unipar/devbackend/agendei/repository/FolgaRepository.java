package br.unipar.devbackend.agendei.repository;


import br.unipar.devbackend.agendei.entity.Folga;
import br.unipar.devbackend.agendei.entity.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface FolgaRepository extends JpaRepository<Folga, Long> {

    Optional<Folga> findByProfissionalId(Long profissionalId);

    Boolean existsByProfissionalIdAndData(Long profissionalId, LocalDate data);
}
