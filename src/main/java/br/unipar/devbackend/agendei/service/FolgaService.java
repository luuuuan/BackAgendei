package br.unipar.devbackend.agendei.service;

import br.unipar.devbackend.agendei.DTO.create.FolgaCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.FolgaResponseDTO;
import br.unipar.devbackend.agendei.entity.Folga;
import br.unipar.devbackend.agendei.entity.Profissional;
import br.unipar.devbackend.agendei.repository.FolgaRepository;
import br.unipar.devbackend.agendei.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FolgaService {

    @Autowired
    private FolgaRepository folgaRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    public FolgaResponseDTO criarFolga(FolgaCreateDTO  folgaCreateDTO) {

        Profissional profissional = profissionalRepository.findById(folgaCreateDTO.getProfissionalId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));


        Folga folga = new Folga();

        folga.setData(folgaCreateDTO.getData());
        folga.setProfissional(profissional);
        folga.setDiaInteiro(folgaCreateDTO.getDiaInteiro());
        folga.setHoraInicio(folgaCreateDTO.getHoraInicio());
        folga.setHoraFim(folgaCreateDTO.getHoraFim());

        folgaRepository.save(folga);

        return new  FolgaResponseDTO(
                folga.getId(),
                folga.getData(),
                folga.getProfissional().getId(),
                folga.getDiaInteiro(),
                folga.getHoraInicio(),
                folga.getHoraFim(),
                folga.getMotivo()
        );

    }
}
