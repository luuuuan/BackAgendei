package br.unipar.devbackend.agendei.repository;

import br.unipar.devbackend.agendei.entity.GradeTrabalho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface GradeTrabalhoRepository extends JpaRepository <GradeTrabalho, Long> {

    List<GradeTrabalho> findByProfissionalId(Long profissionalId);

    List<GradeTrabalho> findByPrestadorId(Long prestadorId);
	
	Optional<GradeTrabalho> findByIdAndProfissionalId(Long id, Long profissionalId);

}
