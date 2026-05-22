package br.unipar.devbackend.agendei.service;

import br.unipar.devbackend.agendei.DTO.create.DadosBancariosCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.DadosBancariosResponseDTO;
import br.unipar.devbackend.agendei.entity.Banco;
import br.unipar.devbackend.agendei.entity.DadosBancarios;
import br.unipar.devbackend.agendei.entity.Prestador;
import br.unipar.devbackend.agendei.repository.BancoRepository;
import br.unipar.devbackend.agendei.repository.DadosBancariosRepository;
import br.unipar.devbackend.agendei.repository.PrestadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DadosBancariosService {
    @Autowired
    private DadosBancariosRepository dadosBancariosRepository;

    @Autowired
    private BancoRepository bancoRepository;

    @Autowired
    private PrestadorRepository prestadorRepository;

    public DadosBancariosResponseDTO cadastrarDadosBancarios(DadosBancariosCreateDTO dadosBancariosCreateDTO) {

        Banco banco = bancoRepository.findById(dadosBancariosCreateDTO.getBancoId())
                .orElseThrow(()-> new RuntimeException("Banco não encontrado"));


        Prestador prestador = prestadorRepository.findById(dadosBancariosCreateDTO.getPrestadorId())
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));

        DadosBancarios dadosBancarios = new DadosBancarios();

        dadosBancarios.setBanco(banco);
        dadosBancarios.setAgencia(dadosBancariosCreateDTO.getAgencia());
        dadosBancarios.setConta(dadosBancariosCreateDTO.getConta());
        dadosBancarios.setDigitoConta(dadosBancariosCreateDTO.getDigitoConta());
        dadosBancarios.setTipoConta(dadosBancariosCreateDTO.getTipoConta());
        dadosBancarios.setCpfTitular(dadosBancariosCreateDTO.getCpfTitular());
        dadosBancarios.setNomeTitular(dadosBancariosCreateDTO.getNomeTitular());
        dadosBancarios.setPrestador(prestador);

        dadosBancariosRepository.save(dadosBancarios);

        return new DadosBancariosResponseDTO(
                dadosBancarios.getId(),
                dadosBancarios.getBanco().getId(),
                dadosBancarios.getAgencia(),
                dadosBancarios.getConta(),
                dadosBancarios.getDigitoConta(),
                dadosBancarios.getTipoConta(),
                dadosBancarios.getCpfTitular(),
                dadosBancarios.getNomeTitular(),
                dadosBancarios.getPrestador().getId()

        );

    }

    public DadosBancariosResponseDTO atualizaConta(DadosBancariosCreateDTO dadosBancariosCreateDTO, Long id) {
        boolean dadoBancario = dadosBancariosRepository.existsById(id);

        if (!dadoBancario) {
            throw new RuntimeException("Dados bancários não encontrado");
        }

        DadosBancarios dadosBancarios = dadosBancariosRepository.findByPrestadorId(id)
                .orElseThrow(() -> new RuntimeException("Dados bancários não encontrado"));

        Banco banco = bancoRepository.findById(dadosBancariosCreateDTO.getBancoId())
                .orElseThrow(()-> new RuntimeException("Banco não encontrado"));


        Prestador prestador = prestadorRepository.findById(dadosBancariosCreateDTO.getPrestadorId())
                .orElseThrow(() -> new RuntimeException("Prestador não encontrado"));

        dadosBancarios.setBanco(banco);
        dadosBancarios.setAgencia(dadosBancariosCreateDTO.getAgencia());
        dadosBancarios.setConta(dadosBancariosCreateDTO.getConta());
        dadosBancarios.setDigitoConta(dadosBancariosCreateDTO.getDigitoConta());
        dadosBancarios.setTipoConta(dadosBancariosCreateDTO.getTipoConta());
        dadosBancarios.setCpfTitular(dadosBancariosCreateDTO.getCpfTitular());
        dadosBancarios.setNomeTitular(dadosBancariosCreateDTO.getNomeTitular());
        dadosBancarios.setPrestador(prestador);


        dadosBancariosRepository.save(dadosBancarios);
        return new DadosBancariosResponseDTO(
                dadosBancarios.getId(),
                dadosBancarios.getBanco().getId(),
                dadosBancarios.getAgencia(),
                dadosBancarios.getConta(),
                dadosBancarios.getDigitoConta(),
                dadosBancarios.getTipoConta(),
                dadosBancarios.getCpfTitular(),
                dadosBancarios.getNomeTitular(),
                dadosBancarios.getPrestador().getId()

        );
    }

    public DadosBancariosResponseDTO buscarConta(Long id){
        DadosBancarios dadosBancarios = dadosBancariosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dados bancários não encontrado"));

        return new DadosBancariosResponseDTO(
                dadosBancarios.getId(),
                dadosBancarios.getBanco().getId(),
                dadosBancarios.getAgencia(),
                dadosBancarios.getConta(),
                dadosBancarios.getDigitoConta(),
                dadosBancarios.getTipoConta(),
                dadosBancarios.getCpfTitular(),
                dadosBancarios.getNomeTitular(),
                dadosBancarios.getPrestador().getId()

        );
    }

}
