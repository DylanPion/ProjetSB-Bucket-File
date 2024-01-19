package com.nextu.projetSB.Controller;

import com.nextu.projetSB.Entities.*;
import com.nextu.projetSB.Exceptions.TokenRefreshException;
import com.nextu.projetSB.Repositories.UserRepository;
import com.nextu.projetSB.Service.JwtService;
import com.nextu.projetSB.Service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    // Interface responsable de l'authentication
    private AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    /**
     * Endpoint pour l'inscription d'un nouvel utilisateur.
     *
     * @param signUpRequest Les informations de l'utilisateur à inscrire.
     * @return ResponseEntity avec un message de réussite.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        User user = new User();
        user.setLogin(signUpRequest.getLogin());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setLastName(signUpRequest.getLastName());
        user.setFirstName(signUpRequest.getFirstName());

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Utilisateur inscrit avec success"));
    }

    /**
     * Endpoint pour l'authentification d'un utilisateur (connexion).
     *
     * @param loginRequest Les informations de connexion de l'utilisateur.
     * @return ResponseEntity avec les informations du token JWT et de l'utilisateur authentifié.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Authentification de l'utilisateur en utilisant le gestionnaire d'authentification
        // authenticate prend en argument un objet authentication
        // UsernamePasswordAuthenticationToken est un objet de springsecurity pour représenter l'authentication avec username et password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));

        // Définition de l'authentification dans le contexte de sécurité
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Génération du token JWT
        String jwt = jwtService.generateJwtToken(authentication);

        // Récupération des détails de l'utilisateur authentifié
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Récupération des rôles de l'utilisateur
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        // Génération du token de rafraichissement
       String refreshToken = refreshTokenService.generateRefreshToken(userDetails);

        // Retourne une réponse avec les informations du token JWT et de l'utilisateur
        return ResponseEntity.ok(new JwtResponse(jwt,
                refreshToken,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getFirstName(),
                roles));
    }

    /*
    * Endpoint pour raffraichir le token jwt
    * Récupération du RefreshToken,nous obtenons l'objet RefreshToken {id, user, token, expiryDate} à partir du Token brut
    *  en utilisant RefreshTokenService. Vérification de la date d'expiration puis on génère un nouveau token à partir
    * du champ user du RefreshToken
     * */

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtService.generateTokenFromUsername(user.getLogin());
                    return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }
}

