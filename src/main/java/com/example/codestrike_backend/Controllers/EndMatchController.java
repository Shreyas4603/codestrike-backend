package com.example.codestrike_backend.Controllers;


import com.example.codestrike_backend.Services.EndMatchService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EndMatchController {

    @Autowired
    private EndMatchService endMatchService;


    @GetMapping("/end/{matchId}")
    public ResponseEntity<?> endMatch(@PathVariable("matchId") String matchId, HttpServletRequest request){
        System.out.println("amtch id "+matchId);
        return  endMatchService.endMatch(matchId,(String)request.getAttribute("userId"));
    }

    @GetMapping("/end/redirect/{matchId}")
    public ResponseEntity<?> endRedirectMatch(@PathVariable("matchId") String matchId, HttpServletRequest request){
        System.out.println("match id "+matchId);
        return  endMatchService.endMatch(matchId,(String)request.getAttribute("userId"));
    }

}
