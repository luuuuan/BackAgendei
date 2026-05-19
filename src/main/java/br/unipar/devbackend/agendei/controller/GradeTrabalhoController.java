package br.unipar.devbackend.agendei.controller;


import br.unipar.devbackend.agendei.DTO.create.GradeTrabalhoCreateDTO;
import br.unipar.devbackend.agendei.DTO.response.GradeTrabalhoResponseDTO;
import br.unipar.devbackend.agendei.service.GradeTrabalhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gradeTrabalho")
public class GradeTrabalhoController {

    @Autowired
    private GradeTrabalhoService gradeTrabalhoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<GradeTrabalhoResponseDTO> jornadaTrabalho(
            @RequestBody GradeTrabalhoCreateDTO gradeTrabalhoCreateDTO
            ){
        GradeTrabalhoResponseDTO gradeTrabalhoResponseDTO = gradeTrabalhoService.cadastraJornada(gradeTrabalhoCreateDTO);

        return ResponseEntity.ok(gradeTrabalhoResponseDTO);
    }


   @GetMapping("/profissional/{profissionalId}")
    public ResponseEntity<List<GradeTrabalhoResponseDTO>> profissionalTrabalhoGrade(
            @PathVariable Long profissionalId
   ){
        List<GradeTrabalhoResponseDTO> gradeTrabalhoResponseDTO =
                gradeTrabalhoService.gradeTrabalhoProfissional(profissionalId);

        return ResponseEntity.ok(gradeTrabalhoResponseDTO);

   }
   
   @PutMapping("/atualizar/{id}")
   public ResponseEntity<GradeTrabalhoResponseDTO> atualizar(
			@PathVariable Long id,
			@RequestBody GradeTrabalhoCreateDTO gradeTrabalhoCreateDTO){
		GradeTrabalhoResponseDTO gradeTrabalhoResponseDTO =
				gradeTrabalhoService.atualizarGrade(id, gradeTrabalhoCreateDTO);

		return ResponseEntity.ok(gradeTrabalhoResponseDTO);
}
