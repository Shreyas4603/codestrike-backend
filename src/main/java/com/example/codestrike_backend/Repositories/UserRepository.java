package com.example.codestrike_backend.Repositories;

import com.example.codestrike_backend.Models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on the User collection in MongoDB.
 * This interface extends MongoRepository, providing built-in methods for basic operations.
 * Custom queries are defined for email, username, and userId lookups.
 *
 * @see MongoRepository
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Find a user by their email.
     *
     * @param email The email of the user.
     * @return An Optional containing the user if found, or empty if not.
     */
    Optional<User> findByEmail(String email);

    /**
     * Find a user by their username.
     *
     * @param userName The username of the user.
     * @return An Optional containing the user if found, or empty if not.
     */
    Optional<User> findByUsername(String userName);

    /**
     * Find a user by their unique userId.
     *
     * @param userId The unique userId of the user.
     * @return An Optional containing the user if found, or empty if not.
     */
    Optional<User> findByUserId(String userId);
}
