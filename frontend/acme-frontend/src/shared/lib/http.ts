import { env } from "../config/env";

/**
 * Opciones para la peticion HTTP
 */
type HttpOptions = {
  method?: "GET" | "POST" | "PUT" | "PATCH" | "DELETE";
  body?: unknown;
  headers?: Record<string, string>;
};

async function httpBase(url: string, apiKey: string, opts: HttpOptions = {}) {
  const headers: Record<string, string> = {
    Accept: 'application/vnd.api+json',
    'Content-Type': 'application/vnd.api+json',
    [env.API_KEY_HEADER]: apiKey,
    ...(opts.headers ?? {}),
  };
  const res = await fetch(url, {
    method: opts.method ?? 'GET',
    headers,
    body: opts.body ? JSON.stringify(opts.body) : undefined,
  });
  if (!res.ok) throw new Error(`HTTP ${res.status}`);
  return res.json();
}

export function httpProducts(path: string, opts: HttpOptions = {}) {
  return httpBase(`${env.PRODUCTS_URL}${path}`, env.PRODUCTS_API_KEY, opts);
}

export function httpInventory(path: string, opts: HttpOptions = {}) {
  return httpBase(`${env.INVENTORY_URL}${path}`, env.INVENTORY_API_KEY, opts);
}
