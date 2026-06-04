package br.unipar.devbackend.agendei.service;

import br.unipar.devbackend.agendei.DTO.create.UsuarioAtualizaCreateDTO;
import br.unipar.devbackend.agendei.DTO.create.UsuarioCreateDTO;
import br.unipar.devbackend.agendei.DTO.create.UsuarioLoginDTO;
import br.unipar.devbackend.agendei.DTO.response.*;
import br.unipar.devbackend.agendei.config.ConfiguracaoSeguranca;
import br.unipar.devbackend.agendei.entity.*;
import br.unipar.devbackend.agendei.enums.StatusAgendamento;
import br.unipar.devbackend.agendei.enums.StatusProfissional;
import br.unipar.devbackend.agendei.enums.UserTipo;
import br.unipar.devbackend.agendei.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private EmailService emailService;

    @Autowired
    private PrestadorRepository prestadorRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public UsuarioResponseDTO mapperDTO(Usuario usuario){

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCpf(),
                usuario.getTelefone(),
                usuario.getDataNascimento(),
                usuario.getEndereco() != null? usuario.getEndereco().getId() : null,
                usuario.getTipoUsuario(),
                usuario.getPrestador() != null ? usuario.getPrestador().getId() : null,
                usuario.getAtivo()
        );

    }



    public UsuarioResponseDTO buscarPorId(Long id){

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado") );


        return mapperDTO(usuario);
    }

    public List<UsuarioResponseDTO> listarUsuarios(){
        List<Usuario> listarUsuarios = usuarioRepository.findAll();

        return listarUsuarios.stream()
                .map(u -> new UsuarioResponseDTO(
                        u.getId() ,
                        u.getNome(),
                        u.getEmail(),
                        u.getCpf(),
                        u.getTelefone(),
                        u.getDataNascimento(),
                        u.getEndereco() != null? u.getEndereco().getId() : null,
                        u.getTipoUsuario(),
                        u.getPrestador() != null ? u.getPrestador().getId() : null,
                        u.getAtivo()
                )).toList();
    }


    public UsuarioResponseDTO criarUsuario(UsuarioCreateDTO usuarioCreateDTO){
        Endereco endereco = enderecoRepository
                .findById(usuarioCreateDTO.getEnderecoId())
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado")
                );

        Prestador prestadorId = null;

        if (usuarioCreateDTO.getPrestadorId() != null) {
            prestadorId = prestadorRepository
                    .findById(usuarioCreateDTO.getPrestadorId())
                    .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));
        }

        if(usuarioRepository.existsByEmail(usuarioCreateDTO.getEmail())){
            throw new RuntimeException("E-mail já cadastrado");
        }

        if(usuarioRepository.existsByTelefone(usuarioCreateDTO.getTelefone())){
            throw new RuntimeException("Telefone já cadastrado");
        }

        if(usuarioRepository.existsByCpf(usuarioCreateDTO.getCpf())){
            throw new RuntimeException("CPF/CNPJ já cadastrado");
        }

        Usuario usuario = new Usuario();
        String token = UUID.randomUUID().toString();
        usuario.setTokenVerificacao(token);
        usuario.setTokenExpiracaoVerificacao(LocalDateTime.now().plusHours(2));

        usuario.setNome(usuarioCreateDTO.getNome());
        usuario.setEmail(usuarioCreateDTO.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuarioCreateDTO.getSenha()));
        usuario.setCpf(usuarioCreateDTO.getCpf());
        usuario.setTelefone(usuarioCreateDTO.getTelefone());
        usuario.setDataNascimento(usuarioCreateDTO.getDataNascimento());
        usuario.setDataCriacao(LocalDate.from(LocalDateTime.now()));
        usuario.setEndereco(endereco);
        usuario.setTipoUsuario(usuarioCreateDTO.getTipoUsuario());
        usuario.setPrestador(prestadorId);
        usuario.setAtivo(false);


        usuarioRepository.save(usuario);


        if (usuarioCreateDTO.getTipoUsuario() == UserTipo.PROFISSIONAL) {
            Profissional profissional = new Profissional();

            profissional.setUsuario(usuario);
            profissional.setNome(usuario.getNome());
            profissional.setStatusProfissional(StatusProfissional.PENDENTE);

            profissionalRepository.save(profissional);

        }

        if (usuarioCreateDTO.getTipoUsuario() == UserTipo.PRESTADOR) {
            Prestador prestador = new Prestador();
            prestador.setUsuario(usuario);
            prestadorRepository.save(prestador);

            usuario.setPrestador(prestador);
            usuarioRepository.save(usuario);
        }

        return mapperDTO(usuario);
    }

    public List<UsuarioResponseDTO> listar(Long prestadorId){
        List<Usuario> usuario = usuarioRepository.findClientesByPrestadorId(prestadorId);

        return usuario.stream()
                        .map(u -> new UsuarioResponseDTO(
                                u.getId() ,
                                u.getNome(),
                                u.getEmail(),
                                u.getCpf(),
                                u.getTelefone(),
                                u.getDataNascimento(),
                                u.getEndereco().getId(),
                                u.getTipoUsuario(),
                                u.getPrestador() != null ? u.getPrestador().getId() : null,
                                u.getAtivo()
                                )).toList();
    }


    public UsuarioLoginResponseDTO logar(UsuarioLoginDTO usuarioLoginDTO){
        Usuario usuario = usuarioRepository
                .findByEmail(usuarioLoginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("E-mail ou senha incorreto!"));


        if (usuario.getAtivo() == null || !usuario.getAtivo()){
            throw new RuntimeException("Usuario não verificado!");
        }

        if(!passwordEncoder.matches(usuarioLoginDTO.getSenha(), usuario.getSenha())){
            throw new RuntimeException("E-mail ou senha incorreto!");
        }

        return new UsuarioLoginResponseDTO(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getTipoUsuario().name(),
                usuario.getPrestador() != null ? usuario.getPrestador().getId() : null);


    }

    public void confirmarConta(String email){
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("E-mail inexistente!"));

        String token = UUID.randomUUID().toString();
        usuario.setTokenVerificacao(token);
        usuario.setTokenExpiracaoVerificacao(LocalDateTime.now().plusHours(2));
        usuarioRepository.save(usuario);

        emailService.confirmarConta(email, token);

    }

    public void contaConfirmada(String  email, String token){
        LocalDateTime horaAtual = LocalDateTime.now();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        if(usuario.getTokenExpiracaoVerificacao() == null || horaAtual.isAfter(usuario.getTokenExpiracaoVerificacao())){
            throw new RuntimeException("Token expirado!");
        }

        if(token.equals(usuario.getTokenVerificacao())){
            usuario.setTokenVerificacao(null);
            usuario.setAtivo(true);

            usuarioRepository.save(usuario);
        } else{
            throw new RuntimeException("Token inválido!");
        }

    }

    public void solicitarRecuperacao(String email){
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("E-mail não encontrado!"));

        String token = UUID.randomUUID().toString();
        usuario.setTokenRecuperacaoSenha(token);
        usuario.setTokenExpiracao(LocalDateTime.now().plusMinutes(30));
        usuarioRepository.save(usuario);

        emailService.enviarRecuperacao(email, token);
    }

    public void redefinirSenha(String token, String novaSenha){
        Usuario usuario = usuarioRepository.findByTokenRecuperacaoSenha(token)
                .orElseThrow(() -> new RuntimeException("Token invalido!"));

        if(usuario.getTokenExpiracao().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Token expirado!");
        }

        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuario.setTokenRecuperacaoSenha(null);
        usuario.setTokenExpiracao(null);

        usuarioRepository.save(usuario);
    }

    public UsuarioResponseDTO buscarPorCpf(String cpf){
        Usuario usuario = usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("CPF não encontrado"));

        if(usuario.getTipoUsuario() != UserTipo.PROFISSIONAL){
            throw new RuntimeException("Profissional não encontrado");
        }

        return mapperDTO(usuario);
    }

    public UsuarioResponseDTO buscarPorEmail(String email){
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("E-mail não encontrado"));

        if(usuario.getTipoUsuario() != UserTipo.PROFISSIONAL){
            throw new RuntimeException("Profissional não encontrado");
        }

        return mapperDTO(usuario);
    }

    public UsuarioAtualizaResponseDTO atualizarCliente(Long usuarioId, UsuarioAtualizaCreateDTO usuarioAtualizaCreateDTO){

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));


        usuario.setNome(usuarioAtualizaCreateDTO.getNome());
        usuario.setEmail(usuarioAtualizaCreateDTO.getEmail());
        usuario.setTelefone(usuarioAtualizaCreateDTO.getTelefone());
        usuario.setCpf(usuarioAtualizaCreateDTO.getCpf());
        usuario.setDataCriacao(usuario.getDataCriacao());
        usuario.setSenha(usuario.getSenha());


        usuarioRepository.save(usuario);


        return new UsuarioAtualizaResponseDTO(

                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCpf(),
                usuario.getTelefone()
                //usuario.getDataNascimento(),
                //usuario.getEndereco().getId(),
                //usuario.getTipoUsuario(),
                //usuario.getPrestador() != null ? usuario.getPrestador().getId() : null

        );
    }

    public void atualizarSenha(Long id, String senhaAtual, String novaSenha){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if(!passwordEncoder.matches(senhaAtual, usuario.getSenha())){
            throw new RuntimeException("Senha atual incorreta!");
        }

        if(passwordEncoder.matches(novaSenha, usuario.getSenha())){
            throw new RuntimeException("Nova senha não pode ser a mesma que a anterior");
        }

        usuario.setSenha(passwordEncoder.encode(novaSenha));

        usuarioRepository.save(usuario);
    }

    public void atualizaCadastro( Long id, Boolean ativo){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        StatusAgendamento statusAgendamento = StatusAgendamento.CONFIRMADO;

        Boolean agendamentos = agendamentoRepository.existsByUsuarioIdAndStatusAgendamento(id, statusAgendamento);


        if(!ativo && agendamentos){
            List<Agendamento> listAgendamento = agendamentoRepository.findByUsuarioIdAndStatusAgendamento(id, statusAgendamento);

            for(Agendamento agendamento : listAgendamento){
                agendamento.setStatusAgendamento(StatusAgendamento.CANCELADO);
                agendamento.setMotivoCancelamento("Usuário inativado");

                agendamentoRepository.save(agendamento);
            }

        }
        usuario.setAtivo(ativo);
        usuarioRepository.save(usuario);
    }


}
