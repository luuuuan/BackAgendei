package br.unipar.devbackend.agendei.service;


import br.unipar.devbackend.agendei.DTO.create.GradeTrabalhoCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.GradeTrabalhoResponseDTO;
import br.unipar.devbackend.agendei.entity.GradeTrabalho;
import br.unipar.devbackend.agendei.entity.Profissional;
import br.unipar.devbackend.agendei.repository.GradeTrabalhoRepository;
import br.unipar.devbackend.agendei.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeTrabalhoService {
    @Autowired
    private GradeTrabalhoRepository  gradeTrabalhoRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    public GradeTrabalhoResponseDTO cadastraJornada(GradeTrabalhoCreateDTO  gradeTrabalhoCreateDTO) {


        Profissional profissional = profissionalRepository.findById(gradeTrabalhoCreateDTO.getProfissionalId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        GradeTrabalho gradeTrabalho = new GradeTrabalho();

        gradeTrabalho.setProfissional(profissional);
        gradeTrabalho.setHorarioInicio(gradeTrabalhoCreateDTO.getHorarioInicio());
        gradeTrabalho.setHorarioFim(gradeTrabalhoCreateDTO.getHorarioFim());
        gradeTrabalho.setDiaInicio(gradeTrabalhoCreateDTO.getDiaInicio());
        gradeTrabalho.setDiaFim(gradeTrabalhoCreateDTO.getDiaFim());
        gradeTrabalho.setInicioIntervalo(gradeTrabalhoCreateDTO.getInicioIntervalo());
        gradeTrabalho.setFimIntervalo(gradeTrabalhoCreateDTO.getFimIntervalo());
        gradeTrabalho.setAtivo(gradeTrabalhoCreateDTO.getAtivo());

        gradeTrabalhoRepository.save(gradeTrabalho);

        return new GradeTrabalhoResponseDTO(
                gradeTrabalho.getId(),
                gradeTrabalho.getProfissional().getId(),
                gradeTrabalho.getDiaInicio(),
                gradeTrabalho.getDiaFim(),
                gradeTrabalho.getHorarioInicio(),
                gradeTrabalho.getHorarioFim(),
                gradeTrabalho.getInicioIntervalo(),
                gradeTrabalho.getFimIntervalo(),
                gradeTrabalho.getAtivo()


        );

    }

    public List<GradeTrabalhoResponseDTO> gradeTrabalhoProfissional(Long profissionalId){
        List<GradeTrabalho> gradeTrabalhoLista = gradeTrabalhoRepository.findByProfissionalId(profissionalId);



        return gradeTrabalhoLista.stream()
                        .map( gradeTrabalho ->  new GradeTrabalhoResponseDTO(
                                gradeTrabalho.getId(),
                                gradeTrabalho.getProfissional().getId(),
                                gradeTrabalho.getDiaInicio(),
                                gradeTrabalho.getDiaFim(),
                                gradeTrabalho.getHorarioInicio(),
                                gradeTrabalho.getHorarioFim(),
                                gradeTrabalho.getInicioIntervalo(),
                                gradeTrabalho.getFimIntervalo(),
                                gradeTrabalho.getAtivo()))
                .toList();


    }

}
