package br.unipar.devbackend.agendei.service;


import br.unipar.devbackend.agendei.entity.Agendamento;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.url}")
    private String appUrl;

    @Value("$spring.mail.username")
    private String remetente;
    private String nome;


    public void enviarAgendamento(Agendamento agendamento){
        nome = agendamento.getProfissional() != null ? agendamento.getProfissional().getNome()
                : agendamento.getPrestador().getUsuario().getNome();
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(remetente);
            message.setTo(agendamento.getUsuario().getEmail());
            message.setSubject("Lembrete de agendamento com " + nome);
            message.setText(montarMensagem(agendamento));

            mailSender.send(message);
        }catch (Exception e){
            log.error("Erro ao enviar email: {}", e.getMessage());
        }

    }

    public String montarMensagem(Agendamento agendamento){
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
                agendamento.getDataAgendamento(),
                agendamento.getHoraInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                agendamento.getServicos()
        );


    }

    public void enviarRecuperacao(String email, String token){
        String link = appUrl + "/redefinir-senha?token=" + token;

        SimpleMailMessage mensagem = new  SimpleMailMessage();
        mensagem.setFrom("luanzxcvbnm558@gmail.com");
        mensagem.setTo(email);
        mensagem.setSubject("Agendei - Redefinir Senha");
        mensagem.setText(
                "Solicitação de redefinição de senha. \n\n" +
                "Clique no link abaixo para criar uma nova senha: \n" +
                        link + "\n\n" +
                "Este link expira em 30 minutos. \n" +
                "Se você não pediu a troca de senha, apenas ignore esse e-mail"
        );

        mailSender.send(mensagem);
    }


}
