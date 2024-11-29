package com.example.codestrike_backend.Classes;

import java.util.ArrayList;

/**
 * Represents a unique problem identifier for ranking and tracking
 * the questions attempted by two participants.
 *
 * This class stores the rank and lists of attempted questions for
 * participants in a coding competition or problem-solving scenario.
 */
public class UniqueProblemId {

    // The rank associated with the problem
    private String rank;

    // List of questions attempted by participant 1
    private ArrayList<String> p1AttemptedQuestions;

    // List of questions attempted by participant 2
    private ArrayList<String> p2AttemptedQuestions;

    /**
     * Constructor to initialize the rank and attempted questions for both participants.
     *
     * @param rank                 the rank associated with this problem
     * @param p1AttemptedQuestions the list of questions attempted by participant 1
     * @param p2AttemptedQuestions the list of questions attempted by participant 2
     */
    public UniqueProblemId(String rank, ArrayList<String> p1AttemptedQuestions, ArrayList<String> p2AttemptedQuestions) {
        this.rank = rank;
        this.p1AttemptedQuestions = p1AttemptedQuestions;
        this.p2AttemptedQuestions = p2AttemptedQuestions;
    }

    /**
     * Default constructor to initialize the object without parameters.
     */
    public UniqueProblemId() {
        // Default initialization
        this.p1AttemptedQuestions = new ArrayList<>();
        this.p2AttemptedQuestions = new ArrayList<>();
    }

    /**
     * @return the rank associated with the problem
     */
    public String getRank() {
        return rank;
    }

    /**
     * @param rank sets the rank associated with the problem
     */
    public void setRank(String rank) {
        this.rank = rank;
    }

    /**
     * @return the list of questions attempted by participant 1
     */
    public ArrayList<String> getP1AttemptedQuestions() {
        return p1AttemptedQuestions;
    }

    /**
     * @param p1AttemptedQuestions sets the list of questions attempted by participant 1
     */
    public void setP1AttemptedQuestions(ArrayList<String> p1AttemptedQuestions) {
        this.p1AttemptedQuestions = p1AttemptedQuestions;
    }

    /**
     * @return the list of questions attempted by participant 2
     */
    public ArrayList<String> getP2AttemptedQuestions() {
        return p2AttemptedQuestions;
    }

    /**
     * @param p2AttemptedQuestions sets the list of questions attempted by participant 2
     */
    public void setP2AttemptedQuestions(ArrayList<String> p2AttemptedQuestions) {
        this.p2AttemptedQuestions = p2AttemptedQuestions;
    }
}
