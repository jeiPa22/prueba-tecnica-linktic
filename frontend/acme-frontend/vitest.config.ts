import { defineConfig } from 'vitest/config'

// Provide a small import.meta.env shim for tests so modules that read env at import-time
// receive deterministic values.
export default defineConfig({
  test: {
    environment: 'jsdom',
    setupFiles: ['./src/setupTests.ts'],
    coverage: {
      provider: 'istanbul',
      reporter: ['text', 'html', 'json'],
    },
  },
  define: {
    'import.meta.env': {
      VITE_PRODUCTS_URL: 'https://products.test',
      VITE_PRODUCTS_API_KEY: 'prod-key',
      VITE_INVENTORY_URL: 'https://inventory.test',
      VITE_INVENTORY_API_KEY: 'inv-key',
      VITE_API_KEY_HEADER: 'x-api-key',
    },
  },
})
