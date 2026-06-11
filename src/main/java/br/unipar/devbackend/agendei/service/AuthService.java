package br.unipar.devbackend.agendei.service;


import br.unipar.devbackend.agendei.DTO.create.CadastroCreateDTO;
import br.unipar.devbackend.agendei.DTO.create.PrestadorCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.UsuarioLoginResponseDTO;
import br.unipar.devbackend.agendei.entity.Prestador;
import br.unipar.devbackend.agendei.entity.Servico;
import br.unipar.devbackend.agendei.entity.Usuario;
import br.unipar.devbackend.agendei.enums.UserTipo;
import br.unipar.devbackend.agendei.repository.PrestadorRepository;
import br.unipar.devbackend.agendei.repository.ServicoRepository;
import br.unipar.devbackend.agendei.repository.UsuarioRepository;
import br.unipar.devbackend.agendei.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PrestadorRepository prestadorRepository;

    @Autowired
    private ServicoRepository servicoRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtil jwtUtil;
	
    public UsuarioLoginResponseDTO cadastrar(CadastroCreateDTO cadastroCreateDTO) {

        Usuario usuario = new Usuario();
        usuario.setNome(cadastroCreateDTO.getUsuario().getNome());
        usuario.setEmail(cadastroCreateDTO.getUsuario().getEmail());
        usuario.setSenha(passwordEncoder.encode(cadastroCreateDTO.getUsuario().getSenha()));
        usuario.setTipoUsuario(cadastroCreateDTO.getUsuario().getTipoUsuario());

        usuarioRepository.save(usuario);

        Prestador prestadorSalvo = null;

        if (usuario.getTipoUsuario() == UserTipo.PRESTADOR) {

            PrestadorCreateDTO prestadorDTO = cadastroCreateDTO.getPrestador();

            if (prestadorDTO == null) {
                throw new RuntimeException("Dados do prestador obrigatórios");
            }

            List<Servico> servicos = servicoRepository
                    .findAllById(prestadorDTO.getServicosId());

            Prestador prestador = new Prestador();
            prestador.setEspecialidade(prestadorDTO.getEspecialidade());
            prestador.setServico(servicos);
            prestador.setUsuario(usuario);

            prestadorSalvo = prestadorRepository.save(prestador);
        }
		
		Long prestadorId = prestadorSalvo != null ? prestadorSalvo.getId() : null;
		
		String token = jwtUtil.gerarToken(usuario.getId(), usuario.getEmail(), usuario.getTipoUsuario().name(), prestadorId);

        return new UsuarioLoginResponseDTO(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getTipoUsuario().name(),
                prestadorId,
				token
				
        );
    }
}
