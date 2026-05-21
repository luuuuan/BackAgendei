package br.unipar.devbackend.agendei.repository;

import br.unipar.devbackend.agendei.entity.Profissional;
import br.unipar.devbackend.agendei.entity.Servico;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

    List<Profissional> findByServicos_Id(Long aLong);

    Optional<Profissional> findByUsuario_Id(Long usuarioId);

    List<Profissional> findByPrestador_Id(Long aLong);

    List<Long> findByPrestadorId(Long prestadorId);
}
