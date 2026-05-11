package br.unipar.devbackend.agendei.entity;



import br.unipar.devbackend.agendei.enums.UserTipo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String telefone;

    private String bio;

    private String fotoPerfil;

    private LocalDate dataNascimento;

    @Column(nullable = false)
    private LocalDate dataCriacao;

    private LocalDate ultimoLogin;

    private Boolean ativo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    private UserTipo tipoUsuario;

    private String tokenRecuperacaoSenha;

    private LocalDateTime tokenExpiracao;

    @ManyToOne
    @JoinColumn(name = "prestador_id")
    private Prestador prestador;

}
