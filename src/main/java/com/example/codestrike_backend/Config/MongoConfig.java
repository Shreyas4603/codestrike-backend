package com.example.codestrike_backend.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * Configuration class for MongoDB settings.
 *
 * This class enables MongoDB auditing, which allows automatic population of
 * auditing fields like createdBy, createdDate, lastModifiedBy, and lastModifiedDate
 * in your entities. Additional MongoDB-specific configurations can be added here
 * as needed.
 */
@Configuration
@EnableMongoAuditing
public class MongoConfig {

    /**
     * Placeholder for additional MongoDB configurations.
     *
     * If your application requires custom settings (e.g., custom converters,
     * database connection configurations, etc.), they can be defined here.
     */
    // Add custom configurations if necessary
}
