package br.unipar.devbackend.agendei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AgendeiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgendeiApplication.class, args);
    }

}
