package br.unipar.devbackend.agendei.repository;

import br.unipar.devbackend.agendei.entity.GradeTrabalho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradeTrabalhoRepository extends JpaRepository <GradeTrabalho, Long> {

    Optional<GradeTrabalho> findByProfissionalId(Long profissionalId);

}
