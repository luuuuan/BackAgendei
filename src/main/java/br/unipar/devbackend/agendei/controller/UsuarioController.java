package br.unipar.devbackend.agendei.controller;

import br.unipar.devbackend.agendei.DTO.create.UsuarioAtualizaCreateDTO;
import br.unipar.devbackend.agendei.DTO.create.UsuarioCreateDTO;
import br.unipar.devbackend.agendei.DTO.create.UsuarioLoginDTO;
import br.unipar.devbackend.agendei.DTO.response.UsuarioAtualizaResponseDTO;
import br.unipar.devbackend.agendei.DTO.response.UsuarioLoginResponseDTO;
import br.unipar.devbackend.agendei.DTO.response.UsuarioResponseDTO;
import br.unipar.devbackend.agendei.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }


    @PatchMapping("/atualizar-cliente/{usuarioId}")
    public ResponseEntity<UsuarioAtualizaResponseDTO> atualizar(
            @Valid @RequestBody UsuarioAtualizaCreateDTO usuarioAtualizaCreateDTO,
            @PathVariable Long usuarioId){

        UsuarioAtualizaResponseDTO usuarioAtualizaResponseDTO = usuarioService.atualizarCliente(usuarioId, usuarioAtualizaCreateDTO);

        return ResponseEntity.ok(usuarioAtualizaResponseDTO);
    }




    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioResponseDTO> criar(
            @Valid @RequestBody UsuarioCreateDTO usuarioCreateDTO){
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.criarUsuario(usuarioCreateDTO);


        return ResponseEntity.ok(usuarioResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody UsuarioLoginDTO usuarioLoginDTO) {
        UsuarioLoginResponseDTO usuarioLoginResponseDTO = usuarioService.logar(usuarioLoginDTO);

        if (usuarioLoginResponseDTO == null) {
            return ResponseEntity.status(401).body("Credenciais inválidas");
        }

        return ResponseEntity.ok(usuarioLoginResponseDTO);
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<UsuarioResponseDTO>> listarClientes(
            @RequestParam (required = false) String cpf,
            @RequestParam (required = false) Long prestadorId)
    {
        List<UsuarioResponseDTO> clientes = usuarioService.listar(cpf, prestadorId);
        return ResponseEntity.ok(clientes);
    }

//    @GetMapping("/clientes")
//    public ResponseEntity<List<UsuarioResponseDTO>> listarClientes() {
//        List<UsuarioResponseDTO> clientes = usuarioService.listar();
//        return ResponseEntity.ok(clientes);
//    }

    @GetMapping("/buscar")
    public ResponseEntity<UsuarioResponseDTO> buscar(
            @RequestParam (required = false) String cpf,
            @RequestParam (required = false) String email){
        UsuarioResponseDTO usuario;

        if( cpf != null  ){
            usuario = usuarioService.buscarPorCpf(cpf);
        }else if(email != null){
            usuario = usuarioService.buscarPorEmail(email);
        }else{
            return ResponseEntity.badRequest().build();
        }


        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/recuperarSenha")
    public ResponseEntity<?> recuperarSenha(@RequestBody Map<String, String> body) {
        usuarioService.solicitarRecuperacao(body.get("email"));
        return ResponseEntity.ok(Map.of("mensagem", "Email enviado!"));


    }

    @PostMapping("/redefinirSenha")
    public ResponseEntity<?> redefinirSenha(@RequestBody Map<String, String> body) {
        usuarioService.redefinirSenha(body.get("token"), body.get("novaSenha"));
        return  ResponseEntity.ok(Map.of("mensagem", "Senha redefinida!"));

    }

}
