package com.example.codestrike_backend.Config;

import com.example.codestrike_backend.Classes.QueueData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.PriorityBlockingQueue;

@Configuration
public class QueueConfig {

    @Bean
    public PriorityBlockingQueue<QueueData> NoviceQueue() {
        return new PriorityBlockingQueue<>();
    }

    @Bean
    public PriorityBlockingQueue<QueueData> CoderQueue() {
        return new PriorityBlockingQueue<>();
    }

    @Bean
    public PriorityBlockingQueue<QueueData> HackerQueue() {
        return new PriorityBlockingQueue<>();
    }

    @Bean
    public PriorityBlockingQueue<QueueData> GuruQueue() {
        return new PriorityBlockingQueue<>();
    }

    @Bean
    public PriorityBlockingQueue<QueueData> MasterQueue() {
        return new PriorityBlockingQueue<>();
    }

}

