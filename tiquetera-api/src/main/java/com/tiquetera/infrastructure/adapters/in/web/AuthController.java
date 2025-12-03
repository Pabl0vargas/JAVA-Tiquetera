package com.tiquetera.infrastructure.adapters.in.web;

import com.tiquetera.domain.model.Role;
import com.tiquetera.domain.model.User;
import com.tiquetera.domain.ports.in.AuthUseCase;
import com.tiquetera.infrastructure.adapters.in.web.dto.AuthRequest;
import com.tiquetera.infrastructure.adapters.in.web.dto.AuthResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación")
public class AuthController {

    private final AuthUseCase authUseCase;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(Role.USER); // Por defecto, quien se registra es USER

        authUseCase.register(user);

        // Auto-login al registrarse para devolver el token inmediatamente
        String token = authUseCase.login(request.getUsername(), request.getPassword());

        return ResponseEntity.ok(AuthResponse.builder().token(token).build());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        String token = authUseCase.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(AuthResponse.builder().token(token).build());
    }
}