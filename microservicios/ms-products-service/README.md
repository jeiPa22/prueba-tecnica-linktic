# Resultados Pruebas.
<img width="1912" height="924" alt="image" src="https://github.com/user-attachments/assets/60aa8591-7f58-4fdc-8e91-ed67ba317454" />

# Descripción
Microservicio de gestión de productos que expone CRUD con respuestas en formato JSON:API y documentación OpenAPI/Swagger UI.

# Stack técnico
Java 21, Spring Boot 3.x, Spring Data JPA, PostgreSQL, Flyway, Springdoc OpenAPI, Docker multi-stage y Docker Compose.

# Arquitectura
- Patrón microservicios con “Database per Service” o esquema dedicado, endpoints REST contractados con JSON:API y documentación generada con springdoc-openapi.
- Se utiliza arquitectura hexagonal y DDD

# Estandar API
JSON:API: usar media type application/vnd.api+json, y estructuras con links, data, attributes, relationships e included.

# Endpoints principales
GET/POST/PUT/DELETE /api/v1/products con ejemplos JSON:API y códigos de estado estándar descritos en OpenAPI.

# Seguridad
Autenticación simple por API Key en header configurable y documentada como SecurityScheme en OpenAPI.

# Configuración
Perfiles Spring: local y docker con URLs JDBC distintas y propiedades de springdoc para habilitar /swagger-ui.html y /v3/api-docs.

# Base de datos y migraciones
PostgreSQL con Flyway para versionado, esquema/tabla de historial dedicados al servicio para evitar colisiones.

# Ejecución local
mvn spring-boot:run -Dspring-boot.run.profiles=local y acceso a Swagger UI en /swagger-ui.html.

# Contenedores
Dockerfile multi-stage (Maven build + Temurin JRE) e imagen acme/products-service:latest.

# Docker Compose
Servicios products-app y products-db con healthcheck, mapeos 8080:8080 y 5433:5432, y conexión interna por nombre de servicio.

# Variables de entorno
SPRING_PROFILES_ACTIVE, SPRING_DATASOURCE_URL/USERNAME/PASSWORD y PRODUCTS_API_KEY para credenciales y perfil activo.

# Logs
Logs estructurados en consola (ECS/GELF/Logstash) para observabilidad y correlación.

# Pruebas
Pruebas unitarias y de controlador con cobertura mínima acordada; publicar comando mvn test y reporte.

# Troubleshooting
UnknownHostException al usar nombres de servicio fuera de Compose: usar localhost y puerto publicado en perfil local.

# Roadmap
Autenticación entre servicios más robusta, mejoras de observabilidad, pruebas de contrato y versionado de API (v1→v2).

# Propuesta
En products se propone escalar con un gateway que centralice autenticación, rate limiting y caché; usar paginación por cursores en listados; escalar horizontalmente en contenedores con orquestación (HPA) y reforzar resiliencia con timeouts, retries y circuit breakers; introducir caché de lectura (p. ej., Redis) para catálogos “hot”; mejorar observabilidad con logs estructurados, trazas distribuidas, métricas, correlation IDs y SLOs; formalizar versionado de API con deprecaciones y canary releases; y optimizar datos con réplicas de lectura y, a futuro, particionamiento o sharding y eventos de dominio para sincronización e invalidación asíncrona de caché.
