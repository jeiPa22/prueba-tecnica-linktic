import { env } from "../config/env";

/**
 * Opciones para la peticion HTTP
 */
type HttpOptions = {
  method?: "GET" | "POST" | "PUT" | "PATCH" | "DELETE";
  body?: unknown;
  headers?: Record<string, string>;
};

/**
 * peticion HTTP con fetch
 * @param url  URL de la peticion
 * @param opts  Opciones de la peticion
 * @returns  Respuesta de la peticion
 */
export async function http(url: string, opts: HttpOptions = {}) {
  const headers: Record<string, string> = {
    Accept: "application/vnd.api+json",
    "Content-Type": "application/vnd.api+json",
    [env.API_KEY_HEADER]: env.API_KEY,
    ...(opts.headers ?? {}),
  };
  const res = await fetch(url, {
    method: opts.method ?? "GET",
    headers,
    body: opts.body ? JSON.stringify(opts.body) : undefined,
  });
  if (!res.ok) throw new Error(`HTTP ${res.status}`);
  return res.json();
}
