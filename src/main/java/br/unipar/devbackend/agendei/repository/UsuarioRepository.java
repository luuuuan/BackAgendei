package br.unipar.devbackend.agendei.repository;

import br.unipar.devbackend.agendei.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    boolean existsByEmail(String email);

    boolean existsByTelefone(String telefone);

    boolean existsByCpf(String cpf);

    List<Usuario> findByCpfAndPrestadorId(String cpf, Long prestadorId);

    Optional<Usuario> findByCpf(String cpf);


    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByTokenRecuperacaoSenha(String token);

    Optional<Usuario> findByPrestadorId(Long prestadorId);

    @Query("SELECT DISTINCT a.usuario FROM Agendamento a WHERE a.profissional.prestador.id = :prestadorId")
    List<Usuario> findClientesByPrestadorId(@Param("prestadorId") Long prestadorId);
}
