# 🎟️ Tiquetera API (Fase 3) - Arquitectura Hexagonal

Este proyecto es una API RESTful para la gestión de venta de tiquetes, que ha evolucionado desde un monolito en capas hacia una Arquitectura Hexagonal (Puertos y Adaptadores).

El objetivo principal de esta refactorización (HU-3) fue desacoplar el núcleo de negocio de los frameworks tecnológicos (Spring Boot, JPA, Web), garantizando mantenibilidad, testabilidad e independencia tecnológica.

---

## 🔄 Evolución del Proyecto: De Capas a Hexagonal

### ❌ Antes (HU-2: Arquitectura en Capas)

- **Dependencia Fuerte**: La lógica de negocio (`Service`) dependía directamente de los Repositorios JPA y de las anotaciones de Spring.
- **Entidades Contaminadas**: Las clases de base de datos (`@Entity`) se usaban en todas partes, mezclando reglas de negocio con persistencia.
- **Difícil de Testear**: Para probar la lógica, era obligatorio levantar el contexto de Spring o mockear demasiadas dependencias.

### ✅ Ahora (HU-3: Arquitectura Hexagonal)

Se ha reestructurado todo el proyecto en tres capas concéntricas:

#### 🟢 Dominio (Núcleo)

- Contiene **Modelos Puros (POJOs)** sin anotaciones de ningún framework.
- Define **Puertos (Interfaces)**:
  - **Puertos de Entrada (In)**: Casos de Uso (lo que la app hace).
  - **Puertos de Salida (Out)**: Repositorios (lo que la app necesita).
- Tecnología: **Java Puro + Lombok**.

#### 🟡 Aplicación (Casos de Uso)

- Implementa los Puertos de Entrada (`UseCaseImpl`).
- Orquesta la lógica de negocio.
- Usa los Puertos de Salida para comunicarse con el exterior.
- **Importante**: No usa `@Service` ni `@Autowired`. La inyección se hace por **constructor**.

#### 🔴 Infraestructura (Adaptadores)

Es la capa "sucia" que interactúa con el mundo exterior:

- **Adapter Web**: Controladores REST (`EventController`).
- **Adapter Persistence**: Implementación de JPA, H2 y Mappers.
- **Config**: Clase `BeanConfiguration` que inyecta manualmente las dependencias usando `@Bean`.

---

## 🛠️ Stack Tecnológico Actualizado

- Java 17 (LTS para máxima estabilidad).
- Spring Boot 3 (Solo como framework de inyección y web).
- H2 Database (Base de datos en memoria).
- Spring Data JPA (Para la persistencia en el adaptador de salida).
- MapStruct (NUEVO: Para mapeo automático `DTO <-> Dominio <-> Entidad`).
- Lombok (Para reducción de *boilerplate*).
- OpenAPI / Swagger UI (Documentación de API).

---

## 📂 Nueva Estructura del Proyecto

El código ahora sigue estrictamente la separación de responsabilidades:

```text
com.tiquetera
├── domain/                         # 🟢 NÚCLEO (Java Puro)
│   ├── model/                      # Modelos de negocio (Event, Venue)
│   ├── ports/in/                   # Interfaces de Casos de Uso (ManageEventUseCase)
│   └── ports/out/                  # Interfaces de Repositorio (EventRepositoryPort)
│
├── application/                    # 🟡 LÓGICA
│   └── usecase/                    # Implementación de reglas de negocio
│
└── infrastructure/                 # 🔴 FRAMEWORKS & ADAPTADORES
    ├── adapters/in/web/            # Controladores REST y DTOs
    ├── adapters/out/persistence/   # Implementación JPA
    │   ├── entity/                 # Tablas de Base de Datos (@Entity)
    │   ├── mapper/                 # Mappers de MapStruct
    │   ├── repository/             # Interfaces JpaRepository
    │   └── [Adapters]              # Implementación de los Puertos de Salida
    └── config/                     # Configuración de Beans de Spring
```

---

## 🚀 Guía de Ejecución

Debido al uso de MapStruct, es crucial compilar el proyecto correctamente antes de ejecutarlo para que se generen los archivos de implementación de los mappers.

### 1. Compilación (Crucial)

Ejecuta el siguiente comando en la terminal (o usa el panel Maven de tu IDE):

```bash
./mvnw clean install
```

(Si usas IntelliJ, asegúrate de tener configurado  
**"Delegate IDE build/run actions to Maven"** en  
`Settings > Build tools > Maven > Runner`).

### 2. Ejecutar

```bash
./mvnw spring-boot:run
```

### 3. Acceder a Swagger UI

Una vez iniciado, abre:

👉 `http://localhost:8080/swagger-ui.html`

---

## 🧪 Pruebas Funcionales (Paso a Paso)

Aunque la arquitectura interna cambió radicalmente, la API externa mantiene su funcionalidad intacta (Equivalencia Funcional).

### Escenario 1: Gestión de Recintos (Venues)

**Endpoint**

`POST /venues`  
Crea un recinto físico.

**Body de ejemplo**

```json
{
  "name": "Hexagonal Arena",
  "address": "Av. Arquitectura 123",
  "city": "Bogota",
  "capacity": 50000
}
```

**Resultado esperado**: `201 Created`.

---

### Escenario 2: Gestión de Eventos (Events)

#### Crear evento

**Endpoint**

`POST /events`  
Crea un evento asociado al recinto anterior (ID `1`).

**Body de ejemplo**

```json
{
  "name": "Java Architecture Fest",
  "eventDate": "2025-12-20T18:00:00",
  "category": "Tech",
  "venueId": 1
}
```

**Resultado esperado**: `201 Created`.

#### Listar eventos (con filtros y paginación)

**Endpoint**

`GET /events` (Con filtros y paginación)  
Prueba el filtrado dinámico.

**Importante**: En el campo `pageable`, asegúrate de enviar el `sort` vacío o con un campo válido para evitar error `500`.

**Ejemplo de Query Params:**

```text
city=Bogota
```

**Ejemplo de Body pageable:**

```json
{
  "page": 0,
  "size": 10,
  "sort": []
}
```

**Resultado esperado**: `200 OK` con la lista de eventos.

---

### Escenario 3: Validaciones de Negocio

- Intenta crear un evento con el mismo nombre.  
  **Resultado**: `409 Conflict` (Manejado por `DuplicateResourceException`).

- Intenta crear un evento con fecha pasada.  
  **Resultado**: `400 Bad Request` (Validación `@Future`).