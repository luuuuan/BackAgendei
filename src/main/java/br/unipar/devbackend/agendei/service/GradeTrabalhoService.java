package br.unipar.devbackend.agendei.service;


import br.unipar.devbackend.agendei.DTO.create.GradeTrabalhoCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.GradeTrabalhoResponseDTO;
import br.unipar.devbackend.agendei.entity.GradeTrabalho;
import br.unipar.devbackend.agendei.entity.Profissional;
import br.unipar.devbackend.agendei.repository.GradeTrabalhoRepository;
import br.unipar.devbackend.agendei.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
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
        DayOfWeek inicio;
        DayOfWeek fim;

        if(gradeTrabalhoCreateDTO.getDiasSemana().equals("SEG_SEX")){
            inicio = DayOfWeek.MONDAY;
            fim = DayOfWeek.FRIDAY;
        } else if (gradeTrabalhoCreateDTO.getDiasSemana().equals("SEG_SAB")) {
            inicio = DayOfWeek.MONDAY;
            fim = DayOfWeek.SATURDAY;
        }else{
            inicio = DayOfWeek.MONDAY;
            fim = DayOfWeek.SUNDAY;
        }

        GradeTrabalho gradeTrabalho = new GradeTrabalho();

        gradeTrabalho.setProfissional(profissional);
        gradeTrabalho.setHoraInicio(gradeTrabalhoCreateDTO.getHoraInicio());
        gradeTrabalho.setHoraFim(gradeTrabalhoCreateDTO.getHoraFim());
        gradeTrabalho.setDiaInicio(inicio);
        gradeTrabalho.setDiaFim(fim);
        gradeTrabalho.setInicioIntervalo(gradeTrabalhoCreateDTO.getInicioIntervalo());
        gradeTrabalho.setFimIntervalo(gradeTrabalhoCreateDTO.getFimIntervalo());
        gradeTrabalho.setAtivo(gradeTrabalhoCreateDTO.getAtivo());

        gradeTrabalhoRepository.save(gradeTrabalho);

        return new GradeTrabalhoResponseDTO(
                gradeTrabalho.getId(),
                gradeTrabalho.getProfissional().getId(),
                gradeTrabalho.getDiaInicio(),
                gradeTrabalho.getDiaFim(),
                gradeTrabalho.getHoraInicio(),
                gradeTrabalho.getHoraFim(),
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
                                gradeTrabalho.getHoraInicio(),
                                gradeTrabalho.getHoraFim(),
                                gradeTrabalho.getInicioIntervalo(),
                                gradeTrabalho.getFimIntervalo(),
                                gradeTrabalho.getAtivo()))
                .toList();


    }
	
	public GradeTrabalhoResponseDTO atualizarGrade(Long id, GradeTrabalhoCreateDTO gradeTrabalhoCreateDTO){
		GradeTrabalho gradeTrabalho = gradeTrabalhoRepository.findByIdAndProfissionalId(id, gradeTrabalhoCreateDTO.getProfissionalId())
			.orElseThrow(()-> new RuntimeException("Grade de trabalho não encontrada"));
	
        gradeTrabalho.setHoraInicio(gradeTrabalhoCreateDTO.getHoraInicio());
        gradeTrabalho.setHoraFim(gradeTrabalhoCreateDTO.getHoraFim());
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
                gradeTrabalho.getHoraInicio(),
                gradeTrabalho.getHoraFim(),
                gradeTrabalho.getInicioIntervalo(),
                gradeTrabalho.getFimIntervalo(),
                gradeTrabalho.getAtivo()
		);
	}

}
