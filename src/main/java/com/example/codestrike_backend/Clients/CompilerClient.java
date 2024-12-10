package com.example.codestrike_backend.Clients;


import com.example.codestrike_backend.Classes.CodeSubmission;
import com.example.codestrike_backend.Classes.UniqueProblemResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "compiler-service", url = "${compiler-service.url}")
public interface CompilerClient {

    @PostMapping("/api/compile")
    String compileCode(CodeSubmission codeSubmission);


}
