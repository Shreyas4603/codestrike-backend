package com.example.codestrike_backend.Controllers;


import com.example.codestrike_backend.Services.ProblemServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/api/problem")
public class ProblemControllerTest {

    @Autowired
    private ProblemServiceTest problemServiceTest;

    @GetMapping("/unique")
    public String getUniqueQuestion(){
        System.out.println("In here");
        String res=problemServiceTest.getUniqueQuestion();
        return res;
    }





}
