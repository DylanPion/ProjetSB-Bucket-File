package com.nextu.projetSB.Service;

import com.nextu.projetSB.Repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class FileService {
    private final FileRepository fileRepository;

    /**
     * Vérifie si un fichier avec le nom donné existe dans le référentiel.
     *
     * @param fileName Le nom du fichier à vérifier.
     * @return true si le fichier existe, sinon false.
     */
    public boolean checkIfFileExist(String fileName) {
        // Utilise le repository pour rechercher un fichier par son label (nom)
        return fileRepository.findByLabel(fileName) != null;
        // Retourne true si un fichier correspondant est trouvé, sinon false
    }
}
