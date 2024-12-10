package com.example.codestrike_backend.Controllers;


import com.example.codestrike_backend.Classes.MatchProblemDetails;
import com.example.codestrike_backend.Classes.ProblemDTO;
import com.example.codestrike_backend.Models.Match;
import com.example.codestrike_backend.Repositories.MatchRepository;
import com.example.codestrike_backend.Services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/match")
public class MatchController {

    @Autowired
     private MatchService matchService;




    @GetMapping("/{id}")
    public MatchProblemDetails getMatchById(@PathVariable("id") String id) {

        return  matchService.getMatchInfo(id);
    }
}
