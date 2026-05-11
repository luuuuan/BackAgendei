package br.unipar.devbackend.agendei.service;

import br.unipar.devbackend.agendei.DTO.create.AgendamentoPesquisaDTO;
import br.unipar.devbackend.agendei.DTO.create.ProfissionalCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.AgendamentoResponseDTO;
import br.unipar.devbackend.agendei.DTO.response.ProfissionalResponseDTO;
import br.unipar.devbackend.agendei.DTO.response.UsuarioResponseDTO;
import br.unipar.devbackend.agendei.entity.*;
import br.unipar.devbackend.agendei.enums.StatusProfissional;
import br.unipar.devbackend.agendei.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfissionalService {
    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;
    @Autowired
    private PrestadorRepository prestadorRepository;


    public ProfissionalResponseDTO mapperDTO(Profissional profissional){

        return new ProfissionalResponseDTO(
                profissional.getId(),
                profissional.getNome(),
                profissional.getUsuario().getId(),
                profissional.getDescricao(),
                profissional.getPrestador() != null ? profissional.getPrestador().getId() : null,
                profissional.getServicos()
                        .stream()
                        .map(Servico::getId)
                        .toList(),
                profissional.getAtendeADomicilio()
        );

    }


    public ProfissionalResponseDTO criarProfissional(ProfissionalCreateDTO profissionalCreateDTO){
        Usuario usuario = usuarioRepository
                .findById(profissionalCreateDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado ou não cadastrado")
                        );

        Prestador prestador = prestadorRepository
                .findById(profissionalCreateDTO.getPrestadorId())
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado")
                );

        List<Servico> servicos = servicoRepository
                .findAllById(profissionalCreateDTO.getServicosIds());

//        Avaliacao avaliacao = avaliacaoRepository
//                .findById(profissionalCreateDTO.getAvaliacaoId())
//                .orElseThrow(() -> new RuntimeException("Avaliação não encontrada"));

        Profissional profissional = new Profissional();

        profissional.setUsuario(usuario);
        profissional.setNome(usuario.getNome());
        profissional.setDescricao(profissionalCreateDTO.getDescricao());
        profissional.setComissaoPercentual(profissionalCreateDTO.getComissaoPercentual());
        profissional.setStatusProfissional(StatusProfissional.PENDENTE);
        profissional.setPrestador(prestador);
        profissional.setServicos(servicos);
        profissional.setAtendeADomicilio(profissionalCreateDTO.getAtendeADomicilio());



        profissionalRepository.save(profissional);
        return mapperDTO(profissional);
    }


    public List<ProfissionalResponseDTO>
    listarProfissionais(Long prestadorId) {
        List<Profissional> profissionais = profissionalRepository.findByPrestador_Id(prestadorId);

        if(profissionais.isEmpty()){
            throw new RuntimeException("Nenhum profissinal encontrado");
        }

        return profissionais.stream()
                .map( p ->  new ProfissionalResponseDTO(
                        p.getId(),
                        p.getNome(),
                        p.getUsuario().getId(),
                        p.getDescricao(),
                        p.getPrestador() != null ? p.getPrestador().getId() : null,
                        p.getServicos()
                                .stream()
                                .map(Servico::getId)
                                .toList(),
                        p.getAtendeADomicilio())

                ).toList();
    }

    public List<ProfissionalResponseDTO>
            listarProfissionaisServico(Long servicoId){
        List<Profissional> profissional = profissionalRepository.findByServicos_Id(servicoId);

        return profissional.stream()
                .map( p ->  new ProfissionalResponseDTO(
                        p.getId(),
                        p.getNome(),
                        p.getUsuario().getId(),
                        p.getDescricao(),
                        p.getPrestador() != null ? p.getPrestador().getId() : null,
                        p.getServicos()
                                .stream()
                                .map(Servico::getId)
                                .toList(),
                        p.getAtendeADomicilio())

                ).toList();
    }

    public ProfissionalResponseDTO buscarPorUsuarioId(Long usuarioId){
        Profissional profissional = profissionalRepository.findByUsuario_Id(usuarioId)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        return mapperDTO(profissional);
    }


    public void vincularProfissional(Long profissionalId, Long prestadorId) {


        Profissional profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        if(profissionalRepository.existsProfissionals(profissionalId)){
            throw new RuntimeException("Profissinal já vinculado a empresa");
        }

        Prestador prestador = prestadorRepository.findById(prestadorId)
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));

        profissional.setPrestador(prestador);
        profissionalRepository.save(profissional);

    }
}
