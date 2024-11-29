package com.example.codestrike_backend.Config;

import com.example.codestrike_backend.Classes.QueueData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * Configuration class for defining PriorityBlockingQueue beans.
 *
 * This class creates and registers separate PriorityBlockingQueue instances for
 * different user categories (e.g., Novice, Coder, Hacker, Guru, Master).
 * Each queue is specifically configured to handle `QueueData` objects and is
 * prioritized based on the `Comparable` implementation in the `QueueData` class.
 */
@Configuration
public class QueueConfig {

    /**
     * Defines a PriorityBlockingQueue bean for the "Novice" user category.
     *
     * @return a new instance of PriorityBlockingQueue for Novice users
     */
    @Bean
    public PriorityBlockingQueue<QueueData> NoviceQueue() {
        return new PriorityBlockingQueue<>();
    }

    /**
     * Defines a PriorityBlockingQueue bean for the "Coder" user category.
     *
     * @return a new instance of PriorityBlockingQueue for Coder users
     */
    @Bean
    public PriorityBlockingQueue<QueueData> CoderQueue() {
        return new PriorityBlockingQueue<>();
    }

    /**
     * Defines a PriorityBlockingQueue bean for the "Hacker" user category.
     *
     * @return a new instance of PriorityBlockingQueue for Hacker users
     */
    @Bean
    public PriorityBlockingQueue<QueueData> HackerQueue() {
        return new PriorityBlockingQueue<>();
    }

    /**
     * Defines a PriorityBlockingQueue bean for the "Guru" user category.
     *
     * @return a new instance of PriorityBlockingQueue for Guru users
     */
    @Bean
    public PriorityBlockingQueue<QueueData> GuruQueue() {
        return new PriorityBlockingQueue<>();
    }

    /**
     * Defines a PriorityBlockingQueue bean for the "Master" user category.
     *
     * @return a new instance of PriorityBlockingQueue for Master users
     */
    @Bean
    public PriorityBlockingQueue<QueueData> MasterQueue() {
        return new PriorityBlockingQueue<>();
    }
}
