package com.example.codestrike_backend.Repositories;

import com.example.codestrike_backend.Models.UserGameData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on the UserGameData collection in MongoDB.
 * This interface extends MongoRepository, which provides built-in methods for basic CRUD operations.
 * A custom query is defined to retrieve user game data by their userId.
 *
 * @see MongoRepository
 */
@Repository
public interface UserGameDataRepository extends MongoRepository<UserGameData, String> {

    /**
     * Find UserGameData by userId.
     * This method allows retrieval of game data for a specific user based on their unique userId.
     *
     * @param userId The unique identifier of the user.
     * @return An Optional containing the UserGameData if found, or an empty Optional if not found.
     */
    Optional<UserGameData> findByUserId(String userId);
}
