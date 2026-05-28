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
import lombok.RequiredArgsConstructor;
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

    private AgendamentoRepository agendamentoRepository;
    private PagamentoRepository pagamentoRepository;
    private EmailService emailService;
    private WhatsAppService whatsAppService;

    @Value("${app.notificacao.antecedencia-minutos:60}")
    private int antecedenciaMinutos;

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    @Scheduled(fixedRate = 5 * 60 * 1000)
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

    @Scheduled(fixedRate = 60 * 1000)
    public void atualizaStatusAgendamento(){
        LocalDate agoraDia = LocalDate.now();

        LocalTime agoraHora = LocalTime.now().minusHours(2);

        StatusAgendamento statusAgendamento = StatusAgendamento.PENDENTE;

        List<Agendamento> agendamentos = agendamentoRepository.findByStatusAgendamento(statusAgendamento);


        if (agendamentos.isEmpty()) return;

        for(Agendamento agendamento : agendamentos){
            if (agoraDia.isEqual(agendamento.getDataAgendamento()) &&
                    agoraHora.isAfter(agendamento.getHoraFim())) {

                Pagamento pagamento = pagamentoRepository.findByAgendamentoId(agendamento.getId())
                        .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

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
                    log.error("{}Erro ao realizar operação: ", e.getMessage());
                }

                agendamento.setStatusAgendamento(StatusAgendamento.CANCELADO);
                agendamento.setMotivoCancelamento("Prestador não confirmou agendamento");

                agendamentoRepository.save(agendamento);

                log.info("{}{}{}agendamento cancelado ", agendamento.getId(), " com ",
                        agendamento.getProfissional().getUsuario().getNome());
            }

        }

    }


}
