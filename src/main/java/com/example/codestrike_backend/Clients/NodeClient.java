package com.example.codestrike_backend.Clients;


import com.example.codestrike_backend.Classes.UniqueProblemId;
import com.example.codestrike_backend.Classes.UniqueProblemResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "node-service", url = "${node-service.url}")
public interface NodeClient {
    @GetMapping("/api/problem/unique")
    ResponseEntity<Object>  greet();

    @PostMapping("/api/problem/unique")
    ResponseEntity<UniqueProblemResponse> getUnique(@RequestBody UniqueProblemId request);
}
