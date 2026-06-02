package br.unipar.devbackend.agendei.scheduler;


import br.unipar.devbackend.agendei.DTO.response.PagamentoResponseDTO;
import br.unipar.devbackend.agendei.entity.Agendamento;
import br.unipar.devbackend.agendei.entity.Pagamento;
import br.unipar.devbackend.agendei.enums.StatusAgendamento;
import br.unipar.devbackend.agendei.enums.StatusPagamento;
import br.unipar.devbackend.agendei.repository.AgendamentoRepository;
import br.unipar.devbackend.agendei.repository.PagamentoRepository;
import br.unipar.devbackend.agendei.service.EmailService;
import br.unipar.devbackend.agendei.service.WhatsAppService;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import com.stripe.param.RefundCreateParams;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class AgendamentoScheduler {
    @Autowired
    private final AgendamentoRepository agendamentoRepository;

    @Autowired
    private final PagamentoRepository pagamentoRepository;

    @Autowired
    private final EmailService emailService;

    @Autowired
    private final WhatsAppService whatsAppService;

    @Value("${app.notificacao.antecedencia-minutos:120}")
    private int antecedenciaMinutos;

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @Transactional
    @Scheduled(fixedRate = 60 * 1000)
    public void notificarAgendamentos(){

        System.out.println("inicio do envio de notificacoes");
        LocalDate hoje = LocalDate.now();

        LocalTime agoraHora =  LocalTime.now();

        LocalTime inicio = agoraHora.plusMinutes(antecedenciaMinutos - 2);

        LocalTime fim = agoraHora.plusMinutes(antecedenciaMinutos + 2);

        System.out.println("inicio: " + inicio + " fim " + fim);

        StatusAgendamento statusAgendamento = StatusAgendamento.CONFIRMADO;

        List<Agendamento> agendamentos = agendamentoRepository
                .findByDataAgendamentoAndHoraInicioBetweenAndStatusAgendamentoAndNotificacaoEnviadaFalse(hoje, inicio, fim, statusAgendamento);


        System.out.println("Total de agendamentos: " + agendamentos.size());
        if(agendamentos.isEmpty()) return;

        log.info("{} agendamentos para notificar", agendamentos.size());

        for(Agendamento agendamento : agendamentos){
            System.out.println("Entrou no loop");
                emailService.enviarAgendamento(agendamento);
                whatsAppService.enviarAgendamento(agendamento);

                agendamento.setNotificacaoEnviada(true);
                //agendamentoRepository.save(agendamento);

        }

    }

    @Scheduled(fixedRate = 60 * 1000)
    public void atualizaStatusAgendamento(){

        LocalDate agoraDia = LocalDate.now();

        LocalTime agoraHora = LocalTime.now().minusHours(2);


        StatusAgendamento statusAgendamento = StatusAgendamento.PENDENTE;

        List<Agendamento> agendamentos = agendamentoRepository.findByStatusAgendamento(statusAgendamento);

        if (!agendamentos.isEmpty()){

            for(Agendamento agendamento : agendamentos){

                if (agoraDia.isEqual(agendamento.getDataAgendamento()) &&
                        agoraHora.isAfter(agendamento.getHoraInicio())) {
                    Pagamento pagamento = pagamentoRepository.findByAgendamentoId(agendamento.getId())
                            .orElse(null);

                    if(pagamento != null){
                        try{

                            StripeClient client = new StripeClient(stripeSecretKey);
                            RefundCreateParams params =
                                    RefundCreateParams.builder().setPaymentIntent(pagamento.getIdTransacaoStripe()).build();


                            Refund refund = client.refunds().create(params);

                            pagamento.setStatusPagamento(StatusPagamento.REEMBOLSADO);
                            pagamento.setIdReembolsoStripe(refund.getId());
                            pagamento.setDataReembolso(LocalDateTime.now());
                            pagamentoRepository.save(pagamento);


                        }catch(StripeException e){
                            log.error("Erro ao realizar operação: {}", e.getMessage());
                        }
                    }

                    agendamento.setStatusAgendamento(StatusAgendamento.CANCELADO);
                    agendamento.setMotivoCancelamento("Prestador não confirmou agendamento");

                    agendamentoRepository.save(agendamento);
                    log.info("{}agendamento cancelado com ",
                            agendamento.getProfissional().getUsuario().getNome());
                }

            }

        }else {
            System.out.println("nenhum agendamento encontrado");
            log.info("nenhum agendamento encontrado");
        }



    }


}
