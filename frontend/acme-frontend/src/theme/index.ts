import { createTheme } from "@mui/material/styles";

/**
 * Tema personalizado para la aplicacion
 */
export const theme = createTheme({
  palette: {
    mode: "light",
    primary: { main: "#1976d2" },
    secondary: { main: "#9c27b0" },
  },
  components: {
    MuiButton: { defaultProps: { variant: "contained" } },
  },
});
