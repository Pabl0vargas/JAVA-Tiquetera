# 🎟️ Tiquetera API - Sistema de Gestión de Eventos (Entrega Final HU-6)

Backend profesional para una plataforma de venta de tiquetes, desarrollado bajo una **Arquitectura Hexagonal**, con altos estándares de **seguridad**, **calidad de código** y **despliegue contenerizado**.

---

## 📜 Evolución del Proyecto (Historial de HUs)

Este proyecto ha madurado a través de **6 fases iterativas**, transformándose de un prototipo simple a un sistema distribuido y seguro.

### 🔹 HU-1: Prototipo Inicial

- **Estado**: Monolito simple.  
- **Persistencia**: Memoria volátil (`ArrayList`, `HashMap`).  
- **Alcance**: CRUD básico de **Eventos** y **Venues**.

### 🔹 HU-2: Persistencia Relacional

- **Cambio**: Integración de **Spring Data JPA** y base de datos **H2**.  
- **Mejora**:
  - Validación de datos (`@Valid`).
  - Paginación de resultados (`Pageable`).

### 🔹 HU-3: Arquitectura Hexagonal (Refactor Major)

- **Cambio**: Desacoplamiento total del núcleo.  
- **Estructura**:
  - **Dominio**: Modelos puros y Puertos (Interfaces).
  - **Aplicación**: Casos de Uso (Lógica de negocio).
  - **Infraestructura**: Adaptadores (Web, Persistencia).
- **Tecnología**: Implementación de **MapStruct** para mapeo de objetos.

### 🔹 HU-4: Optimización y Migraciones

- **Base de Datos**: Control de versiones con **Flyway** (Scripts SQL V1, V2, V3).  
- **Rendimiento**: Solución al problema **N+1** usando `@EntityGraph`.  
- **Transacciones**: Integridad de datos con `@Transactional` (**ACID**).

### 🔹 HU-5: Seguridad Robusta

- **Autenticación**: Implementación de **JWT (JSON Web Tokens)** *stateless*.  
- **Usuarios**: Nueva tabla `users` con contraseñas encriptadas (**BCrypt**).  
- **RBAC**: Control de acceso por roles (**ADMIN** vs **USER**).  
- **Errores**: Estandarización de respuestas de error (**RFC 7807 ProblemDetails**).

### 🔹 HU-6: DevOps y Calidad (Versión Actual)

- **Contenerización**: `Dockerfile` multi-stage para producción.  
- **Orquestación**: **Docker Compose** (API + MySQL + Eureka).  
- **Observabilidad**: Métricas con **Actuator** y **Prometheus**.  
- **Testing**: Pruebas Unitarias y de Integración (**Testcontainers**).

---

## 🛠️ Stack Tecnológico Final

| Capa       | Tecnología                               | Versión   |
|-----------|-------------------------------------------|-----------|
| Lenguaje  | Java (OpenJDK)                            | 17 (LTS)  |
| Framework | Spring Boot                               | 3.2.3     |
| Base de Datos | MySQL (Prod) / H2 (Test)              | 8.0       |
| Migraciones | Flyway                                  | 9.x       |
| Seguridad | Spring Security + JJWT                    | 6.x       |
| Mapper    | MapStruct                                 | 1.5.5     |
| DevOps    | Docker & Docker Compose                   | Latest    |
| Testing   | JUnit 5, Mockito, Testcontainers          | -         |
| Cobertura | JaCoCo Plugin                             | 0.8.11    |

---

## 🐳 Guía de Instalación de Docker

Para ejecutar este proyecto, necesitas el motor de contenedores **Docker**.

### 🪟 En Windows

1. Ir a **Docker Desktop for Windows**.  
2. Descargar e instalar el ejecutable.  
3. **Importante**: Durante la instalación, asegurar que la opción `"Use WSL 2 instead of Hyper-V"` esté marcada (recomendado).  
4. Reiniciar el equipo.  
5. Abrir **Docker Desktop** y esperar a que el estado sea **"Engine Running"** (Verde).

### 🐧 En Linux (Ubuntu/Debian)

Ejecutar en terminal:

```bash
# 1. Actualizar repositorios
sudo apt-get update

# 2. Instalar dependencias
sudo apt-get install ca-certificates curl gnupg

# 3. Agregar llave GPG oficial de Docker
sudo install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
sudo chmod a+r /etc/apt/keyrings/docker.gpg

# 4. Instalar Docker Engine
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

# 5. Verificar instalación
sudo docker run hello-world
```

---

## 🚀 Despliegue del Sistema (Paso a Paso)

Una vez instalado Docker, sigue estos pasos para levantar todo el ecosistema (**API**, **Base de Datos** y **Service Discovery**).

### 1. Clonar y Ubicarse

```bash
git clone <https://github.com/Pabl0vargas/JAVA-Tiquetera/tree/feature/hu-6>
cd tiquetera-api
```

### 2. Construir y Levantar Contenedores

Ejecuta el siguiente comando en la raíz del proyecto (donde está `docker-compose.yml`):

```bash
docker compose up --build
```

> En versiones antiguas de Docker en Linux puede ser `docker-compose up --build` (con guion).

### 3. Verificar Estado

Espera unos segundos hasta que los logs se estabilicen. Deberías ver **3 servicios activos**:

- `tiquetera-mysql` (Puerto **3307 externo** -> **3306 interno**).  
- `eureka-server` (Puerto **8761**).  
- `tiquetera-api` (Puerto **8082**).  

---

## 🌐 Accesos y Visualización

Una vez el sistema esté arriba, puedes acceder a las siguientes interfaces:

### 1. Documentación API (Swagger UI)

- **URL**: `http://localhost:8082/swagger-ui.html`  
- **¿Qué es?** Interfaz gráfica para probar todos los endpoints (crear eventos, ver venues, login, etc.).

### 2. Service Discovery (Eureka)

- **URL**: `http://localhost:8761`  
- **¿Qué es?** Panel de control de microservicios. Debes ver **TIQUETERA-API** listado en _"Instances currently registered"_.

### 3. Métricas de Salud (Actuator)

- **URL**: `http://localhost:8082/actuator/health`  
- **¿Qué es?** JSON que indica si el sistema está `UP` (arriba) y si tiene conexión con la base de datos.

---

## 🔐 Guía de Seguridad y Login

El sistema es **seguro por defecto**. La mayoría de operaciones de escritura (`POST`, `PUT`, `DELETE`) requieren autenticación.

### Credenciales por Defecto (Creadas en Migración V4)

- **Usuario**: `admin`  
- **Contraseña**: `admin123`  
- **Rol**: `ADMIN`  

### Paso a Paso para Autenticarse en Swagger

1. Ve a la sección **Auth** -> endpoint `POST /auth/login`.  
2. Ejecuta el request con las credenciales:

   ```json
   {
     "username": "admin",
     "password": "admin123"
   }
   ```

3. Copia el token que aparece en la respuesta (`eyJhbGciOi...`).  
4. Sube al inicio de la página y haz clic en el botón verde **Authorize**.  
5. Escribe: `Bearer <TU_TOKEN_COPIADO>` (Ej: `Bearer eyJhb...`).  
6. Haz clic en **Authorize** y cierra la ventana.  

> ¡Listo! Ya puedes ejecutar endpoints protegidos como `POST /venues`.

---

## 🧪 Ejecución de Pruebas y Calidad

El proyecto incluye una suite de pruebas robusta.

### Ejecutar Tests

```bash
./mvnw clean test
```

> Nota: En entornos Windows con restricciones de Docker, algunas pruebas de integración podrían estar marcadas con `@Disabled`. Las pruebas unitarias siempre se ejecutan.

### Reporte de Cobertura (JaCoCo)

Tras ejecutar los tests, se genera un informe HTML detallado.

- **Ruta**: `target/site/jacoco/index.html`  
- **Abrir**: Arrastra ese archivo a tu navegador.  
- **Meta**: Verificar cobertura **> 70%** en paquetes de **Dominio** y **Aplicación**.

---


