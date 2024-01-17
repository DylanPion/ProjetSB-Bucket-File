package com.nextu.projetSB.Repositories;

import com.nextu.projetSB.Entities.Bucket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BucketRepository extends MongoRepository<Bucket, String> {
    Optional<Bucket> findByLabel(String label);
}


