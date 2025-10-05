import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import "./index.css";
import App from "./App.tsx";
import { Provider } from "react-redux";
import { QueryProvider } from "./app/providers/QueryClient.tsx";
import { AppThemeProvider } from "./app/providers/Theme.tsx";
import { store } from "./app/store.ts";

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <Provider store={store}>
      <QueryProvider>
        <AppThemeProvider>
          <App />
        </AppThemeProvider>
      </QueryProvider>
    </Provider>
  </StrictMode>
);
