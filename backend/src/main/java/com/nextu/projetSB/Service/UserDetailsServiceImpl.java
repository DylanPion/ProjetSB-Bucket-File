package com.nextu.projetSB.Service;

import com.nextu.projetSB.Entities.User;
import com.nextu.projetSB.Entities.UserDetailsImpl;
import com.nextu.projetSB.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Récupère un utilisateur à partir de son nom d'utilisateur, puis crée un objet UserDetailsImpl basé sur cet utilisateur.
     *
     * @param username Le nom d'utilisateur de l'utilisateur à charger.
     * @return Un objet UserDetailsImpl représentant les détails de l'utilisateur.
     * @throws UsernameNotFoundException Si aucun utilisateur n'est trouvé avec le nom d'utilisateur spécifié.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Recherche de l'utilisateur dans le repository par son nom d'utilisateur
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec le nom d'utilisateur : " + username));

        // Construction d'un objet UserDetailsImpl basé sur l'utilisateur trouvé
        return UserDetailsImpl.build(user);
    }
}
