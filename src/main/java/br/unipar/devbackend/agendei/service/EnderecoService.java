package br.unipar.devbackend.agendei.service;


import br.unipar.devbackend.agendei.DTO.create.EnderecoCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.EnderecoResponseDTO;
import br.unipar.devbackend.agendei.entity.Endereco;
import br.unipar.devbackend.agendei.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {
    @Autowired
    private EnderecoRepository enderecoRepository;


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

        endereco = enderecoRepository.save(endereco);

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
