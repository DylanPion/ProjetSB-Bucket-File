package com.nextu.projetSB.Service;

import com.nextu.projetSB.Entities.Bucket;
import com.nextu.projetSB.Entities.FileData;
import com.nextu.projetSB.Exceptions.FileContentException;
import com.nextu.projetSB.Repositories.BucketRepository;
import com.nextu.projetSB.Repositories.FileRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Service responsable de la gestion du stockage des fichiers dans le système de fichiers.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class StorageService {
    @Value("${BucketProject.filestore}")
    private String SERVER_LOCATION;
    private Path root;
    private final Logger logger = LoggerFactory.getLogger(StorageService.class);
    private final FileRepository fileRepository;
    private final BucketService bucketService;
    private final BucketRepository bucketRepository;

    /**
     * Initialise le service en créant le dossier de stockage s'il n'existe pas déjà.
     */
    @PostConstruct
    public void init() {
        try {
            this.root = Paths.get(SERVER_LOCATION);
            Files.createDirectory(root);
        } catch (IOException e) {
            logger.warn("Le dossier de stockage existe déjà.");
        }
    }

    /**
     * Sauvegarde le fichier et retourne le nom du fichier sauvegardé.
     *
     * @param file Le fichier à sauvegarder.
     * @return Le nom du fichier sauvegardé.
     * @throws FileContentException Si une erreur survient lors de la sauvegarde du fichier.
     */
    public String save(MultipartFile file, String bucketId) throws FileContentException {
        return copyFile(file, bucketId );
    }

    /**
     * Effectue la copie réelle du fichier dans le dossier de stockage et le sauvegarde en base de données.
     *
     * @param file Le fichier à copier.
     * @return Le nom du fichier copié.
     * @throws FileContentException Si une erreur survient lors de la copie du fichier.
     */
    private String copyFile(MultipartFile file, String bucketId) throws FileContentException {
        var fileNameDest = FileUtils.generateStringFromDate(FileUtils.getExtension(file.getOriginalFilename()));
        String filePathDest = SERVER_LOCATION + fileNameDest;

        try {
            Files.copy(file.getInputStream(), this.root.resolve(fileNameDest));
            FileData newFile = new FileData();
            newFile.setLabel(fileNameDest);
            newFile.setPathFile(filePathDest);
            Bucket bucket = bucketService.findById(bucketId);
            bucket.addFile(fileRepository.save(newFile));
            bucketRepository.save(bucket);
            return fileNameDest;
        } catch (Exception e) {
            logger.error("Une exception s'est produite lors de la sauvegarde du fichier : {}", e.getMessage());
            throw new FileContentException("Impossible de stocker le fichier. Erreur : " + e.getMessage());
        }
    }

    /**
     * Récupère un fichier en utilisant le chemin du fichier.
     *
     * @param filename Le nom du fichier à charger.
     * @return L'objet File représentant le fichier chargé.
     * @throws IOException Si une erreur survient lors du chargement du fichier.
     */
    public File load(String filename) throws IOException {
        return new File(SERVER_LOCATION + filename);
    }
}
