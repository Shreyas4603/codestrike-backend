package com.example.codestrike_backend.Controllers;


import com.example.codestrike_backend.Models.UserGameData;
import com.example.codestrike_backend.Services.StartMatchService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping("api/start")
public class StartMatchController {

    @Autowired
    private StartMatchService startMatchService;


    @PostMapping("/match")
    public DeferredResult<String> start(@RequestBody UserGameData body, HttpServletRequest request) {
        return startMatchService.AddPlayerToQueue(request);
    }
}

