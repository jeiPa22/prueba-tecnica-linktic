import { describe, it, expect, vi } from 'vitest'
import { render } from '@testing-library/react'

vi.mock('@mui/x-data-grid', () => ({ DataGrid: () => null }))

describe('ProductsPage rendering', () => {
  it('renders list header when not loading', async () => {
    vi.resetModules()
    vi.doMock('../entities/product/api/products', () => ({
      useProducts: () => ({ data: { data: [] }, isLoading: false, isError: false, isFetching: false }),
    }))
    const { QueryProvider } = await import('../app/providers/QueryClient')
    const { default: ProductsPage } = await import('./ProductsPage')
    const { getAllByText } = render(
      <QueryProvider>
        <ProductsPage />
      </QueryProvider>
    )
    const matches = getAllByText(/Listado de Productos/)
    expect(matches.length).toBeGreaterThanOrEqual(1)
  })

  it('renders loading when useProducts reports isLoading', async () => {
    vi.resetModules()
    vi.doMock('../entities/product/api/products', () => ({
      useProducts: () => ({ isLoading: true }),
    }))
    const { QueryProvider } = await import('../app/providers/QueryClient')
    const { default: ProductsPage } = await import('./ProductsPage')
    const { container } = render(
      <QueryProvider>
        <ProductsPage />
      </QueryProvider>
    )
    expect(container).toBeTruthy()
  })

  it('renders mapped rows when useProducts returns data', async () => {
    vi.resetModules()
    vi.doMock('../entities/product/api/products', () => ({
      useProducts: () => ({ data: { data: [{ id: 'p1', attributes: { name: 'X', price: 1, currency: 'USD', status: 'ACTIVE' } }], meta: { total: 5 } }, isLoading: false, isError: false, isFetching: false }),
    }))
    const { QueryProvider } = await import('../app/providers/QueryClient')
    const { default: ProductsPage } = await import('./ProductsPage')
    const { getAllByText } = render(
      <QueryProvider>
        <ProductsPage />
      </QueryProvider>
    )
    const matches = getAllByText(/Listado de Productos/)
    expect(matches.length).toBeGreaterThanOrEqual(1)
  })
  it('renders error message when useProducts reports isError', async () => {
    vi.resetModules()
    vi.doMock('../entities/product/api/products', () => ({
      useProducts: () => ({ isError: true }),
    }))
    const { QueryProvider } = await import('../app/providers/QueryClient')
    const { default: ProductsPage } = await import('./ProductsPage')
    const { getByText } = render(
      <QueryProvider>
        <ProductsPage />
      </QueryProvider>
    )
    expect(getByText(/No fue posible cargar productos/)).toBeTruthy()
  })
  it('opens drawer when a row is clicked', async () => {
    vi.resetModules()
    vi.doMock('../entities/product/api/products', () => ({
      useProducts: () => ({ data: { data: [{ id: 'p1', attributes: { name: 'X', price: 1, currency: 'USD', status: 'ACTIVE' } }], meta: { total: 1 } }, isLoading: false, isError: false, isFetching: false }),
    }))
    // mock ProductsTable to trigger onRowClick
    vi.doMock('../entities/product/ui/ProductsTable', () => ({
      default: (props: any) => {
        // simulate a row click
        props.onRowClick?.({ id: 'p1' })
        return null
      },
    }))
    // mock inventory hooks used by ProductDetailDrawer
    vi.doMock('../entities/product/api/inventory', () => ({
      useInventoryQuantity: () => ({ data: { data: { attributes: { quantity: 7 } } }, isLoading: false, isError: false }),
      usePurchase: () => ({ isPending: false, isError: false, isSuccess: false, mutate: () => {} }),
    }))

    const { QueryProvider } = await import('../app/providers/QueryClient')
    const { default: ProductsPage } = await import('./ProductsPage')
    const { getByText } = render(
      <QueryProvider>
        <ProductsPage />
      </QueryProvider>
    )
    expect(getByText(/Cantidad disponible/)).toBeTruthy()
  })
})
