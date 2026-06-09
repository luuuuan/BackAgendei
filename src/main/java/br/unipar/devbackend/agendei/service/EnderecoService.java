package br.unipar.devbackend.agendei.service;


import br.unipar.devbackend.agendei.DTO.create.EnderecoCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.EnderecoResponseDTO;
import br.unipar.devbackend.agendei.entity.Endereco;
import br.unipar.devbackend.agendei.entity.Usuario;
import br.unipar.devbackend.agendei.repository.EnderecoRepository;
import br.unipar.devbackend.agendei.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    public EnderecoResponseDTO criarEndereco(EnderecoCreateDTO enderecoCreateDTO){

        Endereco endereco = new Endereco();

        endereco.setBairro(enderecoCreateDTO.getBairro());
        endereco.setCep(enderecoCreateDTO.getCep());
        endereco.setCidade(enderecoCreateDTO.getCidade());
        endereco.setComplemento(enderecoCreateDTO.getComplemento());
        endereco.setEstado(enderecoCreateDTO.getEstado());
        endereco.setLogradouro(enderecoCreateDTO.getLogradouro());
        endereco.setNumero(enderecoCreateDTO.getNumero());
        endereco.setUsuarioId(enderecoCreateDTO.getUsuarioId());

        enderecoRepository.save(endereco);

        return new EnderecoResponseDTO(
                endereco.getId(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep(),
                endereco.getUsuarioId()
        );
    }

    public EnderecoResponseDTO meuEndereco(Long enderecoId){

        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(()-> new RuntimeException("Endereço não encontrado"));

        return new EnderecoResponseDTO(
                endereco.getId(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep(),
                endereco.getUsuarioId()
        );
    }

    public EnderecoResponseDTO atualizaEndereco(EnderecoCreateDTO enderecoCreateDTO){

        Endereco endereco = enderecoRepository.findById(enderecoCreateDTO.getId())
                .orElseThrow(()-> new RuntimeException("Endereço não encontrado"));

        Usuario usuario = usuarioRepository.findById(enderecoCreateDTO.getUsuarioId())
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        endereco.setBairro(enderecoCreateDTO.getBairro());
        endereco.setCep(enderecoCreateDTO.getCep());
        endereco.setCidade(enderecoCreateDTO.getCidade());
        endereco.setComplemento(enderecoCreateDTO.getComplemento());
        endereco.setEstado(enderecoCreateDTO.getEstado());
        endereco.setLogradouro(enderecoCreateDTO.getLogradouro());
        endereco.setNumero(enderecoCreateDTO.getNumero());

        enderecoRepository.save(endereco);

        return new EnderecoResponseDTO(
                endereco.getId(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep(),
                endereco.getUsuarioId()
        );
    }

}
