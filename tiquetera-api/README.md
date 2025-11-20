# 🎟️ Tiquetera API (Fase 2) - Persistencia y Paginación

API RESTful para la gestión de eventos y recintos, construida con **Spring Boot**.
Este proyecto ha evolucionado desde un prototipo en memoria hasta una solución robusta con persistencia de datos, relaciones SQL y manejo avanzado de errores.

## 🔄 Evolución del Proyecto (HU-1 vs HU-2)

### HU-1: Prototipo Inicial (Deprecado)
* **Almacenamiento:** Memoria volátil (`ArrayList`, `HashMap`). Los datos se perdían al reiniciar.
* **Arquitectura:** Capas básicas (Controller -> Service -> Repo Simulado).
* **Funcionalidad:** CRUD básico sin relaciones fuertes.

### HU-2: Persistencia y Calidad (Versión Actual) ✅
* **Almacenamiento:** Base de datos Relacional **H2** (en memoria, compatible con SQL).
* **ORM:** Uso de **Spring Data JPA** y `Hibernate` para mapear entidades.
* **Relaciones:** Implementación real de llave foránea (`@ManyToOne`) entre Eventos y Venues.
* **Validaciones:**
    * Control de duplicados (Error `409 Conflict` si el nombre del evento se repite).
    * Validación lógica (`@Future` para fechas).
* **Paginación:** Endpoints optimizados con `Pageable` (page, size, sort) y filtros dinámicos por ciudad y categoría.

---
## 🚀 Tecnologías Utilizadas

El proyecto está construido con:

* **Java 21** 
* **Spring Boot:** Framework principal.
* **Spring Web:** Para la creación de controladores RESTful.
* **Spring Validation:** Para la validación de DTOs (`@Valid`, `@NotBlank`).
* **Springdoc OpenAPI (Swagger):** Para la documentación interactiva de la API.
* **Lombok:** Para reducir el código repetitivo (getters, setters, etc.) en los DTOs.
* **Maven:** Para la gestión de dependencias y construcción del proyecto.

## 🏁 Cómo Ejecutar el Proyecto

Sigue estos pasos para levantar la aplicación en tu máquina local.

### Pre-requisitos

* Tener instalado [Java JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) (o superior).
* Tener instalado [Apache Maven](https://maven.apache.org/download.cgi).

### Pasos

1.  **Clonar el repositorio:**
    ```bash
    git clone [https://github.com/Pabl0vargas/JAVA-Tiquetera.git](https://github.com/Pabl0vargas/JAVA-Tiquetera.git)
    ```

2.  **Navegar a la carpeta del proyecto:**
    ```bash
    cd tiquetera-api
    ```

3.  **Ejecutar la aplicación con Maven:**
    (Esto compilará e iniciará el servidor web en el puerto 8080)
    ```bash
    mvn spring-boot:run
    ```

4.  **Acceder a la documentación de la API:**
    Una vez que la aplicación esté corriendo, abre tu navegador y ve a:
    **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

    Desde esta interfaz de Swagger podrás ver, probar y ejecutar todos los endpoints de la API.

## 🧪 Guía de Pruebas (Paso a Paso)

Para verificar que la aplicación cumple con los criterios de aceptación, sigue estos pasos usando **Swagger UI**: `http://localhost:8080/swagger-ui.html`

### 1. Crear un Recinto (Venue)
El sistema requiere un recinto antes de crear eventos debido a la integridad referencial.
* **Endpoint:** `POST /venues`
* **Body:**
```json
{
  "name": "Movistar Arena",
  "address": "Diagonal 61c",
  "city": "Bogota",
  "capacity": 14000
}
```
Resultado: Código 201 Created e id: 1.

2. Crear un Evento Exitoso
Endpoint: POST /events

Body:
```
{
"name": "Rock al Parque 2025",
"eventDate": "2025-11-15T18:00:00",
"category": "Musica",
"venueId": 1
}
```
Resultado: Código 201 Created.

3. Probar Validación de Fechas (Error 400)
   Intenta crear un evento con fecha en el pasado.

Body: Cambia "eventDate" a "2020-01-01T...".

Resultado: Código 400 Bad Request - Mensaje: "La fecha del evento debe ser en el futuro".

4. Probar Control de Duplicados (Error 409)
   Intenta crear nuevamente el evento "Rock al Parque 2025" (mismo nombre).

Resultado: Código 409 Conflict - Mensaje: "Ya existe un evento con el nombre...".

5. Probar Paginación y Filtros
   Endpoint: GET /events

Parámetros en Swagger:

city: Bogota

page: 0

size: 5

sort: eventDate,desc

Resultado: Código 200 OK con una estructura paginada (content, totalPages, totalElements).


Acceder a la consola de Base de Datos: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:tiqueteradb

User: sa

Password: (vacio)

## ✅ Criterios Cumplidos

Este proyecto cumple con los siguientes requisitos de la historia de usuario:

* **API RESTful:** Expone endpoints HTTP para el CRUD de dos entidades.
* **Arquitectura por Capas:** Separación clara de responsabilidades:
    * `controller`: Maneja las peticiones HTTP.
    * `service`: Contiene la lógica de negocio.
    * `repository`: Simula el acceso a datos (en memoria).
    * `dto`: Define los objetos de transferencia de datos.
    * `exception`: Manejo centralizado de errores.
* **CRUD Completo:**
    * `POST /events` y `POST /venues`
    * `GET /events` y `GET /venues`
    * `GET /events/{id}` y `GET /venues/{id}`
    * `PUT /events/{id}` y `PUT /venues/{id}`
    * `DELETE /events/{id}` y `DELETE /venues/{id}`
* **Documentación OpenAPI:** La API está documentada y es navegable a través de Swagger UI.
* **Manejo de Errores:** Responde con códigos HTTP adecuados (200, 201, 204, 400 para validaciones y 404 para recursos no encontrados).
* **Perfiles:** Configuración básica de perfiles `dev` y `test` en `application.properties`.

## 📁 Estructura del Proyecto

```
tiquetera-api/
├── .mvn/                       # Archivos del wrapper de Maven (no tocar)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── tiquetera/
│   │   │           │
│   │   │           ├── TiqueteraApiApplication.java  // Clase principal (Main)
│   │   │           │
│   │   │           ├── controller/                   // Capa HTTP (REST)
│   │   │           │   ├── EventController.java      // Actualizado con paginación
│   │   │           │   └── VenueController.java
│   │   │           │
│   │   │           ├── dto/                          // Objetos de transferencia
│   │   │           │   ├── EventDTO.java             // Con validaciones nuevas
│   │   │           │   └── VenueDTO.java
│   │   │           │
│   │   │           ├── entity/                       //  NUEVO PAQUETE (Tablas BD)
│   │   │           │   ├── EventEntity.java          // Tabla 'events'
│   │   │           │   └── VenueEntity.java          // Tabla 'venues'
│   │   │           │
│   │   │           ├── exception/                    // Manejo de Errores
│   │   │           │   ├── DuplicateResourceException.java  //  NUEVO (Para el 409)
│   │   │           │   ├── GlobalExceptionHandler.java      // Actualizado
│   │   │           │   └── ResourceNotFoundException.java
│   │   │           │
│   │   │           ├── repository/                   // Capa de Datos (Interfaces)
│   │   │           │   ├── EventRepository.java      // extends JpaRepository
│   │   │           │   └── VenueRepository.java      // extends JpaRepository
│   │   │           │
│   │   │           └── service/                      // Lógica de Negocio
│   │   │               ├── EventService.java         // Lógica real con JPA
│   │   │               └── VenueService.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties        // Configuración H2 y JPA
│   │       └── application-test.properties   // (Opcional, si lo creaste antes)
│   │
│   └── test/                                 // Pruebas automáticas (por ahora vacío/default)
│
├── .gitignore                  // Archivo para ignorar binarios
├── mvnw                        // Ejecutable Maven Wrapper
├── mvnw.cmd                    // Ejecutable Maven Wrapper (Windows)
├── pom.xml                     // Dependencias (JPA, H2, Web, Lombok, OpenAPI)
└── README.md                   // Documentación actualizada
