# 🎟️ Tiquetera API (Fase 4) - Persistencia Avanzada y Migraciones

API RESTful con Arquitectura Hexagonal para la gestión de eventos.

Esta versión (**HU-4**) introduce madurez técnica al proyecto mediante el control de versiones de base de datos, transacciones ACID y optimización de alto rendimiento.

---

## 🚀 Novedades de la Versión (HU-4)

### 1. 🗄️ Migraciones de Base de Datos (Flyway)

Ya no dependemos de que Hibernate cree las tablas "mágicamente". Ahora tenemos control total y versionado:

- **V1**: Estructura inicial (Tablas `events`, `venues`).
- **V2**: Índices de rendimiento (`idx_events_category`, `idx_venues_city`).
- **V3**: Ajustes de integridad (`CHECK capacity > 0`) y datos semilla.

### 2. ⚡ Optimización de Consultas (Solución N+1)

Se eliminó el problema de múltiples consultas `select` en cascada.

- **Antes**: 1 consulta para listar eventos + N consultas para obtener el recinto de cada uno.
- **Ahora**: Uso de `@EntityGraph` para traer todo en una sola consulta SQL optimizada con `LEFT JOIN`.
- **Filtros**: Implementación de **JPA Specifications** para búsquedas dinámicas y eficientes.

### 3. 🛡️ Integridad y Transacciones

- **ACID**: Uso de `@Transactional` en la capa de aplicación para garantizar atomicidad.
- **Constraints**: Validaciones duplicadas en BD (Unique Name, Check Capacity) para seguridad robusta.

---

## 🛠️ Stack Tecnológico

- Java 17 (LTS).
- Spring Boot 3 (Web, Validation, Data JPA).
- Flyway (Gestión de Migraciones).
- H2 Database (Base de datos en memoria).
- MapStruct (Mapeo de objetos).
- Lombok (Boilerplate).
- OpenAPI (Documentación).

---

## 📂 Estructura del Proyecto (Hexagonal)

```text
com.tiquetera
├── domain/                         # 🟢 NÚCLEO (Puro)
├── application/                    # 🟡 CASOS DE USO (Transaccionales)
└── infrastructure/                 # 🔴 ADAPTADORES
    ├── adapters/in/web/            # Controllers
    ├── adapters/out/persistence/   # Repository + Entity + Flyway
    │   ├── entity/
    │   ├── mapper/
    │   ├── repository/             # @EntityGraph & Specifications
    │   └── ...
    └── config/
```

---

## 🚀 Guía de Ejecución

### 1. Compilación

Es necesario compilar para generar los Mappers de MapStruct.

```bash
./mvnw clean install
```

### 2. Ejecutar

```bash
./mvnw spring-boot:run
```

El sistema aplicará automáticamente las migraciones **V1**, **V2** y **V3** al iniciar.

### 3. Documentación API

👉 `http://localhost:8082/swagger-ui.html`  
(Nota: El puerto se configuró en **8082**).

---

## 🧪 Pruebas de Aceptación Realizadas

### ✅ Migraciones

- El sistema arranca con datos precargados ("Arena Inicial").
- Las tablas tienen restricciones `CHECK` activas.

### ✅ Rendimiento

- El endpoint `GET /events` ejecuta **1 sola consulta SQL** en lugar de N+1.
- Los filtros por ciudad y fecha se traducen a cláusulas `WHERE` eficientes.

### ✅ Seguridad de Datos

- No permite eventos con nombres duplicados (**Error 409**).
- No permite capacidades negativas (**Error 400/500**).
- Si una operación falla, se hace **Rollback** automático.
