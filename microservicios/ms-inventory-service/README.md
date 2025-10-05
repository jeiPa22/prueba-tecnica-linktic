# Descripción
- Microservicio de inventario que gestiona existencias y consulta productos, documentado con OpenAPI/Swagger UI y respuestas JSON legibles.
- Incluye migraciones con Flyway, perfiles “local” y “docker”, y logs estructurados en formato ECS para observabilidad.

# Stack técnico
- Java 21, Spring Boot 3.x, Spring Data JPA, PostgreSQL, Flyway, Resilience4j, Springdoc OpenAPI y Docker multi‑stage.
- Orquestación con Docker Compose y red interna para resolver por nombre de servicio los contenedores dependientes.

# Arquitectura
- Patrón microservicios con “Database per Service” o esquema dedicado inventory y tabla de historial propia para las migraciones.
- Comunicación dentro de Compose por service name (p. ej., inventory-db:5432 y products-app:8080) sin depender de puertos del host.

# Perfiles
- local: base de datos publicada en el host (5434) y productos en localhost:8080 para desarrollar desde el equipo.
- docker: base de datos por nombre de servicio (inventory-db:5432) y productos por products-app:8080 en la red de Compose.

# Configuración
- No fijar spring.profiles.active en archivos base y activar por variable SPRING_PROFILES_ACTIVE o parámetro de ejecución.

- Personalizar springdoc.api-docs.enabled y springdoc.swagger-ui.enabled para exponer /v3/api-docs y /swagger-ui.html según necesidad.

# Ejecución local
- Arrancar con Maven usando el perfil local: mvn spring-boot:run -Dspring-boot.run.profiles=local en ms-inventory-service.
- Alternativa con JAR: mvn -DskipTests clean package y luego SPRING_PROFILES_ACTIVE=local java -jar target/*.jar.

# Docker (imagen)
- Construcción multi‑stage: docker build -t acme/inventory-service:latest . desde la carpeta del servicio.
- Seleccionar perfil y puerto en tiempo de ejecución vía SPRING_PROFILES_ACTIVE y SERVER_PORT en variables de entorno.

# Docker Compose
- inventory-app depende de inventory-db con healthcheck y se publica en el host 8081, mientras la base se publica en 5434 para acceso externo.
- La URL JDBC dentro de Compose debe usar el nombre del servicio y el puerto del contenedor (jdbc:postgresql://inventory-db:5432/postgres).

# Base de datos y migraciones
-Flyway versiona el esquema inventory con tabla flyway_schema_history_inventory para aislar el historial del resto de servicios.
- Validar Hibernate contra el esquema por defecto (hibernate.default_schema=inventory) para evitar errores de “missing table” al iniciar.

# API y documentación
- Swagger UI disponible en /swagger-ui.html y especificación en /v3/api-docs, personalizable vía propiedades de springdoc.
- Mantener contratos claros con códigos de estado estándar y esquemas consistentes para mejorar DX y pruebas.

# Resiliencia
- Usar Resilience4j para timeouts, retries y circuit breakers en llamadas al servicio de productos, evitando fallas en cascada.
- Complementar con healthchecks de Compose y políticas de arranque ordenado para mejorar la robustez operacional.

# Logs estructurados
- Habilitar logging.structured.format.console=ecs para salida JSON estructurada y metadatos de servicio en consola.
-Mantener niveles y campos coherentes para facilitar correlación y trazabilidad en entornos de observabilidad.

# Variables de entorno clave
- SPRING_PROFILES_ACTIVE: local o docker para activar el perfil adecuado por entorno.
- SPRING_DATASOURCE_URL/USERNAME/PASSWORD: JDBC interno por service name en docker o localhost:5434 en local.

# Troubleshooting
- UnknownHostException al usar nombres de servicio fuera de Compose: cambiar a localhost y puerto publicado en el perfil local.
- Fallos de inicio de Postgres en Compose: definir POSTGRES_USER, POSTGRES_PASSWORD y POSTGRES_DB, y revisar logs del contenedor.

# Propuesta
Para inventario, la propuesta es escalar con un gateway al frente para autenticación y rate limiting, y reforzar resiliencia en las llamadas a products con Resilience4j (timeouts, retries con backoff y circuit breakers) y límites de concurrencia por bulkheads; adoptar un modelo event‑driven para reservas/ajustes de stock con outbox + mensajería (CDC/Debezium) para consistencia eventual, y usar Sagas de orquestación o coreografía para coordinar transacciones distribuidas entre pedidos, pagos e inventario con compensaciones claras; añadir caché de lectura para stocks consultados frecuentemente, métricas, trazas y logs estructurados para SLOs y diagnósticos, y preparar despliegue en Kubernetes con HPA y separación de pools para absorber picos, junto con particionamiento o colas de trabajo si la demanda crece.
