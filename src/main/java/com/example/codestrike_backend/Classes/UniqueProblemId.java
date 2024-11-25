package com.example.codestrike_backend.Classes;

import java.util.ArrayList;

public class UniqueProblemId {

    private String rank;
    private ArrayList<String> p1AttemptedQuestions;
    private ArrayList<String> p2AttemptedQuestions;


    public UniqueProblemId(String rank, ArrayList<String> p1AttemptedQuestions, ArrayList<String> p2AttemptedQuestions) {
        this.rank = rank;
        this.p1AttemptedQuestions = p1AttemptedQuestions;
        this.p2AttemptedQuestions = p2AttemptedQuestions;
    }

    public UniqueProblemId() {
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public ArrayList<String> getP1AttemptedQuestions() {
        return p1AttemptedQuestions;
    }

    public void setP1AttemptedQuestions(ArrayList<String> p1AttemptedQuestions) {
        this.p1AttemptedQuestions = p1AttemptedQuestions;
    }

    public ArrayList<String> getP2AttemptedQuestions() {
        return p2AttemptedQuestions;
    }

    public void setP2AttemptedQuestions(ArrayList<String> p2AttemptedQuestions) {
        this.p2AttemptedQuestions = p2AttemptedQuestions;
    }
}
