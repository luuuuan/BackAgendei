package br.unipar.devbackend.agendei.repository;


import br.unipar.devbackend.agendei.entity.PreferenciaNotificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PreferenciaNotificacaoRepository extends JpaRepository<PreferenciaNotificacao, Long> {

    Optional<PreferenciaNotificacao> findByUsuarioId(Long UsuarioId);

}
