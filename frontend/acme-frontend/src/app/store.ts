import { configureStore, createSlice } from "@reduxjs/toolkit";

/**
 * ui estado global de la aplicacion
 */
const uiSlice = createSlice({
  name: "ui",
  initialState: {
    themeMode: "light" as "light" | "dark",
    snackbar: null as null | string,
  },
  reducers: {
    setThemeMode(state, action: { payload: "light" | "dark" }) {
      state.themeMode = action.payload;
    },
    showSnackbar(state, action: { payload: string }) {
      state.snackbar = action.payload;
    },
    hideSnackbar(state) {
      state.snackbar = null;
    },
  },
});
/**
 * Acciones del estado global de la aplicacion
 */
export const { setThemeMode, showSnackbar, hideSnackbar } = uiSlice.actions;

/**
 * Store de Redux
 */
export const store = configureStore({
  reducer: {
    ui: uiSlice.reducer,
  },
});

/**
 * Estado inicial del store
 */
export type RootState = ReturnType<typeof store.getState>;
/**
 * Tipo de dispatch del store
 */
export type AppDispatch = typeof store.dispatch;
