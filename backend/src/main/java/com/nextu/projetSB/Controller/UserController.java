package com.nextu.projetSB.Controller;

import com.nextu.projetSB.Dto.UserCreateDTO;
import com.nextu.projetSB.Dto.UserGetDTO;
import com.nextu.projetSB.Entities.User;
import com.nextu.projetSB.Service.StorageService;
import com.nextu.projetSB.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/users")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final StorageService storageService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Début région CRUD
    /**
     * Crée un nouvel utilisateur.
     *
     * @param userCreateDTO Les données pour créer l'utilisateur.
     * @return ResponseEntity contenant les détails de l'utilisateur créé.
     */
    @PostMapping(value = "/", produces = { "application/json", "application/xml" })
    public ResponseEntity<UserGetDTO> create(@RequestBody UserCreateDTO userCreateDTO) {
        User user = new User();
        user.setFirstName(userCreateDTO.getFirstName());
        user.setLastName(userCreateDTO.getLastName());
        user.setLogin(userCreateDTO.getLogin());
        user.setPassword(encoder.encode(userCreateDTO.getPassword()));
        return ResponseEntity.ok(userService.create(user));
    }

    /**
     * Récupère un utilisateur par son ID.
     *
     * @param id L'ID de l'utilisateur à récupérer.
     * @return ResponseEntity avec les détails de l'utilisateur s'il existe, sinon une réponse 404.
     */
    @GetMapping(value = "/{id}", produces = { "application/json", "application/xml" })
    public ResponseEntity<UserGetDTO> find(@PathVariable String id) {
        UserGetDTO userGetDTO = userService.findById(id);
        if (userGetDTO != null) {
            return ResponseEntity.ok(userGetDTO);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Met à jour les informations d'un utilisateur.
     *
     * @param id             L'ID de l'utilisateur à mettre à jour.
     * @param userCreateDTO Les nouvelles informations de l'utilisateur.
     * @return ResponseEntity indiquant que l'utilisateur a été modifié avec succès.
     */
    @PutMapping(value = "/{id}", produces = { "application/json", "application/xml" })
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody UserCreateDTO userCreateDTO) {
        userService.update(id, userCreateDTO);
        return ResponseEntity.ok("L'utilisateur avec l'id:" + id + " a été modifié");
    }

    /**
     * Supprime un utilisateur par son ID.
     *
     * @param id L'ID de l'utilisateur à supprimer.
     * @return ResponseEntity indiquant que l'utilisateur a été supprimé avec succès.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    // Fin région CRUD

    /**
     * Ajoute un fichier à un utilisateur.
     *
     * @param id   L'ID de l'utilisateur auquel ajouter le fichier.
     * @param file Le fichier à ajouter.
     * @return ResponseEntity avec le nom du fichier ajouté s'il a été ajouté avec succès, sinon une réponse 400 avec un message d'erreur.
     */
    @PostMapping(value = "/{id}/file")
    public ResponseEntity<String> saveFile(@PathVariable String id, @RequestParam MultipartFile file) {
        String messageError = "";
        try {
            String fileName = storageService.save(file, id);
            userService.saveFileByUserId(id, fileName);
            return ResponseEntity.ok(fileName);
        } catch (Exception e) {
            messageError = e.getMessage();
        }
        return ResponseEntity.badRequest().body(messageError);
    }
}
