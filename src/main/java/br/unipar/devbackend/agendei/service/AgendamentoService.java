package br.unipar.devbackend.agendei.service;

import br.unipar.devbackend.agendei.DTO.create.AgendamentoCreateDTO;
import br.unipar.devbackend.agendei.DTO.create.AgendamentoPesquisaDTO;
import br.unipar.devbackend.agendei.DTO.create.PagamentoConfirmaCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.AgendamentoPesquisaResponseDTO;
import br.unipar.devbackend.agendei.DTO.response.AgendamentoResponseDTO;
import br.unipar.devbackend.agendei.entity.*;
import br.unipar.devbackend.agendei.enums.StatusAgendamento;
import br.unipar.devbackend.agendei.enums.StatusHorario;
import br.unipar.devbackend.agendei.enums.TipoCobranca;
import br.unipar.devbackend.agendei.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
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

    @Autowired
    private PrestadorRepository prestadorRepository;

    @Autowired
    private PagamentoService pagamentoService;

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
                agendamento.getProfissional() != null ? agendamento.getProfissional().getId() : null,
                agendamento.getServicos()
                        .stream()
                        .map(Servico::getId)
                        .toList(),
                agendamento.getEndereco() != null ? agendamento.getEndereco().getId() : null,
                agendamento.getPrestador() != null ? agendamento.getPrestador().getId() : null
        );

    }

    @Transactional
    public AgendamentoResponseDTO criarAgendamento(AgendamentoCreateDTO agendamentoCreateDTO) {
        Usuario usuario = agendamentoCreateDTO.getUsuarioId() != null ?
                usuarioRepository
                .findById(agendamentoCreateDTO.getUsuarioId())
                .orElse(null) : null;

        Profissional profissional = null;

        Prestador prestador = null;

        if(agendamentoCreateDTO.getProfissionalId() != null){
            profissional = profissionalRepository
                    .findById(agendamentoCreateDTO.getProfissionalId())
                    .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
        }else{
            prestador = prestadorRepository.
                    findById(agendamentoCreateDTO.getPrestadorId())
                    .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));

        }

        List<Servico> servicos = servicoRepository.findAllById(agendamentoCreateDTO.getServicos());


        if (servicos.size() != agendamentoCreateDTO.getServicos().size()) {
            throw new RuntimeException("Um ou mais serviços não foram encontrados");
        }

        int duracaoTotal = servicos.stream()
                .mapToInt(Servico::getDuracaoMinutos)
                .sum();

        LocalTime horaFim = agendamentoCreateDTO.getHoraInicio().plusMinutes(duracaoTotal);


        Endereco endereco = agendamentoCreateDTO.getEnderecoId() != null ?
                enderecoRepository.findById(agendamentoCreateDTO.getEnderecoId())
                .orElse(null) : null;

        double quantidade = agendamentoCreateDTO.getQuantidade() != null
                ? agendamentoCreateDTO.getQuantidade()
                : 1.0;

        /*
        COMENTADO POIS SERA USADO
        VALOR DO SERVIÇO DE FORMA INDIVIDUAL POR HORA,
        POIS NAO SERA POSSIVEL AGENDAR MAIS DE UM SERVIÇO

        BigDecimal valorTotalAgendamento = servicos.stream().map(s -> {
            if (s.getTipoCobranca() == TipoCobranca.FIXO) {
                return s.getValorServico();
            } else {
                return s.getValorServico().multiply(BigDecimal.valueOf(quantidade));
            }
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
*/
        Agendamento agendamento = new Agendamento();

        agendamento.setDataAgendamento(agendamentoCreateDTO.getDataAgendamento());
        agendamento.setDataCriacao(agendamentoCreateDTO.getDataCriacao());
        agendamento.setHoraInicio(agendamentoCreateDTO.getHoraInicio());
        agendamento.setHoraFim(horaFim);
        agendamento.setDataConfirmacao(agendamentoCreateDTO.getDataConfirmacao());
        agendamento.setStatusAgendamento(StatusAgendamento.PENDENTE);
        agendamento.setTaxaPlataforma(agendamentoCreateDTO.getTaxaPlataforma());
        agendamento.setValorTotal(agendamentoCreateDTO.getValorTotal());
        agendamento.setObservacoes(agendamentoCreateDTO.getObservacoes());
        agendamento.setUsuario(usuario);
        agendamento.setProfissional(profissional);
        agendamento.setServicos(servicos);
        agendamento.setEndereco(endereco);
        agendamento.setPrestador(prestador);
        agendamento.setNotificacaoEnviada(false);

        agendamentoRepository.save(agendamento);

        marcarHorarioIndisponivel(
                agendamentoCreateDTO.getProfissionalId(),
                agendamentoCreateDTO.getDataAgendamento(),
                agendamentoCreateDTO.getHoraInicio());

        if (agendamentoCreateDTO.getPaymentIntentId() != null) {
            PagamentoConfirmaCreateDTO pgto = new PagamentoConfirmaCreateDTO();
            pgto.setAgendamentoId(agendamento.getId());
            pgto.setPaymentIntentId(agendamentoCreateDTO.getPaymentIntentId());
            pgto.setValor(agendamentoCreateDTO.getValorTotal());
            pgto.setFormaPgto(agendamentoCreateDTO.getFormaPgto());
            pagamentoService.confirmarPagamento(pgto);
            agendamentoRepository.save(agendamento);
        }

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
    buscaAgendamento(LocalDate dataAgendamento) {
        List<Agendamento> agendamentos =
                agendamentoRepository.findByDataAgendamento(dataAgendamento);


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
    buscarHorariosDisponiveis(Long profissionalId, LocalDate data, Long servicoId, Long prestadorId) {
        LocalDate diaAtual = LocalDate.now();

        Servico servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        if (profissionalId == null){
            prestadorId = servico.getPrestador().getId();
        }

        List<GradeTrabalho> listaGrade = profissionalId == null || profissionalId == 0 ?
                gradeTrabalhoRepository.findByPrestadorId(prestadorId) :
                gradeTrabalhoRepository.findByProfissionalId(profissionalId);


        Optional<GradeTrabalho> gradeAtiva = listaGrade.stream().filter(g -> g.getAtivo() == true).findFirst();

        GradeTrabalho gradeTrabalho = gradeAtiva.orElseThrow(() -> new RuntimeException("Nenhuma grade ativa encontrada"));

        List<Agendamento> agendamentos = profissionalId != null ?
                agendamentoRepository.findByProfissionalIdAndDataAgendamento(profissionalId, data) :
                agendamentoRepository.findByPrestadorIdAndDataAgendamento(prestadorId, data);


        if(data.getDayOfWeek().getValue() > gradeTrabalho.getDiaFim().getValue() ||
                data.getDayOfWeek().getValue() < gradeTrabalho.getDiaInicio().getValue()){
            return new ArrayList<>();
        }
        //===============================================================


        Integer duracao = servico.getDuracaoMinutos();

        Boolean folga = profissionalId != null ?
                folgaRepository.existsByProfissionalIdAndData(profissionalId, data) :
                folgaRepository.existsByPrestadorIdAndData(prestadorId, data);

        if(folga == true) return new ArrayList<>();

        //===============================

        List<String> horarioDisponivel = new ArrayList<>();
        int inicio = gradeTrabalho.getHoraInicio().getHour() * 60 + gradeTrabalho.getHoraInicio().getMinute();

        int fim = gradeTrabalho.getHoraFim().getHour() * 60 + gradeTrabalho.getHoraFim().getMinute();





        for (int minutos = inicio; minutos + duracao < fim; minutos += duracao) {

            LocalTime horaAtual = LocalTime.now();

            int horasConvertido = minutos / 60;

            int minutosConvertido = minutos % 60;

            LocalTime tempo = LocalTime.of(horasConvertido, minutosConvertido);

            if(tempo.isBefore(horaAtual.plusHours(2)) && data.isEqual(diaAtual)){
                continue;
            }

            if (tempo.equals(horaAtual)) {
                continue;
            }

            if(gradeTrabalho.getInicioIntervalo() != null
                    && tempo.isAfter(gradeTrabalho.getInicioIntervalo())
                    && tempo.isBefore(gradeTrabalho.getFimIntervalo())){
                continue;
            }

            LocalTime slotFim = LocalTime.of((minutos + duracao) / 60, (minutos + duracao) % 60);

            boolean temConflito = agendamentos.stream().anyMatch(a ->
                    a.getHoraInicio().isBefore(slotFim) && a.getHoraFim().isAfter(tempo)
            );

            if (temConflito) {
                continue;
            }

            horarioDisponivel.add(tempo.toString());
        }


        return horarioDisponivel;


    }

    public AgendamentoResponseDTO atualizaStatus(Long agendamentoId, String status){
        Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() -> new RuntimeException("Nenhum agendamento encontrado"));

        agendamento.setStatusAgendamento(StatusAgendamento.valueOf(status));
        agendamento.setDataConfirmacao(LocalDate.from(LocalDateTime.now()));

        if(StatusAgendamento.valueOf(status) == StatusAgendamento.REAGENDADO ){
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
