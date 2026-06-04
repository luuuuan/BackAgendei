package br.unipar.devbackend.agendei.service;

import br.unipar.devbackend.agendei.DTO.create.AvaliacaoCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.AvaliacaoResponseDTO;
import br.unipar.devbackend.agendei.entity.Agendamento;
import br.unipar.devbackend.agendei.entity.Avaliacao;
import br.unipar.devbackend.agendei.entity.Profissional;
import br.unipar.devbackend.agendei.entity.Usuario;
import br.unipar.devbackend.agendei.repository.AgendamentoRepository;
import br.unipar.devbackend.agendei.repository.AvaliacaoRepository;
import br.unipar.devbackend.agendei.repository.ProfissionalRepository;
import br.unipar.devbackend.agendei.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvaliacaoService {

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public AvaliacaoResponseDTO avaliarServico(AvaliacaoCreateDTO  avaliacaoCreateDTO) {

        Usuario usuario = usuarioRepository.findById(avaliacaoCreateDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Agendamento agendamento = agendamentoRepository.findById(avaliacaoCreateDTO.getAgendamentoId())
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        //Profissional profissional = profissionalRepository.findById(avaliacaoCreateDTO.getProfissionalId())
                //.orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        if (avaliacaoRepository.existsByAgendamentoIdAndUsuarioId(avaliacaoCreateDTO.getAgendamentoId(),
                avaliacaoCreateDTO.getUsuarioId())){
            throw new RuntimeException("Agendamento já avaliado!");
        }

        Avaliacao avaliacao = new Avaliacao();

        avaliacao.setNota(avaliacaoCreateDTO.getNota());
        avaliacao.setComentario(avaliacaoCreateDTO.getComentario());
        avaliacao.setAgendamento(agendamento);
        avaliacao.setUsuario(usuario);
        //avaliacao.setProfissional(profissional);
        


        avaliacaoRepository.save(avaliacao);

        return new AvaliacaoResponseDTO(
                avaliacao.getId(),
                avaliacao.getNota(),
                avaliacao.getComentario(),
                avaliacao.getAgendamento().getId(),
                //avaliacao.getProfissional().getId(),
                avaliacao.getUsuario().getId()
        );
    }

    public List<AvaliacaoResponseDTO> listarAvaliacoes(){

        List<Avaliacao> avaliacaos = avaliacaoRepository.findAll();


        return avaliacaos.stream()
                .map(a -> new AvaliacaoResponseDTO(
                        a.getId(),
                        a.getNota(),
                        a.getComentario(),
                        a.getAgendamento().getId(),
                                //a.getProfissional() != null ? a.getProfissional().getId() : null,
                        a.getUsuario().getId()
                )).toList();
    }

}
