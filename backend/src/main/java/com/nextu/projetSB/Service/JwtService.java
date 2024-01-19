package com.nextu.projetSB.Service;

import com.nextu.projetSB.Entities.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;

@Component
public class JwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
    @Value("${BucketProject.app.jwtSecret}")
    private String jwtSecret;
    @Value("${BucketProject.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /**
     * Génère un token JWT à partir de l'objet d'authentification.
     *
     * @param authentication L'objet d'authentification.
     * @return Le token JWT généré.
     */
    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Retourne la clé utilisée pour signer les tokens JWT.
     */
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    /**
     * Récupère le nom d'utilisateur à partir du token JWT.
     *
     * @param token Le token JWT.
     * @return Le nom d'utilisateur extrait du token.
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Valide un token JWT en vérifiant sa signature et sa validité temporelle.
     *
     * @param authToken Le token JWT à valider.
     * @return true si le token est valide, sinon false.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Jeton JWT invalide : {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Le jeton JWT a expiré : {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Le jeton JWT n'est pas pris en charge : {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("La chaîne de revendications JWT est vide : {}", e.getMessage());
        }


        return false;
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
