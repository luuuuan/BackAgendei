package br.unipar.devbackend.agendei.service;

import br.unipar.devbackend.agendei.DTO.create.AgendamentoCreateDTO;
import br.unipar.devbackend.agendei.DTO.create.AgendamentoPesquisaDTO;
import br.unipar.devbackend.agendei.DTO.response.AgendamentoPesquisaResponseDTO;
import br.unipar.devbackend.agendei.DTO.response.AgendamentoResponseDTO;
import br.unipar.devbackend.agendei.entity.*;
import br.unipar.devbackend.agendei.enums.StatusAgendamento;
import br.unipar.devbackend.agendei.enums.StatusHorario;
import br.unipar.devbackend.agendei.enums.TipoCobranca;
import br.unipar.devbackend.agendei.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private  GradeTrabalhoRepository gradeTrabalhoRepository;
    @Autowired
    private FolgaRepository folgaRepository;

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
                agendamento.getEndereco() != null ? agendamento.getEndereco().getId() : null,
                agendamento.getPrestadorId()
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

        BigDecimal valorTotalAgendamento = servicos.stream().map(s -> {
            if (s.getTipoCobranca() == TipoCobranca.FIXO) {
                return s.getValorServico();
            } else {
                return s.getValorServico().multiply(BigDecimal.valueOf(agendamentoCreateDTO.getQuantidade()));
            }
        }).reduce(BigDecimal.ZERO, BigDecimal::add);

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
        agendamento.setPrestadorId(profissional.getPrestador().getId());

        agendamentoRepository.save(agendamento);

        marcarHorarioIndisponivel(
                agendamentoCreateDTO.getProfissionalId(),
                agendamentoCreateDTO.getDataAgendamento(),
                agendamentoCreateDTO.getHoraInicio());

        return mapperDTO(agendamento);
    }

    private void marcarHorarioIndisponivel(
            Long profissionalId,
            LocalDate dataAgendamento,
            LocalTime horaInicio) {
        if(profissionalId == null || dataAgendamento == null || horaInicio == null ) return;

        Optional<HorarioDisponivel> horarioDisponivelOptional = horarioDisponivelRepository
                .findByProfissionalIdAndData(profissionalId, dataAgendamento)
                .stream()
                .filter(h -> h.getHoraInicio().equals(horaInicio))
                .findFirst();
        horarioDisponivelOptional.ifPresent(h -> {
            h.setStatusHorario(StatusHorario.INDISPONIVEL);
            horarioDisponivelRepository.save(h);
        });

    }



    public List<AgendamentoResponseDTO>
    buscaAgendamento(AgendamentoPesquisaDTO agendamentoPesquisaDTO) {
        List<Agendamento> agendamentos =
                agendamentoRepository.findByDataCriacao(agendamentoPesquisaDTO.getDataCriacao());


        return agendamentos.stream()
                .map(this::mapperDTO).toList();

    }


    public List<AgendamentoResponseDTO>
            buscarPorUsuario(AgendamentoPesquisaDTO agendamentoPesquisaDTO) {
        List<Agendamento> agendamentos =
                agendamentoRepository.findByUsuarioId(agendamentoPesquisaDTO.getUsuarioId());



        return agendamentos.stream()
                .map(this::mapperDTO)
                .toList();
    }


    public List<String>
        buscarHorariosDisponiveis(Long profissionalId, LocalDate data, Long servicoId) {

        List<GradeTrabalho> listaGrade = gradeTrabalhoRepository.findByProfissionalId(profissionalId);

        Optional<GradeTrabalho> gradeAtiva = listaGrade.stream().filter(g -> g.getAtivo() == true).findFirst();

        GradeTrabalho gradeTrabalho = gradeAtiva.orElseThrow(() -> new RuntimeException("Nenhuma grade ativa encontrada"));

        if(data.getDayOfWeek().getValue() > gradeTrabalho.getDiaFim().getValue() ||
                data.getDayOfWeek().getValue() < gradeTrabalho.getDiaFim().getValue()){
            return new ArrayList<>();
        }
        //===============================================================
        Servico servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new RuntimeException("Servico não encontrado"));

        Integer duracao = servico.getDuracaoMinutos();

        Boolean folgaProfissional = folgaRepository.existsByProfissionalIdAndData(profissionalId, data);

        if(folgaProfissional == true){
            return new ArrayList<>();
        }

        //===============================




        return horarioDisponivelRepository.findByProfissionalIdAndData(profissionalId, data)
                        .stream()
                        .filter(h -> h.getStatusHorario() == StatusHorario.DISPONIVEL)
                        .map(h -> h.getHoraInicio().toString())
                        .toList();


    }

    public AgendamentoResponseDTO atualizaStatus(Long agendamentoId, String status){
        Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() -> new RuntimeException("Nenhum agendamento encontrado"));

        agendamento.setStatusAgendamento(StatusAgendamento.valueOf(status));
        agendamento.setDataConfirmacao(LocalDate.from(LocalDateTime.now()));

        if(StatusAgendamento.valueOf(status) == StatusAgendamento.CANCELADO ){
            agendamento.setDataCancelamento(LocalDateTime.from(LocalDateTime.now()));
            liberarHorario(agendamento);
        }

        agendamentoRepository.save(agendamento);

        return mapperDTO(agendamento);

    }

    private void liberarHorario(Agendamento agendamento){
        if(agendamento.getProfissional() == null || agendamento.getDataAgendamento() == null
        || agendamento.getHoraInicio() == null) return;

        Optional<HorarioDisponivel> horarioDisponivel = horarioDisponivelRepository
                .findByProfissionalIdAndData(agendamento.getProfissional().getId(), agendamento.getDataAgendamento())
                .stream()
                .filter(h -> h.getHoraInicio().equals(agendamento.getHoraInicio()))
                .findFirst();

        horarioDisponivel.ifPresent(h -> {
           h.setStatusHorario(StatusHorario.DISPONIVEL);
           horarioDisponivelRepository.save(h);
        });
    }

    public List<AgendamentoResponseDTO> agendamentosPrestaor(Long prestadorId){
        List<Agendamento> agendamento = agendamentoRepository.findByPrestadorId(prestadorId);


        return agendamento.stream()
                .map(this::mapperDTO)
                .toList();

    }

}
