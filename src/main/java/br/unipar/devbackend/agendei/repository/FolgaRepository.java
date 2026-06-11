package br.unipar.devbackend.agendei.repository;


import br.unipar.devbackend.agendei.entity.Folga;
import br.unipar.devbackend.agendei.entity.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FolgaRepository extends JpaRepository<Folga, Long> {

    List<Folga> findByProfissionalId(Long profissionalId);

    List<Folga> findByPrestadorId(Long prestadorId);

    Boolean existsByProfissionalIdAndData(Long profissionalId, LocalDate data);

    Boolean existsByPrestadorIdAndData(Long prestadorId, LocalDate data);

    List<Folga> findByPrestadorIdAndDataBetween(Long prestadorId, LocalDate inicio, LocalDate fim);

    Boolean existsByDataAndProfissionalIdAndPrestadorId(LocalDate data, Long profissionalId, Long prestadorId);
}
