package br.unipar.devbackend.agendei.service;


import br.unipar.devbackend.agendei.entity.Agendamento;
import br.unipar.devbackend.agendei.entity.Endereco;
import br.unipar.devbackend.agendei.entity.Servico;
import br.unipar.devbackend.agendei.entity.Usuario;
import br.unipar.devbackend.agendei.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Value("${app.url}")
    private String appUrl;



//    @Value("${spring.mail.username}")
//    private String remetente;



    public void confirmarConta(String email, String token){

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        String link = appUrl + "/confirmar-conta?token=" + token + "&email" + usuario.getEmail();

        String texto = String.format("""
            Olá, %s!
            
            Seu cadastro no Agendei foi realizado com sucesso.
            
            Para confirmar sua conta e ativar seu acesso, clique no link abaixo:
            
            %s
            
            ⚠️ Este link expira em 2 horas.
            
            Se você não realizou este cadastro, apenas ignore este e-mail.
            
            Equipe Agendei 💜
            """,
                usuario.getNome(),
                link);


        SimpleMailMessage mensagem = new  SimpleMailMessage();
        mensagem.setFrom("luanzxcvbnm558@gmail.com");
        mensagem.setTo(email);
        mensagem.setSubject("Agendei - Confirmar Conta");
        mensagem.setText(texto);

        mailSender.send(mensagem);
    }


    public void enviarAgendamento(Agendamento agendamento){
        String nome;

        nome = agendamento.getProfissional() != null ? agendamento.getProfissional().getNome()
                : agendamento.getPrestador().getUsuario().getNome();

        try{
            SimpleMailMessage message = new SimpleMailMessage();
            //message.setFrom(remetente);
            message.setFrom("luanzxcvbnm558@gmail.com");

            message.setTo(agendamento.getUsuario().getEmail());
            message.setSubject("Lembrete de agendamento com " + nome);
            message.setText(montarMensagem(agendamento));

            mailSender.send(message);
            log.info("Email enviado para: {}", agendamento.getUsuario().getEmail());

            System.out.println("Email enviado para : " + agendamento.getUsuario().getEmail());
        }catch (Exception e){
            log.error("Erro ao enviar email: {}", e.getMessage());
        }

    }

    public String montarMensagem(Agendamento agendamento){
        System.out.println("Enviando email");

        return String.format("""
                Olá, %s!
                
                Você tem um agendamento em breve:
                
                Endereço:
                📌 %s, %s, %s
                🕐 %s às %s
                
                Serviço a ser realizado:
                📝 %s
                
                Descrição: %s
                
                Até logo!
                
                Obs: Você pode cancelar o agendamento
                com até duas horas de antecedência!
                """,
                agendamento.getUsuario().getNome(),
                agendamento.getEndereco().getLogradouro(),
                agendamento.getEndereco().getNumero(),
                agendamento.getEndereco().getBairro(),
                agendamento.getDataAgendamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                agendamento.getHoraInicio().format(DateTimeFormatter.ofPattern("HH:mm")),
                agendamento.getServicos().stream().map(Servico::getNome).toList(),
                agendamento.getServicos().stream().map(Servico::getDescricao).toList()

        );


    }

    public void enviarRecuperacao(String email, String token){
        String link = appUrl + "/redefinir-senha?token=" + token + "&email=" + email;

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
