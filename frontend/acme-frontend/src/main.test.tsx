import { describe, it, vi } from 'vitest'

describe('main entry', () => {
  it('boots application without throwing', async () => {
    // create root element expected by main.tsx
    const root = document.createElement('div')
    root.id = 'root'
    document.body.appendChild(root)
    // mock DataGrid to avoid importing CSS during bootstrap
  // vi is provided globally by Vitest
  vi.mock('@mui/x-data-grid', () => ({ DataGrid: () => null }))
    await import('./main')
  })
})
