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
import org.springframework.beans.factory.annotation.Autowired;
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

    public UsuarioLoginResponseDTO cadastrar(CadastroCreateDTO cadastroCreateDTO) {

        Usuario usuario = new Usuario();
        usuario.setNome(cadastroCreateDTO.getUsuario().getNome());
        usuario.setEmail(cadastroCreateDTO.getUsuario().getEmail());
        usuario.setSenha(cadastroCreateDTO.getUsuario().getSenha());
        usuario.setTipoUsuario(cadastroCreateDTO.getUsuario().getTipoUsuario());

        usuarioRepository.save(usuario);

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

            prestadorRepository.save(prestador);
        }

        return new UsuarioLoginResponseDTO(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getTipoUsuario().name(),
                usuario.getPrestador().getId()
        );
    }
}
