package com.example.codestrike_backend.Services;

import com.example.codestrike_backend.Classes.UniqueProblemId;
import com.example.codestrike_backend.Classes.UniqueProblemResponse;
import com.example.codestrike_backend.Clients.NodeClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProblemServiceTest {

    private final ArrayList<String> player1List = new ArrayList<>(Arrays.asList(
            "ff2c298e-d448-4f68-8639-e0cbab1f2719",
            "cec47cf4-ec2c-4b24-bfb5-aac41ed3d3e0"
    ));

    private final ArrayList<String> player2List = new ArrayList<>(List.of(
            "adc8e614-9ddb-4c21-9f51-f52cd6f0a8de"
    ));

    private final String rank = "hacker";

    private final UniqueProblemId obj = new UniqueProblemId(rank, player1List, player2List);

    @Autowired
    private NodeClient nodeClient;

    public String getUniqueQuestion() {
        // Make the request and map the response to UniqueProblemResponse
        ResponseEntity<UniqueProblemResponse> res = nodeClient.getUnique(obj);

        // Null check and logging for better error handling
        if (res != null && res.getBody() != null) {
            // Access the uniqueProblemId
            String uniqueProblemId = res.getBody().getUniqueProblemId();
            System.out.println("Unique Problem ID: " + uniqueProblemId);

            return uniqueProblemId;
        } else {
            System.err.println("Failed to retrieve unique problem ID: Response or body is null.");
            return null;
        }
    }
}
