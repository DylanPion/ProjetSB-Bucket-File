package com.nextu.projetSB.Token;

import com.nextu.projetSB.Service.JwtService;
import com.nextu.projetSB.Service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Récupère le token JWT du header Authorization
            String jwt = parseJwt(request);
            // Vérifie et valide le token JWT
            if (jwt != null && jwtService.validateJwtToken(jwt)) {
                    // Extrait le nom d'utilisateur du token
                    String username = jwtService.getUserNameFromJwtToken(jwt);

                    // Retrouver un User via son Username et créer un UserDetailsImpl à partir de celui-ci
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    // Crée une instance d'authentication avec les détails de l'utilisateur
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null,
                            userDetails.getAuthorities());

                    // Ajoute les détails de l'authentification basés sur la requête
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Définit l'authentification dans le contexte de sécurité
                    SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            // En cas d'erreur, enregistre un message d'erreur
            logger.error("Cannot set user authentication: {}", e);
        }

        // Passe la requête au filtre suivant dans la chaîne
        filterChain.doFilter(request, response);
    }

    // Méthode pour extraire le token JWT du header Authorization
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            // Retourne le token JWT en supprimant le préfixe "Bearer "
            return headerAuth.substring(7, headerAuth.length());
        }

        // Retourne null si le header n'est pas présent ou ne commence pas par "Bearer "
        return null;
    }
}