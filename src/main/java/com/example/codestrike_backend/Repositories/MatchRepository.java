package com.example.codestrike_backend.Repositories;

import com.example.codestrike_backend.Models.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MatchRepository extends MongoRepository<Match, String> {
    Optional<Match> findByMatchId(String matchId); // Returns Match object
}
