package br.unipar.devbackend.agendei.service;

import br.unipar.devbackend.agendei.DTO.create.AgendamentoCreateDTO;
import br.unipar.devbackend.agendei.DTO.create.AgendamentoPesquisaDTO;
import br.unipar.devbackend.agendei.DTO.response.AgendamentoPesquisaResponseDTO;
import br.unipar.devbackend.agendei.DTO.response.AgendamentoResponseDTO;
import br.unipar.devbackend.agendei.entity.*;
import br.unipar.devbackend.agendei.enums.StatusAgendamento;
import br.unipar.devbackend.agendei.enums.StatusHorario;
import br.unipar.devbackend.agendei.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AgendamentoService {
    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HorarioDisponivelRepository horarioDisponivelRepository;

    public AgendamentoResponseDTO mapperDTO(Agendamento agendamento){

        return new  AgendamentoResponseDTO(
                agendamento.getId(),
                agendamento.getDataAgendamento(),
                agendamento.getDataCriacao(),
                agendamento.getHoraInicio(),
                agendamento.getHoraFim(),
                agendamento.getDataConfirmacao(),
                agendamento.getStatusAgendamento(),
                agendamento.getValorTotal(),
                agendamento.getObservacoes(),
                agendamento.getUsuario() != null ? agendamento.getUsuario().getId() : null,
                agendamento.getProfissional().getId(),
                agendamento.getServicos()
                        .stream()
                        .map(Servico::getId)
                        .toList(),
                agendamento.getEndereco() != null ? agendamento.getEndereco().getId() : null
        );

    }


    public AgendamentoResponseDTO criarAgendamento(AgendamentoCreateDTO agendamentoCreateDTO) {
        Usuario usuario = agendamentoCreateDTO.getUsuarioId() != null ?
                usuarioRepository
                .findById(agendamentoCreateDTO.getUsuarioId())
                .orElse(null) : null;

        Profissional profissional = profissionalRepository
                .findById(agendamentoCreateDTO.getProfissionalId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        List<Servico> servicos = servicoRepository.findAllById(agendamentoCreateDTO.getServicos());

        if (servicos.size() != agendamentoCreateDTO.getServicos().size()) {
            throw new RuntimeException("Um ou mais serviços não foram encontrados");
        }

        Endereco endereco = agendamentoCreateDTO.getEnderecoId() != null ?
                enderecoRepository.findById(agendamentoCreateDTO.getEnderecoId())
                .orElse(null) : null;

        BigDecimal valorTotalAgendamento = servicos.stream()
                .map(Servico::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Agendamento agendamento = new Agendamento();

        agendamento.setDataAgendamento(agendamentoCreateDTO.getDataAgendamento());
        agendamento.setDataCriacao(agendamentoCreateDTO.getDataCriacao());
        agendamento.setHoraInicio(agendamentoCreateDTO.getHoraInicio());
        agendamento.setHoraFim(agendamentoCreateDTO.getHoraFim());
        agendamento.setDataConfirmacao(agendamentoCreateDTO.getDataConfirmacao());
        agendamento.setStatusAgendamento(StatusAgendamento.PENDENTE);
        agendamento.setTaxaPlataforma(agendamentoCreateDTO.getTaxaPlataforma());
        agendamento.setValorTotal(valorTotalAgendamento);
        agendamento.setObservacoes(agendamentoCreateDTO.getObservacoes());
        agendamento.setUsuario(usuario);
        agendamento.setProfissional(profissional);
        agendamento.setServicos(servicos);
        agendamento.setEndereco(endereco);


        agendamentoRepository.save(agendamento);
        return mapperDTO(agendamento);
    }



    public List<AgendamentoResponseDTO>
    buscaAgendamento(AgendamentoPesquisaDTO agendamentoPesquisaDTO) {
        List<Agendamento> agendamentos =
                agendamentoRepository.findByDataCriacao(agendamentoPesquisaDTO.getDataCriacao());


        return agendamentos.stream()
                .map( a -> new AgendamentoResponseDTO(
                        a.getId(),
                        a.getDataAgendamento(),
                        a.getDataCriacao(),
                        a.getHoraInicio(),
                        a.getHoraFim(),
                        a.getDataConfirmacao(),
                        a.getStatusAgendamento(),
                        a.getValorTotal(),
                        a.getObservacoes(),
                        a.getUsuario().getId(),
                        a.getProfissional().getId(),
                        a.getServicos()
                                .stream()
                                .map(Servico::getId)
                                .toList(),
                        a.getEndereco().getId()

                )).toList();

    }


    public List<AgendamentoResponseDTO>
            buscarPorUsuario(AgendamentoPesquisaDTO agendamentoPesquisaDTO) {
        List<Agendamento> agendamentos =
                agendamentoRepository.findByUsuarioId(agendamentoPesquisaDTO.getUsuarioId());

//        if(agendamentos.isEmpty()){
//            throw new RuntimeException("Nenhum agendamento realizado.");
//        }

        return agendamentos.stream()
                .map(this::mapperDTO)
                .toList();
    }


    public List<String>
        buscarHorariosDisponiveis(Long profissionalId, LocalDate data) {

        return horarioDisponivelRepository.findByProfissionalIdAndData(profissionalId, data)
                        .stream()
                        .filter(h -> h.getStatusHorario() == StatusHorario.DISPONIVEL)
                        .map(h -> h.getHoraInicio().toString())
                        .toList();


    }

}
