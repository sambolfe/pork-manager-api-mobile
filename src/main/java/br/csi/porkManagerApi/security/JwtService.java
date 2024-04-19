package br.csi.porkManagerApi.security;

import br.csi.porkManagerApi.models.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    public String createJwt(Usuario user) {
        try {
            if (user == null || user.getUsername() == null || user.getAuthorities().isEmpty()) {
                throw new IllegalArgumentException("Usuário ou autoridades inválidos");
            }

            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer("pork_manager_api")
                    .withSubject(user.getUsername())
                    .withClaim("role", user.getAuthorities().stream().findFirst().orElseThrow().toString())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        } catch (IllegalArgumentException | JWTCreationException e) {
            logger.error("Erro ao gerar token JWT", e);
            throw new RuntimeException("Erro ao gerar token JWT", e);
        }
    }

    public String getSubject(String jwt) {
        try {
            if (jwt == null || jwt.isEmpty()) {
                throw new IllegalArgumentException("Token JWT inválido");
            }

            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm)
                    .withIssuer("pork_manager_api")
                    .build()
                    .verify(jwt)
                    .getSubject();
        } catch (IllegalArgumentException | JWTVerificationException e) {
            logger.error("Token inválido ou expirado!", e);
            throw new RuntimeException("Token inválido ou expirado!", e);
        }
    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
