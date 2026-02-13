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


    /**
     * Crea una nueva instancia de {@code UserResponse}.
     *
     * @param id          identificador único del usuario.
     * @param username    nombre de usuario visible en la plataforma.
     * @param email       correo electrónico asociado a la cuenta del usuario.
     * @param createdAt   marca de tiempo que indica la fecha y hora de creación de la cuenta del usuario.
     * @param firstName   nombre del usuario.
     * @param lastName    apellido del usuario.
     * @param university  universidad a la que pertenece el usuario.
     * @param country     país de residencia del usuario.
     * @param studentId   identificador de estudiante proporcionado por la institución (si aplica).
     * @param createdAt1  marca de tiempo adicional relacionada con la creación o registro del usuario
     *                    (por ejemplo, para auditoría o trazabilidad).
     */
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
