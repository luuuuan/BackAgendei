package br.unipar.devbackend.agendei.config;

import br.unipar.devbackend.agendei.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class ConfiguracaoSeguranca {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           CorsConfigurationSource corsConfigurationSource) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        // ── Públicas ────────────────────────────────────────────────
                        .requestMatchers(HttpMethod.POST, "/usuarios/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios/cadastro").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/cadastro").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios/confirmarConta").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios/contaConfirmada").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios/recuperarSenha").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios/redefinir-senha").permitAll()

                        // Exploração antes de login
                        .requestMatchers(HttpMethod.GET, "/servico/servicos").permitAll()
                        .requestMatchers(HttpMethod.GET, "/servico/servicosProfissional/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/profissional/profissionaisCadastrados").permitAll()
                        .requestMatchers(HttpMethod.GET, "/profissional/profissionalServico/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/horarioDisponivel/disponibilidade").permitAll()
                        .requestMatchers(HttpMethod.GET, "/agendamento/disponibilidade").permitAll()
                        .requestMatchers(HttpMethod.GET, "/avaliacao/listar").permitAll()
                        .requestMatchers(HttpMethod.GET, "/banco/listar").permitAll()
                        .requestMatchers(HttpMethod.GET, "/endereco/**").permitAll()

                        // ── ADMIN ────────────────────────────────────────────────────
                        .requestMatchers(HttpMethod.GET,   "/usuarios/todos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/usuarios/{id}/ativo").hasRole("ADMIN")

                        // ── PRESTADOR ────────────────────────────────────────────────
                        .requestMatchers("/prestador/**").hasAnyRole("PRESTADOR", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/profissional/cadastroProfissional").hasAnyRole("PRESTADOR", "ADMIN")
                        .requestMatchers(HttpMethod.GET,  "/profissional/usuario/{usuarioId}").hasAnyRole("PRESTADOR", "ADMIN")
                        .requestMatchers("/gradeTrabalho/**").hasAnyRole("PRESTADOR", "ADMIN")
                        .requestMatchers("/folga/**").hasAnyRole("PRESTADOR", "ADMIN")
                        .requestMatchers(HttpMethod.POST,  "/servico/cadastroServicos").hasAnyRole("PRESTADOR", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/servico/atualizar/**").hasAnyRole("PRESTADOR", "ADMIN")
                        .requestMatchers("/dadosBancarios/**").hasAnyRole("PRESTADOR", "ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/agendamento/atualizar-status/**").hasAnyRole("PRESTADOR", "ADMIN")
                        .requestMatchers(HttpMethod.GET,   "/agendamento/todos").hasAnyRole("PRESTADOR", "ADMIN")
                        .requestMatchers(HttpMethod.GET,   "/usuarios/clientes").hasAnyRole("PRESTADOR", "ADMIN")

                        // ── CLIENTE ──────────────────────────────────────────────────
                        .requestMatchers(HttpMethod.POST, "/avaliacao/cadastrar").hasRole("CLIENTE")

                        // ── Qualquer autenticado ──────────────────────────────────────
                        .requestMatchers(HttpMethod.POST, "/agendamento/criarAgendamento").authenticated()
                        .requestMatchers(HttpMethod.GET,  "/agendamento/usuario/**").authenticated()
                        .requestMatchers(HttpMethod.GET,  "/agendamento/consultaAgendamento").authenticated()
                        .requestMatchers("/pagamento/**").authenticated()
                        .requestMatchers("/preferenciasNotificacao/**").authenticated()
                        .requestMatchers(HttpMethod.GET,   "/usuarios/{id}").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/usuarios/atualizar-cliente/**").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/usuarios/{id}/senha").authenticated()
                        .requestMatchers(HttpMethod.GET,   "/usuarios/buscar").authenticated()

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}