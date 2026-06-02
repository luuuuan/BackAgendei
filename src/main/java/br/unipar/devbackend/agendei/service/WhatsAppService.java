package br.unipar.devbackend.agendei.service;

import br.unipar.devbackend.agendei.entity.Agendamento;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class WhatsAppService {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.whatsapp-from}")
    private String from;

    @PostConstruct
    public void init(){
        Twilio.init(accountSid, authToken);
    }

    public void enviarAgendamento(Agendamento agendamento){
        try{
            String para = "whatsapp:" + agendamento.getUsuario().getTelefone();

            Message.creator(
                    new PhoneNumber(para),
                    new PhoneNumber(from),
                    montarMensagem(agendamento)
            ).create();
        log.info("Agendamento enviado com sucesso para {}", agendamento.getUsuario().getTelefone());
        }catch (Exception e){
            log.error("Erro ao enviar mensagem para: {}", e.getMessage());
        }
    }

    private String montarMensagem(Agendamento agendamento){

        return String.format("""
                    Olá, %s!
                
                Você tem um agendamento em breve:
                📌 %s
                🕐 %s às 4%s
                📝 %s
                
                Até logo!
                
                Obs: Você pode cancelar o agendamento
                com até duas horas de antecedência
                """,
                agendamento.getUsuario().getNome(),
                agendamento.getEndereco(),
                agendamento.getDataAgendamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                agendamento.getHoraInicio().format(DateTimeFormatter.ofPattern("HH:mm")),
                agendamento.getServicos());
    }


}
