-- Migración para crear la tabla de productos
CREATE TABLE IF NOT EXISTS products (
        id TEXT PRIMARY KEY,
        name TEXT NOT NULL,
        price NUMERIC(18, 2) NOT NULL CHECK (price >= 0),
        currency CHAR(3) NOT NULL,
        status TEXT NOT NULL,
        created_at TIMESTAMPTZ NOT NULL DEFAULT NOW (),
        updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW ()
);

-- Indexes para optimizar consultas
CREATE INDEX IF NOT EXISTS idx_products_status ON products (status);

-- Índice para el nombre del producto
CREATE INDEX IF NOT EXISTS idx_products_name ON products (name);