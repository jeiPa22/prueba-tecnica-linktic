import { describe, it, expect, vi } from 'vitest'
import { render } from '@testing-library/react'

describe('ProductDetailDrawer states', () => {
  it('renders with inventory data', async () => {
    vi.resetModules()
    vi.doMock('../api/inventory', () => ({
      useInventoryQuantity: () => ({ data: { data: { attributes: { quantity: 5 } } }, isLoading: false, isError: false }),
      usePurchase: () => ({ isPending: false, isError: false, isSuccess: false, mutate: () => {} }),
    }))
    const { ProductDetailDrawer } = await import('./ProductDetailDrawer')
    const { QueryProvider } = await import('../../../app/providers/QueryClient')
    const { getAllByText } = render(
      <QueryProvider>
        <ProductDetailDrawer open={true} productId={'p1'} onClose={() => {}} />
      </QueryProvider>
    )
    const matches = getAllByText(/Cantidad disponible/)
    expect(matches.length).toBeGreaterThanOrEqual(1)
  })

  it('shows loading when inventory is loading', async () => {
    vi.resetModules()
    vi.doMock('../api/inventory', () => ({
      useInventoryQuantity: () => ({ isLoading: true }),
      usePurchase: () => ({ isPending: false, isError: false, isSuccess: false, mutate: () => {} }),
    }))
    const { ProductDetailDrawer } = await import('./ProductDetailDrawer')
    const { QueryProvider } = await import('../../../app/providers/QueryClient')
    const { getByText } = render(
      <QueryProvider>
        <ProductDetailDrawer open={true} productId={'p1'} onClose={() => {}} />
      </QueryProvider>
    )
    expect(getByText(/Cargando inventario/)).toBeTruthy()
  })

  it('shows purchase error and success messages', async () => {
    vi.resetModules()
    vi.doMock('../api/inventory', () => ({
      useInventoryQuantity: () => ({ data: { data: { attributes: { quantity: 3 } } }, isLoading: false, isError: false }),
      usePurchase: () => ({ isPending: false, isError: true, isSuccess: false, mutate: () => {} }),
    }))
    const { ProductDetailDrawer } = await import('./ProductDetailDrawer')
    const { QueryProvider } = await import('../../../app/providers/QueryClient')
    const { getByText } = render(
      <QueryProvider>
        <ProductDetailDrawer open={true} productId={'p1'} onClose={() => {}} />
      </QueryProvider>
    )
    expect(getByText(/Error al comprar/)).toBeTruthy()

    // now simulate success
    vi.resetModules()
    vi.doMock('../api/inventory', () => ({
      useInventoryQuantity: () => ({ data: { data: { attributes: { quantity: 3 } } }, isLoading: false, isError: false }),
      usePurchase: () => ({ isPending: false, isError: false, isSuccess: true, mutate: () => {} }),
    }))
    const { ProductDetailDrawer: P2 } = await import('./ProductDetailDrawer')
    const { getByText: getByText2 } = render(
      <QueryProvider>
        <P2 open={true} productId={'p1'} onClose={() => {}} />
      </QueryProvider>
    )
    expect(getByText2(/Compra exitosa/)).toBeTruthy()
  })
})

