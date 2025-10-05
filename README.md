# Prueba Técnica LinkTIC

Este repositorio contiene una aplicación de microservicios con un frontend en React.

## Requisitos

- Docker
- Docker Compose
- Node.js y npm

## Levantamiento de Microservicios

Los microservicios se ejecutan en contenedores de Docker.

### 1. Microservicio de Productos

Navega al directorio del microservicio de productos y levanta el contenedor:

```bash
cd microservicios/ms-products-service
docker-compose up -d
```

El servicio estará disponible en `http://localhost:8080`.

### 2. Microservicio de Inventario

Navega al directorio del microservicio de inventario y levanta el contenedor:

```bash
cd microservicios/ms-inventory-service
docker-compose up -d
```

El servicio estará disponible en `http://localhost:8081`.

## Levantamiento del Frontend

El frontend es una aplicación de React creada con Vite.

1.  **Navega al directorio del frontend:**

    ```bash
    cd frontend/acme-frontend
    ```

2.  **Instala las dependencias:**

    ```bash
    npm install
    ```

3.  **Inicia el servidor de desarrollo:**

    ```bash
    npm run dev
    ```

La aplicación estará disponible en la URL que indique Vite en la terminal (generalmente `http://localhost:5173`).

Tambien Se utiliza para ambos entornos back y front arquitectura hexagonal.
aqui un ejemplo generarl de que debe tener en cuenta para usarla.
<img width="995" height="732" alt="image" src="https://github.com/user-attachments/assets/0f141c82-65dc-4db6-9087-379acf451f72" />

