/**
 * Tipos relacionados con el modelo de producto y la API JSON:API
 */
export type JsonApiResource<TAttrs> = {
  /**
   * Tipo del recurso
   */
  type: string;
  /**
   * ID del recurso
   */
  id: string;
  /**
   * Atributos del recurso
   */
  attributes: TAttrs;
  /**
   * Relaciones del recurso
   */
  relationships?: Record<string, unknown>;
  /**
   * Enlaces del recurso
   */
  links?: { self: string };
};

/**
 * Atributos del modelo de producto
 */
export type ProductAttrs = {
  /**
   * Nombre del producto
   */
  name: string;
  /**
   * Descripcion del producto
   */
  price: number;
  /**
   * Moneda del producto
   */
  currency: string;
  /**
   * Estado del producto
   */
  status: "ACTIVE" | "INACTIVE";
  /**
   * Fecha de creacion del producto
   */
  createdAt: string;
  /**
   * Fecha de actualizacion del producto
   */
  updatedAt: string;
};

/**
 * Recurso de producto en formato JSON:API
 */
export type ProductResource = JsonApiResource<ProductAttrs>;

/**
 * Documento JSON:API
 */
export type JsonApiDoc<T> = {
  links?: { self: string };
  data: T | T[];
  included?: unknown[];
  meta?: Record<string, unknown>;
};
