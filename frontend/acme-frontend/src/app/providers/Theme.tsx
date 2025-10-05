import type { PropsWithChildren } from "react";
import { CssBaseline, ThemeProvider } from "@mui/material";
import { theme } from "../../theme";

/**
 * Proveedor de tema para la aplicacion
 * @param param0 Props del proveedor
 * @returns Componente ThemeProvider
 */
export function AppThemeProvider({ children }: PropsWithChildren) {
  /**
   * Componente ThemeProvider
   */
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      {children}
    </ThemeProvider>
  );
}
