package com.example.goslint_judge.models;

import java.time.Instant;


public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private Instant createdAt;
    private String firstName;
    private String lastName;
    private String university;
    private String country;
    private String studentId;


    public UserResponse(Long id,
                        String username,
                        String email,
                        Instant createdAt,
                        String firstName,
                        String lastName,
                        String university,
                        String country,
                        String studentId, Instant createdAt1) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.firstName = firstName;
        this.lastName = lastName;
        this.university = university;
        this.country = country;
        this.studentId = studentId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

}
