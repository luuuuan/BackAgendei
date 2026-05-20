package br.unipar.devbackend.agendei.service;

import br.unipar.devbackend.agendei.DTO.create.FolgaCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.FolgaResponseDTO;
import br.unipar.devbackend.agendei.entity.Folga;
import br.unipar.devbackend.agendei.entity.Prestador;
import br.unipar.devbackend.agendei.entity.Profissional;
import br.unipar.devbackend.agendei.repository.FolgaRepository;
import br.unipar.devbackend.agendei.repository.PrestadorRepository;
import br.unipar.devbackend.agendei.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolgaService {

    @Autowired
    private FolgaRepository folgaRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private PrestadorRepository prestadorRepository;

    public FolgaResponseDTO criarFolga(FolgaCreateDTO  folgaCreateDTO) {

        Profissional profissional = profissionalRepository.findById(folgaCreateDTO.getProfissionalId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        Prestador prestador = prestadorRepository.findById(folgaCreateDTO.getPrestadorId())
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));


        if (folgaRepository.existsByProfissionalIdAndData(
                folgaCreateDTO.getProfissionalId(),
                folgaCreateDTO.getData()) ||
                folgaRepository.existsByPrestadorIdAndData(
                        folgaCreateDTO.getPrestadorId(), folgaCreateDTO.getData())){
            throw new RuntimeException("Folga já cadastrada para essa data");
        }


        Folga folga = new Folga();

        folga.setData(folgaCreateDTO.getData());
        folga.setProfissional(profissional);
        folga.setPrestador(prestador);
        folga.setDiaInteiro(folgaCreateDTO.getDiaInteiro());
        folga.setHoraInicio(folgaCreateDTO.getHoraInicio());
        folga.setHoraFim(folgaCreateDTO.getHoraFim());
        folga.setAtivo(true);

        folgaRepository.save(folga);

        return new  FolgaResponseDTO(
                folga.getId(),
                folga.getData(),
                folga.getProfissional().getId(),
                folga.getPrestador().getId(),
                folga.getDiaInteiro(),
                folga.getHoraInicio(),
                folga.getHoraFim(),
                folga.getMotivo()

        );

    }


    public List<FolgaResponseDTO> buscaFolga(Long profissionalId){

        List<Folga> folga = folgaRepository.findByProfissionalId(profissionalId);

        return folga.stream()
                .filter(f -> f.getAtivo())
                .map( f -> new FolgaResponseDTO(
                f.getId(),
                f.getData(),
                f.getProfissional().getId(),
                f.getPrestador().getId(),
                f.getDiaInteiro(),
                f.getHoraInicio(),
                f.getHoraFim(),
                f.getMotivo()
                )).toList();


    }

    public void atualizaFolga(Long id){
        Folga folga = folgaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Folga inexistente!"));

        folga.setAtivo(false);
    }
}
