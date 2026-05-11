package br.unipar.devbackend.agendei.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntime(RuntimeException ex) {
        return ResponseEntity.status(400).body(
                Map.of("erro", ex.getMessage())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneral(Exception ex) {
        return ResponseEntity.status(500).body(
                Map.of("erro", ex.getMessage())
        );
    }
}