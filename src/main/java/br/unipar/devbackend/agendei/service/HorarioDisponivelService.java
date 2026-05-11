package br.unipar.devbackend.agendei.service;


import br.unipar.devbackend.agendei.DTO.create.HorarioDisponivelDTO;
import br.unipar.devbackend.agendei.DTO.response.HorarioDisponivelResponseDTO;
import br.unipar.devbackend.agendei.entity.HorarioDisponivel;
import br.unipar.devbackend.agendei.entity.Profissional;
import br.unipar.devbackend.agendei.entity.Servico;
import br.unipar.devbackend.agendei.enums.StatusHorario;
import br.unipar.devbackend.agendei.repository.HorarioDisponivelRepository;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class HorarioDisponivelService {

    @Autowired
    private HorarioDisponivelRepository horarioDisponivelRepository;

    public List<String> disponibilidade(
            Long profissionalId, LocalDate dataAgendamento){

        List<HorarioDisponivel> horarioDisponivelList =
                horarioDisponivelRepository.findByProfissionalIdAndData(profissionalId, dataAgendamento);

        if (horarioDisponivelList.isEmpty()){
            throw new RuntimeException("Nenhum horário cadastrado para o profissional selecionado");
        }

        return horarioDisponivelList.stream()
                .filter(h -> h.getStatusHorario() == StatusHorario.DISPONIVEL)
                .map(h -> h.getHoraInicio().toString()
                ).toList();
    }


    public HorarioDisponivelResponseDTO cadastrarHorario(
            HorarioDisponivelDTO horarioDisponivelDTO){
        //HorarioDisponivel horarioDisponivel = horarioDisponivelRepository.findBy

        HorarioDisponivel horarioDisponivel = new HorarioDisponivel();

        horarioDisponivel.setData(horarioDisponivelDTO.getData());
        horarioDisponivel.setHoraInicio(horarioDisponivelDTO.getHoraInicio());
        horarioDisponivel.setHoraFim(horarioDisponivelDTO.getHoraFim());
        horarioDisponivel.setStatusHorario(StatusHorario.DISPONIVEL);
        horarioDisponivel.setProfissional(horarioDisponivelDTO.getProfissional());
        horarioDisponivel.setServico(horarioDisponivelDTO.getServico());

        horarioDisponivelRepository.save(horarioDisponivel);

        return new HorarioDisponivelResponseDTO(
                horarioDisponivel.getId(),
                horarioDisponivel.getData(),
//                horarioDisponivel.getHoraInicio(),
//                horarioDisponivel.getHoraFim(),
//                horarioDisponivel.getStatus(),
                horarioDisponivel.getProfissional().getId()
//                horarioDisponivel.getServico()
        );
/*
        private LocalDate data;

        private LocalTime horaInicio;

        private LocalTime horaFim;

        private Boolean status;

        @ManyToOne
        @JoinColumn(name = "profissional_id")
        private Profissional profissional;

        @ManyToOne
        @JoinColumn(name = "servico_id")
        private Servico servico;*/
    }
}
