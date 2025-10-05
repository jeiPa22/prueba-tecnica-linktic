# ACME Frontend

Este es el proyecto frontend para la aplicación ACME, construido con React y Vite.

## Enfoque y Arquitectura

El proyecto sigue una arquitectura inspirada en **Feature-Sliced Design (FSD)**. Este enfoque organiza el código por capas y por funcionalidad, lo que facilita la escalabilidad, el mantenimiento y la colaboración en el equipo.

La estructura de directorios principal es:

-   `src/app`: Configuración global de la aplicación (store de Redux, proveedores de contexto, etc.).
-   `src/pages`: Componentes que representan las páginas completas de la aplicación.
-   `src/widgets`: Bloques de UI complejos y compuestos que se usan en las páginas (ej. encabezados, barras laterales).
-   `src/features`: Funcionalidades específicas del negocio (ej. catálogo de productos, autenticación).
-   `src/entities`: Entidades de negocio y sus componentes relacionados (ej. un producto, un usuario).
-   `src/shared`: Código reutilizable que no está atado a ninguna lógica de negocio específica (ej. componentes de UI, hooks, helpers).

## Tecnologías Utilizadas

-   **Framework**: [React](https://react.dev/)
-   **Lenguaje**: [TypeScript](https://www.typescriptlang.org/)
-   **Build Tool**: [Vite](https://vitejs.dev/)
-   **Gestión de Estado**: [Redux Toolkit](https://redux-toolkit.js.org/)
-   **Data Fetching & Cache**: [TanStack Query](https://tanstack.com/query/latest)
-   **Librería de UI**: [Material-UI (MUI)](https://mui.com/)
-   **Styling**: [Emotion](https://emotion.sh/docs/introduction)
-   **Testing**: [Vitest](https://vitest.dev/) y [React Testing Library](https://testing-library.com/docs/react-testing-library/intro/)

## Scripts Disponibles

En el directorio del proyecto, puedes ejecutar:

### `npm install`

Instala todas las dependencias necesarias para el proyecto.

### `npm run dev`

Inicia la aplicación en modo de desarrollo.
Abre [http://localhost:5173](http://localhost:5173) para verla en tu navegador.

### `npm run build`

Compila la aplicación para producción en la carpeta `dist`.

### `npm test`

Ejecuta los tests de la aplicación en modo interactivo.
