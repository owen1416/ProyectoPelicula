package Proyecto.Peliculas.JWT;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}") // Carga desde application.properties
    private String jwtSecret;

    @Value("${app.jwt.expiration-milliseconds}") // Carga desde application.properties
    private int jwtExpirationInMs;

    // Genera una clave segura a partir del secreto
    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Genera el JWT
    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS512) // Firma con la clave segura
                .compact();
    }

    // Obtiene el username del JWT
    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // Valida el JWT
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            // Token JWT mal formado
            System.out.println("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            // Token JWT expirado
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            // Token JWT no soportado
            System.out.println("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            // JWT claims string vacío
            System.out.println("JWT claims string is empty.");
        }
        return false;
    }
}
