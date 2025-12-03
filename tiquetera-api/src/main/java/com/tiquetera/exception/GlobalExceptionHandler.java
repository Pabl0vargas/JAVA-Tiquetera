package com.tiquetera.exception;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;

@RestControllerAdvice
@Slf4j //  Logging estructurado
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFound(ResourceNotFoundException ex) {
        return buildProblemDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ProblemDetail handleDuplicateResource(DuplicateResourceException ex) {
        return buildProblemDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentials(BadCredentialsException ex) {
        return buildProblemDetail(HttpStatus.UNAUTHORIZED, "Usuario o contraseña incorrectos");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDenied(AccessDeniedException ex) {
        return buildProblemDetail(HttpStatus.FORBIDDEN, "No tienes permisos suficientes (Requiere rol ADMIN)");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ProblemDetail handleExpiredJwt(ExpiredJwtException ex) {
        return buildProblemDetail(HttpStatus.UNAUTHORIZED, "El token de sesión ha expirado. Inicia sesión nuevamente.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ")
        );
        return buildProblemDetail(HttpStatus.BAD_REQUEST, errors.toString());
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGlobal(Exception ex) {
        // Logueamos el stack trace completo solo para errores inesperados
        log.error("Error interno no controlado: {}", ex.getMessage(), ex);
        return buildProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error interno en el servidor.");
    }

    private ProblemDetail buildProblemDetail(HttpStatus status, String detail) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
        problem.setTitle(status.getReasonPhrase());
        problem.setType(URI.create("https://tiquetera.api/errors/" + status.value()));
        problem.setProperty("timestamp", LocalDateTime.now());

        //  TraceID para rastreabilidad
        String traceId = UUID.randomUUID().toString();
        problem.setProperty("traceId", traceId);

        // Log estructurado del error
        log.error("Error Response: status={}, detail='{}', traceId={}", status.value(), detail, traceId);

        return problem;
    }
}