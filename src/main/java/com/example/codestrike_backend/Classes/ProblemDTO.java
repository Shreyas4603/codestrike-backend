package com.example.codestrike_backend.Classes;

import java.time.LocalDateTime;

public class ProblemDTO {

    private String id;
    private String problemId;
    private String problemStatement;
    private String toughnessLevel;
    private String templateCodePy;
    private String templateCodeJava;
    private String templateCodeCpp;
    private String driverCodePy;
    private String driverCodeJava;
    private String driverCodeCpp;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProblemId() {
        return problemId;
    }

    public void setProblemId(String problemId) {
        this.problemId = problemId;
    }

    public String getProblemStatement() {
        return problemStatement;
    }

    public void setProblemStatement(String problemStatement) {
        this.problemStatement = problemStatement;
    }

    public String getToughnessLevel() {
        return toughnessLevel;
    }

    public void setToughnessLevel(String toughnessLevel) {
        this.toughnessLevel = toughnessLevel;
    }

    public String getTemplateCodePy() {
        return templateCodePy;
    }

    public void setTemplateCodePy(String templateCodePy) {
        this.templateCodePy = templateCodePy;
    }

    public String getTemplateCodeJava() {
        return templateCodeJava;
    }

    public void setTemplateCodeJava(String templateCodeJava) {
        this.templateCodeJava = templateCodeJava;
    }

    public String getTemplateCodeCpp() {
        return templateCodeCpp;
    }

    public void setTemplateCodeCpp(String templateCodeCpp) {
        this.templateCodeCpp = templateCodeCpp;
    }

    public String getDriverCodePy() {
        return driverCodePy;
    }

    public void setDriverCodePy(String driverCodePy) {
        this.driverCodePy = driverCodePy;
    }

    public String getDriverCodeJava() {
        return driverCodeJava;
    }

    public void setDriverCodeJava(String driverCodeJava) {
        this.driverCodeJava = driverCodeJava;
    }

    public String getDriverCodeCpp() {
        return driverCodeCpp;
    }

    public void setDriverCodeCpp(String driverCodeCpp) {
        this.driverCodeCpp = driverCodeCpp;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
