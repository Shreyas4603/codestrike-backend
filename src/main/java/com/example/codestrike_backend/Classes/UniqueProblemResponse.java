package com.example.codestrike_backend.Classes;

/**
 * Represents the response object containing the unique problem identifier.
 *
 * This class is typically used to encapsulate the unique problem ID
 * for API responses or data transfer between services.
 */
public class UniqueProblemResponse {

    // The unique identifier for a problem
    private String uniqueProblemId;

    /**
     * Retrieves the unique problem identifier.
     *
     * @return the unique problem ID
     */
    public String getUniqueProblemId() {
        return uniqueProblemId;
    }

    /**
     * Sets the unique problem identifier.
     *
     * @param uniqueProblemId the unique problem ID to be set
     */
    public void setUniqueProblemId(String uniqueProblemId) {
        this.uniqueProblemId = uniqueProblemId;
    }
}
