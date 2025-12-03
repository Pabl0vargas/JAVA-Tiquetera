package com.tiquetera.application.usecase;

import com.tiquetera.domain.model.User;
import com.tiquetera.domain.ports.in.AuthUseCase;
import com.tiquetera.domain.ports.out.UserRepositoryPort;
import com.tiquetera.exception.DuplicateResourceException;
import com.tiquetera.infrastructure.config.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class AuthUseCaseImpl implements AuthUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public User register(User user) {
        if (userRepositoryPort.findByUsername(user.getUsername()).isPresent()) {
            throw new DuplicateResourceException("El nombre de usuario ya está en uso");
        }
        // Encriptar contraseña antes de guardar
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepositoryPort.save(user);
    }

    @Override
    public String login(String username, String password) {
        // Spring Security maneja la validación de credenciales aquí
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // Si no lanza excepción, buscamos al usuario para generar el token
        User user = userRepositoryPort.findByUsername(username).orElseThrow();

        // Convertir a UserDetails de Spring para el servicio JWT
        var userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();

        return jwtService.generateToken(userDetails);
    }
}