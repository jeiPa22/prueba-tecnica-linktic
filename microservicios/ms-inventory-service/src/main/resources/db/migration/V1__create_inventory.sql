-- crear la tabla de inventario si no existe
CREATE TABLE
  IF NOT EXISTS inventory (
    product_id TEXT PRIMARY KEY,
    quantity INTEGER NOT NULL CHECK (quantity >= 0),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW ()
  );