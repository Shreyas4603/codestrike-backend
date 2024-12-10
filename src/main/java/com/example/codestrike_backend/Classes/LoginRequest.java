package com.example.codestrike_backend.Classes;

/**
 * DTO (Data Transfer Object) for encapsulating login request data.
 * This class is used to transfer login credentials (email and password)
 * from the client to the server in a structured format.
 */
public class LoginRequest {

    // User's email address used for authentication
    private String email;

    // User's password used for authentication
    private String password;

    /**
     * Default no-args constructor.
     * Needed for frameworks like Spring to create instances of this class via reflection.
     */
    public LoginRequest() {}

    /**
     * Parameterized constructor.
     * Used to initialize the LoginRequest with the provided email and password.
     *
     * @param email    the email address of the user
     * @param password the password of the user
     */
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Getter for email.
     *
     * @return the email address of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter for email.
     *
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter for password.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for password.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
