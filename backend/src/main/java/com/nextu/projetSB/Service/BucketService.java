package com.nextu.projetSB.Service;

import com.nextu.projetSB.Dto.BucketDTO;
import com.nextu.projetSB.Entities.Bucket;
import com.nextu.projetSB.Repositories.BucketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@RequiredArgsConstructor
@Service
public class BucketService {
    private final BucketRepository bucketRepository;

    /**
     * Crée un nouveau Bucket à partir des informations fournies dans le DTO.
     *
     * @param bucketDTO Les informations du Bucket à créer.
     * @return Le DTO du Bucket créé.
     * @throws RuntimeException En cas d'erreur lors de la création du Bucket.
     */
    public BucketDTO create(BucketDTO bucketDTO) {
        try {
            Bucket bucket = new Bucket();
            bucket.setLabel(bucketDTO.getLabel());
            bucket.setDescription(bucketDTO.getDescription());
            Bucket bucketAfterSave = bucketRepository.save(bucket);
            bucketDTO.setId(bucketAfterSave.getId());
            return bucketDTO;
        } catch (Exception ex) {
            throw new RuntimeException("Erreur lors de la création du bucket", ex);
        }
    }

    /**
     * Récupère un Bucket à partir de son ID.
     *
     * @param id L'ID du Bucket à récupérer.
     * @return Le Bucket correspondant à l'ID, ou null s'il n'existe pas.
     */
    public Bucket findById(String id) {
        return bucketRepository.findById(id).orElse(null);
    }

    /**
     * Met à jour un Bucket existant avec les informations fournies dans le DTO.
     *
     * @param id        L'ID du Bucket à mettre à jour.
     * @param bucketDTO Les nouvelles informations du Bucket.
     * @return Le Bucket mis à jour.
     * @throws IllegalArgumentException Si le Bucket avec l'ID spécifié n'est pas trouvé.
     * @throws RuntimeException         En cas d'erreur lors de la modification du Bucket.
     */
    public Bucket update(String id, BucketDTO bucketDTO) {
        Bucket bucket = bucketRepository.findById(id).orElse(null);
        if (bucket == null) throw new IllegalArgumentException("Bucket non trouvé avec l'ID:" + id);

        try {
            bucket.setLabel(bucketDTO.getLabel());
            bucket.setDescription(bucketDTO.getDescription());
            Bucket bucketAfterSave = bucketRepository.save(bucket);
            return bucketAfterSave;
        } catch (Exception ex) {
            throw new RuntimeException("Erreur lors de la modification du Bucket", ex);
        }
    }

    /**
     * Supprime un Bucket à partir de son ID.
     *
     * @param id L'ID du Bucket à supprimer.
     * @throws RuntimeException En cas d'erreur lors de la suppression du Bucket.
     */
    public void deleteById(String id) {
        try {
            bucketRepository.deleteById(id);
        } catch (Exception ex) {
            throw new RuntimeException("Erreur lors de la suppression du Bucket", ex);
        }
    }
}
