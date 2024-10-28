package com.example.codestrike_backend.Repositories;


import com.example.codestrike_backend.Models.UserGameData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserGameDataRepository extends MongoRepository<UserGameData,String> {
    Optional<UserGameData> findByUserId(String userId);
}
