import type { PropsWithChildren } from "react";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";

/**
 * Props del proveedor
 */
const qc = new QueryClient();

/**
 * Provider para el cliente de consultas
 * @param param0 Props del proveedor
 * @returns Componente QueryClientProvider
 */
export function QueryProvider({ children }: PropsWithChildren) {
  /**
   * Componente QueryClientProvider
   */
  return (
    <QueryClientProvider client={qc}>
      {children}
      <ReactQueryDevtools initialIsOpen={false} />
    </QueryClientProvider>
  );
}
