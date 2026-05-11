package br.unipar.devbackend.agendei.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.url}")
    private String appUrl;

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
