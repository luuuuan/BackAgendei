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
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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


        Profissional profissional = null;

        Prestador prestador = null;
        if(folgaCreateDTO.getProfissionalId()!=null) {
            profissional = profissionalRepository.findById(folgaCreateDTO.getProfissionalId())
                    .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
        }else{
            prestador = prestadorRepository.findById(folgaCreateDTO.getPrestadorId())
                    .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));
        }


        LocalDate diaAtual = LocalDate.now();

        if(folgaCreateDTO.getData().isBefore(diaAtual)) {
            throw new RuntimeException("Dia da folga não pode ser anterior ao atual");
        }

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
                folga.getProfissional() != null ? folga.getProfissional().getId() : null,
                folga.getPrestador() != null ? folga.getPrestador().getId() : null,
                folga.getDiaInteiro(),
                folga.getHoraInicio(),
                folga.getHoraFim(),
                folga.getMotivo()

        );

    }


    public List<FolgaResponseDTO> buscaFolgaProfissional(Long profissionalId){

        List<Folga> folga = folgaRepository.findByProfissionalId(profissionalId);

        return folga.stream()
                .filter(Folga::getAtivo)
                .map( f -> new FolgaResponseDTO(
                        f.getId(),
                        f.getData(),
                        f.getProfissional() != null ? f.getProfissional().getId() : null,
                        f.getPrestador() != null ? f.getPrestador().getId() : null,
                        f.getDiaInteiro(),
                        f.getHoraInicio(),
                        f.getHoraFim(),
                        f.getMotivo()
                )).toList();


    }

    public List<FolgaResponseDTO> buscaFolgaPrestador(Long prestadorId){

        List<Folga> folga = folgaRepository.findByPrestadorId(prestadorId);

        return folga.stream()
                .filter(Folga::getAtivo)
                .map( f -> new FolgaResponseDTO(
                        f.getId(),
                        f.getData(),
                        f.getProfissional() != null ? f.getProfissional().getId() : null,
                        f.getPrestador() != null ? f.getPrestador().getId() : null,
                        f.getDiaInteiro(),
                        f.getHoraInicio(),
                        f.getHoraFim(),
                        f.getMotivo()
                )).toList();


    }

    public void atualizaFolga(Long id){
        Folga folga = folgaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Folga inexistente!"));

        LocalDate diaAtual = LocalDate.now();

        if(folga.getData().isBefore(diaAtual)) {
            throw new RuntimeException("Não é possível excluir folgas passadas");
        }


        folga.setAtivo(false);

        folgaRepository.save(folga);
    }
}
