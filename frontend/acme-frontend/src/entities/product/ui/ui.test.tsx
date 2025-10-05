import { describe, it, expect, vi } from 'vitest'
import { render } from '@testing-library/react'

// Mock heavy MUI internals to keep tests fast and avoid CSS imports
vi.mock('@mui/x-data-grid', () => ({
  DataGrid: (props: any) => {
    // simple placeholder that renders children
    return null
  },
}))

vi.mock('@mui/material', async () => {
  const actual = await vi.importActual('@mui/material')
  return { ...actual, DataGrid: (p: any) => null }
})

import ProductsTable from './ProductsTable'
import { ProductDetailDrawer } from './ProductDetailDrawer'
import { QueryProvider } from '../../../app/providers/QueryClient'

describe('UI components smoke tests', () => {
  it('renders ProductsTable with empty rows', () => {
    const { container } = render(
      <ProductsTable
        rows={[]}
        rowCount={0}
        loading={false}
        paginationModel={{ page: 0, pageSize: 6 }}
        onPaginationModelChange={() => {}}
      />
    )
    expect(container).toBeTruthy()
  })

  it('renders ProductDetailDrawer closed', () => {
    const { container } = render(
      <QueryProvider>
        <ProductDetailDrawer open={false} productId={null} onClose={() => {}} />
      </QueryProvider>
    )
    expect(container).toBeTruthy()
  })
})
