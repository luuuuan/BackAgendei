package br.unipar.devbackend.agendei.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String gerarToken(Long usuarioId, String email, String tipoUsuario, Long prestadorId){
        return Jwts.builder()
                .subject(email)
                .claim("usuarioId", usuarioId)
                .claim("tipoUsuario", tipoUsuario)
                .claim("prestadorId", prestadorId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getKey())
                .compact();
    }

    public Claims extrairClaims(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean tokenValido(String token){
        try{
            Claims claims = extrairClaims(token);
            return !claims.getExpiration().before(new Date());
        }catch (Exception e){
            return false;
        }
    }

    public String extrairEmail(String token){
        return extrairClaims(token).getSubject();
    }

    public String extrairTipoUsuario(String token){
        return extrairClaims(token).get("tipoUsuario", String.class);
    }

    public Long extrairUsuarioId(String token){
        return extrairClaims(token).get("usuarioId", Long.class);
    }

    public Long extrairPrestadorId(String token){
        return extrairClaims(token).get("prestadorId", Long.class);
    }

}
