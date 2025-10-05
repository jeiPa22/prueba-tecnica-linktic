import { useQuery } from "@tanstack/react-query";
import { env } from "../../../shared/config/env";
import { http } from "../../../shared/lib/http";
import type { JsonApiDoc, ProductResource } from "../model/types";

/**
 * Hook para obtener un producto por su ID
 * @param id ID del producto
 * @returns Consulta del producto
 */
export function useProduct(id: string) {
  /**
   * Consulta del producto
   */
  return useQuery({
    queryKey: ["product", id],
    queryFn: async () =>
      (await http(
        `${env.PRODUCTS_URL}/api/v1/products/${id}`
      )) as JsonApiDoc<ProductResource>,
  });
}

/**
 *  Hook para obtener la lista de productos
 * @param params Parametros de paginacion
 * @returns Consulta de la lista de productos
 */
export function useProducts(params?: { page?: number; size?: number }) {
  const search = new URLSearchParams();
  if (params?.page) search.set("page[number]", String(params.page));
  if (params?.size) search.set("page[size]", String(params.size));
  const qs = search.toString();
  return useQuery({
    queryKey: ["products", params],
    queryFn: async () =>
      (await http(
        `${env.PRODUCTS_URL}/api/v1/products${qs ? `?${qs}` : ""}`
      )) as JsonApiDoc<ProductResource>,
  });
}
