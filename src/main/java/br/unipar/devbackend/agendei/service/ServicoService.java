package br.unipar.devbackend.agendei.service;

import br.unipar.devbackend.agendei.DTO.create.ServicoCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.ProfissionalResponseDTO;
import br.unipar.devbackend.agendei.DTO.response.ServicoResponseDTO;
import br.unipar.devbackend.agendei.DTO.response.ServicoResultadoConsultaDTO;
import br.unipar.devbackend.agendei.entity.Profissional;
import br.unipar.devbackend.agendei.entity.Servico;
import br.unipar.devbackend.agendei.repository.ProfissionalRepository;
import br.unipar.devbackend.agendei.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;
    @Autowired
    private ProfissionalRepository profissionalRepository;

    public ServicoResponseDTO criarServico(ServicoCreateDTO servicoCreateDTO){
        Profissional profissional = profissionalRepository
                .findById(servicoCreateDTO.getProfissionalId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        Servico servico = new Servico();

        servico.setDuracaoMinutos(servicoCreateDTO.getDuracaoMinutos());
        servico.setTempoBuffer(servicoCreateDTO.getTempoBuffer());
        servico.setStatusServico(servicoCreateDTO.getStatusServico());
        servico.setStatusExecucaoServico(servicoCreateDTO.getStatusExecucaoServico());
        servico.setStatusExecucaoServico(servicoCreateDTO.getStatusExecucaoServico());
        servico.setValor(servicoCreateDTO.getValor());
        servico.setProfissional(profissional);
        servico.setNome(servicoCreateDTO.getNome());
        servico.setDescricao(servicoCreateDTO.getDescricao());

        servicoRepository.save(servico);

        return new ServicoResponseDTO(
                servico.getId(),
                servico.getDuracaoMinutos(),
                servico.getTempoBuffer(),
                servico.getStatusServico(),
                servico.getValor(),
                servico.getProfissional().getId(),
                servico.getNome(),
                servico.getDescricao()

        );
    }



    public  List<ServicoResultadoConsultaDTO> listarServicoProfissional(Long profissionalId) {

        List<Servico> servicosProfissional = servicoRepository.findByProfissionalId(profissionalId);

        if (servicosProfissional.isEmpty()) {
            throw new RuntimeException("Nenhum serviço encontrado para o profissional informado.");
        }

        return servicosProfissional.stream()
                .map(s -> new ServicoResultadoConsultaDTO(
                        s.getDuracaoMinutos(),
                        s.getValor(),
                        s.getProfissional().getId(),
                        s.getNome(),
                        s.getDescricao()

                        )

                ).toList();
    }
    public List<ServicoResponseDTO> listarServicos(){

        List<Servico> servicos = servicoRepository.findAll();

        return servicos.stream()
                .map(s -> new ServicoResponseDTO(
                        s.getId(),
                        s.getDuracaoMinutos(),
                        s.getTempoBuffer(),
                        s.getStatusServico(),
                        s.getValor(),
                        s.getProfissional().getId(),
                        s.getNome(),
                        s.getDescricao()
                        ))
                .toList();
    }
}
