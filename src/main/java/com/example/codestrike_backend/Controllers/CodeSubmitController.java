package com.example.codestrike_backend.Controllers;


import com.example.codestrike_backend.Classes.CodeSubmission;
import com.example.codestrike_backend.Handlers.MatchWebSocketHandler;
import com.example.codestrike_backend.Services.CodeSubmitService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;

@RestController
@RequestMapping("/api/submit")
@CrossOrigin(origins = {"*","http://localhost:5173"})
public class CodeSubmitController {
    @Autowired
    private  MatchWebSocketHandler matchWebSocketHandler;

    @Autowired
    private CodeSubmitService codeSubmitService;

    @PostMapping
    public ResponseEntity<String> submitCode(@RequestBody CodeSubmission body, HttpServletRequest request){
        String userId = request.getAttribute("userId").toString();
        return codeSubmitService.compileCode( body,userId);
//        matchWebSocketHandler.sendMessageToMatch("1234","yoo");
//        matchWebSocketHandler.sendMessageToMatch("1234","yoo");
    }
}
