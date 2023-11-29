package com.sd.sistemasd.security.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.sd.sistemasd.beans.user.UserBean;

import java.util.Date;

@Component
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    @Value("${app.jwt.expiration.duration}")
    private Long EXPIRE_DURATION;

    private final byte[] SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();


    public String generateAccesToken(UserBean user){
        return Jwts.builder()
                .setSubject(String.format("%s,%s", user.getId(), user.getEmail()))
                .setIssuer("sistemassd")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }


    public boolean validateAccesToken(String token) {
        try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(SECRET_KEY)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                return true; // Si no se lanzan excepciones, el token es válido.
        } catch (ExpiredJwtException e) {
            LOGGER.error("Token expirado", e);
        } catch (MalformedJwtException e) {
            LOGGER.error("Token mal formado", e);
        } catch (SignatureException e) {
            LOGGER.error("Error de firma", e);
        } catch (Exception e) {
            LOGGER.error("Error al validar el token", e);
        }

        return false;
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getSubject(String token) {
        return parseClaims(token).getSubject();
    }
}
