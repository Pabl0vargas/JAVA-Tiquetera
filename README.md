# 🎟️ Tiquetera API (Fase 5) - Seguridad y Robustez

API RESTful con Arquitectura Hexagonal para la gestión de eventos.

Esta versión (**HU-5**) transforma el sistema en una aplicación **Segura (Stateless)** y **Confiable**, implementando autenticación JWT, control de acceso por roles (RBAC) y un manejo de errores estandarizado bajo normas internacionales.

---

## 🚀 Novedades de la Versión (HU-5)

### 1. 🔐 Seguridad Robusta (Spring Security + JWT)

El sistema ya no es público. Se ha implementado un esquema de seguridad completo:

- **Autenticación Stateless**: Uso de JSON Web Tokens (JWT). No se guardan sesiones en el servidor.
- **Usuarios y Roles**: Nueva tabla `users` con contraseñas encriptadas (BCrypt).
- **Control de Acceso (RBAC)**:
  - **Rutas Públicas**: Login, Registro, Swagger.
  - **Rutas Privadas**: Gestión de Eventos y Venues (requieren rol `ADMIN` para operaciones de escritura).

### 2. 🚨 Manejo de Errores (RFC 7807)



- **Standard Format**: Todas las excepciones responden con el formato `ProblemDetail` (RFC 7807 de la IETF).
- **Trazabilidad**: Cada error incluye un `traceId` único y un `timestamp` para facilitar la depuración en los logs.
- **Logging Estructurado**: Registro detallado de eventos (SLF4J) con niveles (`INFO`, `ERROR`) y contexto.

### 3. 🏗️ Arquitectura Ajustada

- Se rompieron dependencias circulares moviendo la configuración de Beans (`BeanConfiguration`) fuera de la configuración de seguridad (`SecurityConfig`).
- Se añadió el dominio de **Usuario** y **Auth** siguiendo los principios hexagonales (Puertos y Adaptadores).

---

## 🛠️ Stack Tecnológico

- Java 17 (LTS).
- Spring Boot 3 (Web, Validation, Data JPA, Security).
- Spring Security 6 & JJWT (Seguridad).
- Flyway (V4: Tablas de seguridad).
- H2 Database (Base de datos en memoria).
- MapStruct & Lombok.
- OpenAPI (Documentación).

---

## 📂 Estructura de Seguridad

```text
com.tiquetera
├── domain/
│   ├── model/                      # User, Role
│   └── ports/in/                   # AuthUseCase
├── application/                    # AuthUseCaseImpl (Lógica de login/registro)
└── infrastructure/
    ├── config/
    │   ├── security/               # JwtService, Filters, SecurityConfig
    │   └── BeanConfiguration.java  # Beans de Auth (PasswordEncoder, etc.)
    ├── adapters/in/web/            # AuthController
    └── adapters/out/persistence/   # UserEntity, Repository
```

---

## 🚀 Guía de Ejecución

### 1. Compilación

Es necesario compilar para generar los Mappers y asegurar la integridad de las dependencias de seguridad.

```bash
./mvnw clean install
```

### 2. Ejecutar

```bash
./mvnw spring-boot:run
```

El puerto configurado es: **8082**.

### 3. Documentación API

👉 `http://localhost:8082/swagger-ui.html`

---

## 🧪 Pruebas de Aceptación (Flujo de Seguridad)

Para interactuar con la API, ahora debes autenticarte.

### Paso 1: Intentar acceso no autorizado (Prueba de Fallo)

Intenta hacer un `POST /venues` sin token.

- **Resultado**: `403 Forbidden` (Acceso denegado).

### Paso 2: Autenticación (Obtener Token)

El sistema inicia con un usuario administrador por defecto (gracias a la migración V4).

- **Endpoint**: `POST /auth/login`  
- **Credenciales**:

```json
{
  "username": "admin",
  "password": "admin123"
}
```

- **Resultado**: `200 OK` con un JSON similar a:

```json
{
  "token": "eyJhbGciOi..."
}
```

> Acción: Copia ese token (sin las comillas).

### Paso 3: Usar el Token

En Swagger:

1. Haz clic en el botón verde **Authorize** (arriba a la derecha).
2. Escribe: `Bearer TU_TOKEN_AQUI` (ej: `Bearer eyJhb...`).
3. Haz clic en **Authorize** y luego **Close**.

### Paso 4: Operación Exitosa

Vuelve a intentar:

- `POST /venues`  
- o `GET /events`

- **Resultado**: `201 Created` o `200 OK`. ¡Ahora tienes permiso!

### Paso 5: Prueba de Errores (RFC 7807)

Intenta registrar un usuario que ya existe (`/auth/register` con `"admin"`).

- **Resultado**: JSON estructurado con `type`, `title`, `status`, `detail` y `traceId`.
