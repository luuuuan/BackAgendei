package br.unipar.devbackend.agendei.service;

import br.unipar.devbackend.agendei.DTO.create.PagamentoConfirmaCreateDTO;
import br.unipar.devbackend.agendei.DTO.create.PagamentoIntentCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.PagamentoIntentResponseDTO;
import br.unipar.devbackend.agendei.DTO.response.PagamentoResponseDTO;
import br.unipar.devbackend.agendei.entity.Agendamento;
import br.unipar.devbackend.agendei.entity.Pagamento;
import br.unipar.devbackend.agendei.enums.StatusPagamento;
import br.unipar.devbackend.agendei.repository.AgendamentoRepository;
import br.unipar.devbackend.agendei.repository.PagamentoRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.RefundCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PagamentoService {
    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    public PagamentoIntentResponseDTO criarIntent(PagamentoIntentCreateDTO pagamentoIntentCreateDTO) {
        Agendamento agendamento = agendamentoRepository.findById(pagamentoIntentCreateDTO.getAgendamentoId())
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        try {

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(pagamentoIntentCreateDTO.getValor().multiply(BigDecimal.valueOf(100)).longValue())
                    .setCurrency("brl")
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

            return new PagamentoIntentResponseDTO(intent.getClientSecret(), agendamento.getId());

        } catch (StripeException e) {
            throw new RuntimeException("Erro ao criar pagamento no Stripe: " + e.getMessage());
        }
    }

    public PagamentoResponseDTO confirmarPagamento(
            PagamentoConfirmaCreateDTO pagamentoConfirmaCreateDTO){

        if (pagamentoConfirmaCreateDTO.getPaymentIntentId() == null) {
            throw  new RuntimeException("Dados inválidos");
        }

        Agendamento agendamento = agendamentoRepository.findById(pagamentoConfirmaCreateDTO.getAgendamentoId())
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));


        Pagamento pagamento = new Pagamento();

        pagamento.setValor(pagamentoConfirmaCreateDTO.getValor());
        pagamento.setStatusPagamento(StatusPagamento.APROVADO);
        pagamento.setFormaPgto(pagamentoConfirmaCreateDTO.getFormaPgto());
        pagamento.setIdTransacaoStripe(pagamentoConfirmaCreateDTO.getPaymentIntentId());
        pagamento.setDataPgto(LocalDateTime.now());
        pagamento.setAgendamento(agendamento);


        pagamentoRepository.save(pagamento);

        return new PagamentoResponseDTO(
                pagamento.getId(),
                pagamento.getValor(),
                pagamento.getStatusPagamento(),
                pagamento.getFormaPgto(),
                pagamento.getIdTransacaoStripe(),
                pagamento.getDataPgto(),
                pagamento.getDataReembolso(),
                pagamento.getAgendamento().getId()
        );
    }

    public PagamentoResponseDTO reembolsoAgendamento(Long agendamentoId){
        Pagamento pagamento = pagamentoRepository.findByAgendamentoId(agendamentoId)
                .orElseThrow(() -> new RuntimeException("Dados do pagamento não encontrado"));


        Agendamento agendamento = agendamentoRepository.findById(agendamentoId)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));


        try{

            StripeClient client = new StripeClient(stripeSecretKey);
            RefundCreateParams params =
                    RefundCreateParams.builder().setPaymentIntent(pagamento.getIdTransacaoStripe()).build();


            Refund refund = client.refunds().create(params);

            pagamento.setStatusPagamento(StatusPagamento.REEMBOLSADO);
            pagamento.setIdReembolsoStripe(refund.getId());
            pagamento.setDataReembolso(LocalDateTime.now());
            pagamentoRepository.save(pagamento);

            return new PagamentoResponseDTO(
                    pagamento.getId(),
                    pagamento.getValor(),
                    pagamento.getStatusPagamento(),
                    pagamento.getFormaPgto(),
                    pagamento.getIdTransacaoStripe(),
                    pagamento.getDataPgto(),
                    pagamento.getDataReembolso(),
                    pagamento.getAgendamento().getId()
            );
        }catch(StripeException e){
            throw new RuntimeException("Erro ao realizar operação: " + e.getMessage());
        }
    }

    public byte[] gerarComprovante(Long agendamentoId){
        Pagamento pagamento = pagamentoRepository.findByAgendamentoId(agendamentoId)
                .orElseThrow(() -> new RuntimeException("Dados de pagamento não encontrado"));

        Agendamento agendamento = pagamento.getAgendamento();

        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            document.add(new Paragraph("Comprovante de pagamento"));
            document.add(new Paragraph("Dados do agendamento"));
            document.add(new Paragraph("Data de pagamento: " + pagamento.getDataPgto()));
            document.add(new Paragraph("Valor: R$" + pagamento.getValor()));
            document.add(new Paragraph("Forma de pagamento: " + pagamento.getFormaPgto()));
            document.add(new Paragraph("Dados da transação: " + pagamento.getIdTransacaoStripe()));
            document.add(new Paragraph("Serviço contratado:" + agendamento.getServicos()));

            document.close();
            return baos.toByteArray();

        }catch (Exception e){
            throw new RuntimeException("Erro ao gerar comprovante: " + e.getMessage());
        }

    }

}
