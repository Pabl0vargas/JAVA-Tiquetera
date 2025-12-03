# 📦 Guía de Despliegue y Pruebas (HU-6)

Este documento detalla cómo validar la calidad del código, generar métricas y desplegar el sistema completo utilizando contenedores.

---

## 1. ⚙️ Requisitos Previos

- Java 17 SDK instalado.
- Docker Desktop instalado y en ejecución (obligatorio para Testcontainers y Docker Compose).
- Maven (o usar el wrapper `./mvnw`).

---

## 2. 🧪 Ejecución de Pruebas y Cobertura (Task 1)

El sistema cuenta con pruebas unitarias (Dominio) y de integración (Infraestructura con BD real en Docker).

### Paso 2.1: Ejecutar Tests

```bash
./mvnw clean test
```

Esto descargará una imagen de MySQL temporal, ejecutará las migraciones y validará los endpoints.

### Paso 2.2: Verificar Cobertura (JaCoCo)

Al finalizar los tests, se genera un reporte HTML.

1. Navega a: `target/site/jacoco/index.html`  
2. Abre el archivo en tu navegador.  
3. Verifica que la cobertura en los paquetes `application` y `domain` sea superior al **70%**.

---

## 3. 📈 Monitoreo y Métricas (Task 2)

El sistema expone métricas operativas mediante **Spring Boot Actuator**.

- Estado de Salud: `http://localhost:8082/actuator/health`
- Métricas Prometheus: `http://localhost:8082/actuator/prometheus`
- Info del Sistema: `http://localhost:8082/actuator/info`

---

## 4. 🐳 Despliegue con Docker Compose (Task 3)

Levantamos el ecosistema completo (Microservicios + BD) con un solo comando.

### Paso 4.1: Construir y Levantar

```bash
docker-compose up --build
```

### Paso 4.2: Verificar Servicios

Una vez finalizado el despliegue:

| Servicio       | URL                                           | Descripción                     |
|---------------|-----------------------------------------------|---------------------------------|
| API Tiquetera | `http://localhost:8082/swagger-ui.html`       | Backend conectado a MySQL       |
| Eureka Server | `http://localhost:8761`                       | Service Discovery               |
| MySQL         | `localhost:3306`                              | Base de datos persistente       |

### Paso 4.3: Detener el Entorno

```bash
docker-compose down
```

---

## 5. ✅ Checklist de Aceptación

- [ ] Pruebas: `mvn test` termina en **BUILD SUCCESS**.  
- [ ] Cobertura: Reporte JaCoCo generado con **> 70%**.  
- [ ] Docker: `docker-compose up` levanta los 3 contenedores (App, MySQL, Eureka).  
- [ ] Integración: Swagger funciona y permite crear eventos (persistiendo en MySQL).  
- [ ] Observabilidad: Endpoint `/actuator/health` responde `UP`.  
