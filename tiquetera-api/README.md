# 🎟️ Tiquetera API - Catálogo de Eventos y Recintos

Este proyecto es una API RESTful construida con Spring Boot que sirve como la primera versión del catálogo para una plataforma de venta de tiquetes online. Permite gestionar (Crear, Leer, Actualizar y Eliminar) Eventos y Recintos (Venues) utilizando una arquitectura por capas.

Actualmente, los datos se almacenan en memoria temporal (usando `List<>` simulando un repositorio) y se reinician cada vez que la aplicación se detiene.

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
├── .mvn/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── tiquetera/
│   │   │           ├── TiqueteraApiApplication.java      # Clase principal
│   │   │
│   │   │           ├── controller/                       # Controladores REST
│   │   │           │   ├── EventController.java
│   │   │           │   └── VenueController.java
│   │   │
│   │   │           ├── dto/                              # Data Transfer Objects
│   │   │           │   ├── EventDTO.java
│   │   │           │   └── VenueDTO.java
│   │   │
│   │   │           ├── exception/                        # Manejo global de excepciones
│   │   │           │   ├── GlobalExceptionHandler.java
│   │   │           │   └── ResourceNotFoundException.java
│   │   │
│   │   │           ├── repository/                       # Repositorios (simulados)
│   │   │           │   ├── EventRepository.java
│   │   │           │   └── VenueRepository.java
│   │   │
│   │   │           └── service/                          # Lógica de negocio
│   │   │               ├── EventService.java
│   │   │               └── VenueService.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties                    # Perfil dev
│   │       └── application-test.properties               # Perfil test
│   │
│   └── test/
│
├── .gitignore
├── mvnw
├── mvnw.cmd
└── pom.xml
