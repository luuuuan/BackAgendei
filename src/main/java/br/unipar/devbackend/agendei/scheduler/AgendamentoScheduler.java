package br.unipar.devbackend.agendei.scheduler;


import br.unipar.devbackend.agendei.entity.Agendamento;
import br.unipar.devbackend.agendei.repository.AgendamentoRepository;
import br.unipar.devbackend.agendei.service.EmailService;
import br.unipar.devbackend.agendei.service.WhatsAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AgendamentoScheduler {

    private AgendamentoRepository agendamentoRepository;
    private EmailService emailService;
    private WhatsAppService whatsAppService;

    @Value("${app.notificacao.antecedencia-minutos:60}")
    private int antecedenciaMinutos;

    public void notificarAgendamentos(){
        LocalDateTime agora = LocalDateTime.now();

        LocalDateTime inicio = agora.plusMinutes(antecedenciaMinutos - 2);
        LocalDateTime fim = agora.plusMinutes(antecedenciaMinutos + 2);

        List<Agendamento> agendamentos = agendamentoRepository
                .findByDataAgendamentoBetweenAndNotificacaoEnviadaFalse(inicio, fim);

        if(agendamentos.isEmpty()) return;

        log.info("{} agendamentos para notificar", agendamentos.size());

        for(Agendamento agendamento : agendamentos){
                emailService.montarMensagem(agendamento);
                whatsAppService.enviarAgendamento(agendamento);

                agendamento.setNotificacaoEnviada(true);
                agendamentoRepository.save(agendamento);
        }

    }


}
