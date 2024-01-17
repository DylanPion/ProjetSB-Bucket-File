package com.nextu.projetSB.Repositories;

import com.nextu.projetSB.Entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByLogin(String login);
}
