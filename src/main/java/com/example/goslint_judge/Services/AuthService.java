package com.example.goslint_judge.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.goslint_judge.models.RegisterRequest;
import com.example.goslint_judge.models.User;
import com.example.goslint_judge.models.UserResponse;
import com.example.goslint_judge.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
