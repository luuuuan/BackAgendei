package br.unipar.devbackend.agendei.service;

import br.unipar.devbackend.agendei.DTO.response.BancoResponseDTO;
import br.unipar.devbackend.agendei.entity.Banco;
import br.unipar.devbackend.agendei.repository.BancoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BancoService {
    @Autowired
    private BancoRepository bancoRepository;

    public List<BancoResponseDTO> buscarBancos(){
        List<Banco> bancos = bancoRepository.findAll();

        return bancos.stream()
                .map(b -> new BancoResponseDTO(
                        b.getId(),
                        b.getCodigo(),
                        b.getNome()
                )).toList();
    }
}
