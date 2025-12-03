package com.tiquetera.infrastructure.config;

import com.tiquetera.application.usecase.AuthUseCaseImpl;
import com.tiquetera.application.usecase.EventUseCaseImpl;
import com.tiquetera.application.usecase.VenueUseCaseImpl;
import com.tiquetera.domain.ports.in.AuthUseCase;
import com.tiquetera.domain.ports.in.ManageEventUseCase;
import com.tiquetera.domain.ports.in.ManageVenueUseCase;
import com.tiquetera.domain.ports.out.EventRepositoryPort;
import com.tiquetera.domain.ports.out.UserRepositoryPort;
import com.tiquetera.domain.ports.out.VenueRepositoryPort;
import com.tiquetera.infrastructure.adapters.out.persistence.repository.UserRepository;
import com.tiquetera.infrastructure.config.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final UserRepository userRepository; // Inyección directa del repositorio JPA para Auth

    // --- BEANS DE SEGURIDAD ---

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .map(userEntity -> org.springframework.security.core.userdetails.User.builder()
                        .username(userEntity.getUsername())
                        .password(userEntity.getPassword())
                        .roles(userEntity.getRole().name())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // --- CASOS DE USO ---

    @Bean
    public ManageVenueUseCase manageVenueUseCase(VenueRepositoryPort venueRepositoryPort) {
        return new VenueUseCaseImpl(venueRepositoryPort);
    }

    @Bean
    public ManageEventUseCase manageEventUseCase(EventRepositoryPort eventRepositoryPort, VenueRepositoryPort venueRepositoryPort) {
        return new EventUseCaseImpl(eventRepositoryPort, venueRepositoryPort);
    }

    @Bean
    public AuthUseCase authUseCase(UserRepositoryPort userRepositoryPort,
                                   PasswordEncoder passwordEncoder,
                                   JwtService jwtService,
                                   AuthenticationManager authenticationManager) {
        return new AuthUseCaseImpl(userRepositoryPort, passwordEncoder, jwtService, authenticationManager);
    }
}