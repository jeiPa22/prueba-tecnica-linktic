import { keepPreviousData, useQuery } from "@tanstack/react-query";
import { env } from "../../../shared/config/env";
import { httpProducts } from "../../../shared/lib/http";
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
      (await httpProducts(
        `${env.PRODUCTS_URL}/api/v1/products/${id}`
      )) as JsonApiDoc<ProductResource>,
  });
}

export function useProducts(params: { page: number; size: number }) {
  const search = new URLSearchParams();
  search.set("page[number]", String(params.page));
  search.set("page[size]", String(params.size));
  return useQuery({
    queryKey: ["products", params.page, params.size],
    queryFn: async () =>
      (await httpProducts(
        `/api/v1/products?${search.toString()}`
      )) as JsonApiDoc<ProductResource>,
    placeholderData: keepPreviousData,
  });
}
