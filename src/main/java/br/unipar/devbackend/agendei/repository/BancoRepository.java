package br.unipar.devbackend.agendei.repository;

import br.unipar.devbackend.agendei.entity.Banco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BancoRepository extends JpaRepository<Banco, Long> {

}
