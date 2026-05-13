package br.unipar.devbackend.agendei.service;


import br.unipar.devbackend.agendei.DTO.create.PrestadorCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.PrestadorResponseDTO;
import br.unipar.devbackend.agendei.DTO.response.ProfissionalResponseDTO;
import br.unipar.devbackend.agendei.entity.Prestador;
import br.unipar.devbackend.agendei.entity.Profissional;
import br.unipar.devbackend.agendei.entity.Servico;
import br.unipar.devbackend.agendei.entity.Usuario;
import br.unipar.devbackend.agendei.repository.PrestadorRepository;
import br.unipar.devbackend.agendei.repository.ProfissionalRepository;
import br.unipar.devbackend.agendei.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestadorService {

    @Autowired
    private PrestadorRepository prestadorRepository;
    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    public PrestadorResponseDTO cadastroPrestador(PrestadorCreateDTO  prestadorCreateDTO){

        List<Servico> servicos = servicoRepository
                .findAllById(prestadorCreateDTO.getServicosId());



        Prestador prestador = new Prestador();

        prestador.setEspecialidade(prestadorCreateDTO.getEspecialidade());
        prestador.setServico(servicos);

        prestadorRepository.save(prestador);

        List<Long> servicosIds = prestador.getServico()
                .stream()
                .map(Servico::getId)
                .toList();

        return new PrestadorResponseDTO(
                prestador.getId(),
                prestador.getEspecialidade(),
                servicosIds,
                prestador.getUsuario().getId()


        );
    }





}
