package br.unipar.devbackend.agendei.service;

import br.unipar.devbackend.agendei.DTO.create.ServicoCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.ProfissionalResponseDTO;
import br.unipar.devbackend.agendei.DTO.response.ServicoResponseDTO;
import br.unipar.devbackend.agendei.DTO.response.ServicoResultadoConsultaDTO;
import br.unipar.devbackend.agendei.DTO.response.UsuarioResponseDTO;
import br.unipar.devbackend.agendei.entity.Prestador;
import br.unipar.devbackend.agendei.entity.Profissional;
import br.unipar.devbackend.agendei.entity.Servico;
import br.unipar.devbackend.agendei.entity.Usuario;
import br.unipar.devbackend.agendei.repository.PrestadorRepository;
import br.unipar.devbackend.agendei.repository.ProfissionalRepository;
import br.unipar.devbackend.agendei.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private PrestadorRepository prestadorRepository;

    public ServicoResponseDTO mapperDTO(Servico servico){

        return new ServicoResponseDTO(
                servico.getId(),
                servico.getDuracaoMinutos(),
                servico.getTempoBuffer(),
                servico.getStatusServico(),
                servico.getStatusExecucaoServico(),
                servico.getProfissional() != null ? servico.getProfissional().getId() : null,
                servico.getProfissional() != null ? servico.getProfissional().getNome() : null,
                servico.getNome(),
                servico.getDescricao(),
                servico.getTipoCobranca(),
                servico.getLocalAtendimento(),
                servico.getValorServico(),
                servico.getPrestador() != null ? servico.getPrestador().getId() : null,
                servico.getPrestador() != null && servico.getPrestador().getUsuario() != null
                        ? servico.getPrestador().getUsuario().getNome()
                        : null
        );

    }
    public ServicoResponseDTO criarServico(ServicoCreateDTO servicoCreateDTO){

        Profissional profissional = null;

        Prestador prestador = null;

        if(servicoCreateDTO.getProfissionalId() != null ){
            profissional = profissionalRepository.findById(servicoCreateDTO.getProfissionalId())
                            .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        } else{
            prestador = prestadorRepository.findById(servicoCreateDTO.getPrestadorId())
                            .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));
        }

        Servico servico = new Servico();

        servico.setDuracaoMinutos(servicoCreateDTO.getDuracaoMinutos());
        servico.setTempoBuffer(servicoCreateDTO.getTempoBuffer());
        servico.setStatusServico(servicoCreateDTO.getStatusServico());
        servico.setStatusExecucaoServico(servicoCreateDTO.getStatusExecucaoServico());
        servico.setStatusExecucaoServico(servicoCreateDTO.getStatusExecucaoServico());
        servico.setValorServico(servicoCreateDTO.getValorServico());
        servico.setProfissional(profissional);
        servico.setNome(servicoCreateDTO.getNome());
        servico.setDescricao(servicoCreateDTO.getDescricao());
        servico.setTipoCobranca(servicoCreateDTO.getTipoCobranca());
        servico.setLocalAtendimento(servicoCreateDTO.getLocalAtendimento());
        servico.setDataCriacao(LocalDate.now());
        servico.setPrestador(prestador);

        servicoRepository.save(servico);

        return mapperDTO(servico);


    }



    public  List<ServicoResultadoConsultaDTO>
    listarServicoProfissional(Long profissionalId) {

        List<Servico> servicos = servicoRepository.findByProfissionalId(profissionalId);

        if (servicos.isEmpty()) {
            throw new RuntimeException("Nenhum serviço encontrado para o profissional informado.");
        }

        return servicos.stream()
                .map(s -> new ServicoResultadoConsultaDTO(
                        s.getDuracaoMinutos(),
                        s.getValorServico(),
                        s.getProfissional().getId(),
                        s.getNome(),
                        s.getDescricao())
                ).toList();
    }

    public List<ServicoResponseDTO> listarServicos(Long prestadorId){
        List<Servico> servicos;

        if (prestadorId == null){
            servicoRepository.findAll();

        }

        List<Servico> porPrestador = servicoRepository.findByPrestadorId(prestadorId);

        List<Long> profissionaisIds = profissionalRepository.findByPrestador_Id(prestadorId)
                .stream().map(Profissional::getId).toList();

        List<Servico> porProfissional = servicoRepository.findByProfissionalIdIn(profissionaisIds);


        servicos = new ArrayList<>();
        servicos.addAll(porPrestador);
        servicos.addAll(porProfissional);

        return servicos.stream()
                .map(this::mapperDTO)
                .toList();
    }

    public ServicoResponseDTO atualizarServico(Long id, ServicoCreateDTO servicoCreateDTO){
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicço não encontrado"));

        if(servicoCreateDTO.getNome() != null) servico.setNome(servicoCreateDTO.getNome());
        if(servicoCreateDTO.getDescricao() != null) servico.setDescricao(servicoCreateDTO.getDescricao());
        if(servicoCreateDTO.getValorServico() != null) servico.setValorServico(servicoCreateDTO.getValorServico());
        if(servicoCreateDTO.getDuracaoMinutos() != null) servico.setDuracaoMinutos(servicoCreateDTO.getDuracaoMinutos());
        if(servicoCreateDTO.getTempoBuffer() != null) servico.setTempoBuffer(servicoCreateDTO.getTempoBuffer());
        if(servicoCreateDTO.getStatusServico() != null) servico.setStatusServico(servicoCreateDTO.getStatusServico());
        if(servicoCreateDTO.getProfissionalId() != null) {
            Profissional profissional = profissionalRepository.findById(servicoCreateDTO.getProfissionalId())
                    .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
            servico.setProfissional(profissional);
        }
        servicoRepository.save(servico);

        return mapperDTO(servico);

    }

}
