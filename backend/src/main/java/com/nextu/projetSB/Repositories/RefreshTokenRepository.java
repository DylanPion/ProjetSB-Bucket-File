package com.nextu.projetSB.Repositories;

import com.nextu.projetSB.Entities.Bucket;
import com.nextu.projetSB.Entities.RefreshToken;
import com.nextu.projetSB.Entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);

    int deleteByUser(User user);
}

