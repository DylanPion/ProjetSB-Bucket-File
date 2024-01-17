package com.nextu.projetSB.Service;

import com.nextu.projetSB.Dto.UserCreateDTO;
import com.nextu.projetSB.Dto.UserGetDTO;
import com.nextu.projetSB.Entities.FileData;
import com.nextu.projetSB.Entities.User;
import com.nextu.projetSB.Repositories.FileRepository;
import com.nextu.projetSB.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    /**
     * Récupère un utilisateur grâce à son ID.
     *
     * @param id L'ID de l'utilisateur à rechercher.
     * @return L'utilisateur correspondant à l'ID ou null s'il n'existe pas.
     */
    public User findUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Récupère un utilisateur par son ID et le retourne sous forme de DTO.
     *
     * @param id L'ID de l'utilisateur à rechercher.
     * @return Un objet UserGetDTO représentant les informations de l'utilisateur, ou null s'il n'existe pas.
     */
    public UserGetDTO findById(String id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        return getUserGetDTO(user);
    }

    /**
     * Crée un utilisateur et retourne ses informations sous forme de DTO.
     *
     * @param user L'utilisateur à créer.
     * @return Un objet UserGetDTO représentant les informations de l'utilisateur créé.
     * @throws RuntimeException Si une erreur survient lors de la création de l'utilisateur.
     */
    public UserGetDTO create(User user) {
        try {
            User userAfterSave = userRepository.save(user);
            return getUserGetDTO(userAfterSave);
        } catch (Exception ex) {
            throw new RuntimeException("Erreur lors de la création de l'utilisateur", ex);
        }
    }

    /**
     * Modifie un utilisateur existant.
     *
     * @param id              L'ID de l'utilisateur à modifier.
     * @param userCreateDTO   Les nouvelles informations de l'utilisateur sous forme de DTO.
     * @return L'objet UserCreateDTO représentant les nouvelles informations de l'utilisateur.
     * @throws IllegalArgumentException Si l'utilisateur avec l'ID spécifié n'est pas trouvé.
     * @throws RuntimeException         Si une erreur survient lors de la modification de l'utilisateur.
     */
    public UserCreateDTO update(String id, UserCreateDTO userCreateDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé avec l'ID: " + id));

        try {
            // Met à jour les informations de l'utilisateur
            user.setFirstName(userCreateDTO.getFirstName());
            user.setLastName(userCreateDTO.getLastName());
            user.setLogin(userCreateDTO.getLogin());
            user.setPassword(userCreateDTO.getPassword());

            userRepository.save(user);
            return userCreateDTO;
        } catch (Exception ex) {
            throw new RuntimeException("Erreur lors de la modification de l'utilisateur", ex);
        }
    }

    /**
     * Met à jour l'utilisateur à partir d'un objet User.
     *
     * @param user L'utilisateur à mettre à jour.
     * @return L'utilisateur mis à jour.
     * @throws RuntimeException Si une erreur survient lors de la mise à jour de l'utilisateur.
     */
    public User updateUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception ex) {
            throw new RuntimeException("Erreur lors de la mise à jour de l'utilisateur", ex);
        }
    }

    /**
     * Supprime un utilisateur par son ID.
     *
     * @param id L'ID de l'utilisateur à supprimer.
     * @throws RuntimeException Si une erreur survient lors de la suppression de l'utilisateur.
     */
    public void deleteById(String id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception ex) {
            throw new RuntimeException("Erreur lors de la suppression de l'utilisateur", ex);
        }
    }

    /**
     * Sauvegarde un fichier lié à l'utilisateur.
     *
     * @param userId   L'ID de l'utilisateur auquel le fichier est lié.
     * @param fileName Le nom du fichier à sauvegarder.
     * @throws Exception Si une erreur survient lors de la sauvegarde du fichier.
     */
    public void saveFileByUserId(String userId, String fileName) throws Exception {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            FileData file = new FileData();
            file.setLabel(fileName);
            file.setDescription(fileName);
            FileData fileSaved = fileRepository.save(file);
            user.addFile(fileSaved);
            userRepository.save(user);
        } else {
            throw new Exception("La sauvegarde du fichier pour l'utilisateur :" + userId + " a rencontré une erreur");
        }
    }

    /**
     * Crée un objet UserGetDTO à partir d'un objet User.
     *
     * @param user L'utilisateur dont les informations seront converties en DTO.
     * @return Un objet UserGetDTO représentant les informations de l'utilisateur.
     */
    private static UserGetDTO getUserGetDTO(User user) {
        UserGetDTO userGetDTO = new UserGetDTO();
        userGetDTO.setId(user.getId());
        userGetDTO.setFirstName(user.getFirstName());
        userGetDTO.setLastName(user.getLastName());
        userGetDTO.setLogin(user.getLogin());
        return userGetDTO;
    }
}
