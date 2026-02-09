package com.example.goslint_judge.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.goslint_judge.config.JwtTokenProvider;
import com.example.goslint_judge.models.LoginRequest;
import com.example.goslint_judge.models.RegisterRequest;
import com.example.goslint_judge.models.UpdateUserRequest;
import com.example.goslint_judge.models.User;
import com.example.goslint_judge.models.UserResponse;
import com.example.goslint_judge.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository,
                       JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Registra un nuevo usuario en el sistema a partir de los datos recibidos en el request.
     *
     * @param request Objeto que contiene los datos necesarios para registrar un usuario (username, email, password, etc).
     * @return UserResponse con los datos del usuario registrado (sin exponer la contraseña).
     * @throws IllegalArgumentException si el username o email ya existen, o si los datos no cumplen las validaciones.
     */
    @Transactional
    public UserResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already in use");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();
        user.setUsername(request.getUsername().trim());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        user.setFirstName(request.getFirstName().trim());
        user.setLastName(request.getLastName().trim());
        user.setUniversity(request.getUniversity().trim());
        user.setCountry(request.getCountry().trim());
        user.setStudentId(request.getStudentId().trim());

        User saved = userRepository.save(user);

        return new UserResponse(
                saved.getId(),
                saved.getUsername(),
                saved.getEmail(),
                saved.getCreatedAt(),
                saved.getFirstName(),
                saved.getLastName(),
                saved.getUniversity(),
                saved.getCountry(),
                saved.getStudentId(),
                saved.getCreatedAt()
        );
    }

    @Transactional
    public String login(LoginRequest request) {
        String usernameOrEmail = request.getUsernameOrEmail().trim();

        User user = userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail))
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return jwtTokenProvider.generateToken(user);
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getFirstName(),
                user.getLastName(),
                user.getUniversity(),
                user.getCountry(),
                user.getStudentId(),
                user.getCreatedAt()
        );
    }

    /**
     * Obtiene la lista de todos los usuarios registrados en el sistema.
     *
     * @return Lista de UserResponse, cada uno representando un usuario (sin exponer contraseñas).
     */
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getCreatedAt(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getUniversity(),
                        user.getCountry(),
                        user.getStudentId(),
                        user.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }


    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName().trim());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName().trim());
        }
        if (request.getUniversity() != null) {
            user.setUniversity(request.getUniversity().trim());
        }
        if (request.getCountry() != null) {
            user.setCountry(request.getCountry().trim());
        }
        if (request.getStudentId() != null) {
            user.setStudentId(request.getStudentId().trim());
        }

        User saved = userRepository.save(user);

        return new UserResponse(
                saved.getId(),
                saved.getUsername(),
                saved.getEmail(),
                saved.getCreatedAt(),
                saved.getFirstName(),
                saved.getLastName(),
                saved.getUniversity(),
                saved.getCountry(),
                saved.getStudentId(),
                saved.getCreatedAt()
        );
    }
}
