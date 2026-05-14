package br.unipar.devbackend.agendei.service;

import br.unipar.devbackend.agendei.DTO.create.UsuarioAtualizaCreateDTO;
import br.unipar.devbackend.agendei.DTO.create.UsuarioCreateDTO;
import br.unipar.devbackend.agendei.DTO.create.UsuarioLoginDTO;
import br.unipar.devbackend.agendei.DTO.response.*;
import br.unipar.devbackend.agendei.config.ConfiguracaoSeguranca;
import br.unipar.devbackend.agendei.entity.*;
import br.unipar.devbackend.agendei.enums.StatusProfissional;
import br.unipar.devbackend.agendei.enums.UserTipo;
import br.unipar.devbackend.agendei.repository.EnderecoRepository;
import br.unipar.devbackend.agendei.repository.PrestadorRepository;
import br.unipar.devbackend.agendei.repository.ProfissionalRepository;
import br.unipar.devbackend.agendei.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public UsuarioResponseDTO mapperDTO(Usuario usuario){

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCpf(),
                usuario.getTelefone(),
                usuario.getDataNascimento(),
                usuario.getEndereco().getId(),
                usuario.getTipoUsuario(),
                usuario.getPrestador() != null ? usuario.getPrestador().getId() : null
        );

    }



    public UsuarioResponseDTO buscarPorId(Long id){

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado") );


        return mapperDTO(usuario);
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

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getCpf(),
                usuario.getTelefone(),
                usuario.getDataNascimento(),
                usuario.getEndereco().getId(),
                usuario.getTipoUsuario(),
                usuario.getPrestador() != null ? usuario.getPrestador().getId() : null

        );
    }

    public List<UsuarioResponseDTO> listar(String cpf, Long prestadorId){

        List<Usuario> usuario = usuarioRepository.findByCpfAndPrestadorId(cpf, prestadorId);

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
                                u.getPrestador() != null ? u.getPrestador().getId() : null
                                )).toList();



    }


    public UsuarioLoginResponseDTO logar(UsuarioLoginDTO usuarioLoginDTO){
        Usuario usuario = usuarioRepository
                .findByEmail(
                        usuarioLoginDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("E-mail ou senha incorreto!"));

//        Profissional profissional = profissionalRepository.findByPrestador_Id()
//
//        if(usuario.getTipoUsuario() == UserTipo.PROFISSIONAL && usuario.getPrestador() == null){
//            throw new RuntimeException("Profissional não possui prestador vinculado");
//
//        }

        if(!passwordEncoder.matches(usuarioLoginDTO.getSenha(), usuario.getSenha())){
            throw new RuntimeException("E-mail ou senha incorretooo");
        }

        return new UsuarioLoginResponseDTO(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getTipoUsuario().name(),
                usuario.getPrestador() != null ? usuario.getPrestador().getId() : null);


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


}
