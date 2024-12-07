package com.example.codestrike_backend.Classes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TestResult {
    @JsonProperty("total_testcases")
    private int totalTestcases;
    private int passed;
    private int failed;

    @JsonIgnore
    private String details;
    private String execution_status;
    private double execution_time_ms;


    public String getExecution_status() {
        return execution_status;
    }

    public void setExecution_status(String execution_status) {
        this.execution_status = execution_status;
    }

    public double getExecution_time_ms() {
        return execution_time_ms;
    }

    public void setExecution_time_ms(double execution_time_ms) {
        this.execution_time_ms = execution_time_ms;
    }

    public int getTotalTestcases() {
        return totalTestcases;
    }

    public void setTotalTestcases(int totalTestcases) {
        this.totalTestcases = totalTestcases;
    }

    public int getPassed() {
        return passed;
    }

    public void setPassed(int passed) {
        this.passed = passed;
    }

    public int getFailed() {
        return failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
// Getters and setters
}

class TestCaseDetail {
    private int testCase;
    private String status;
    private String input;
    private String error;
    private String traceback;

    // Getters and setters

    public int getTestCase() {
        return testCase;
    }

    public void setTestCase(int testCase) {
        this.testCase = testCase;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input =(String) input;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getTraceback() {
        return traceback;
    }

    public void setTraceback(String traceback) {
        this.traceback = traceback;
    }
}
