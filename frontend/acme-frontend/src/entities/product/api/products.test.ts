import { describe, expect, it, vi, beforeEach } from 'vitest'

// Mock the httpProducts helper to observe queryFn invocation
vi.mock('../../../shared/lib/http', () => ({
  httpProducts: vi.fn(async (p: string) => ({ calledWith: p })),
}))

// Provide a fake react-query implementation to capture options
const captured: any = {}
vi.mock('@tanstack/react-query', () => ({
  useQuery: (opts: any) => {
    // store for assertions
    captured.args = opts
    return { mocked: true }
  },
  keepPreviousData: Symbol('keepPreviousData'),
}))

import { useProduct, useProducts } from './products'
import { httpProducts } from '../../../shared/lib/http'

describe('products api hooks', () => {
  beforeEach(() => {
    vi.restoreAllMocks()
  })

  it('useProduct config generates a query that calls httpProducts', async () => {
    // call exported function to trigger useQuery wrapper
    const result = useProduct('abc')
    expect(result).toEqual({ mocked: true })
    // captured.args.queryFn should be a function that when run calls httpProducts
    const fn = captured.args.queryFn
    const data = await fn()
    expect(data).toEqual({ calledWith: expect.stringContaining('/api/v1/products/abc') })
  })

  it('useProducts builds search params and uses placeholderData', async () => {
    const result = useProducts({ page: 2, size: 10 })
    expect(result).toEqual({ mocked: true })
    const fn = captured.args.queryFn
    const data = await fn()
    expect(data).toEqual({ calledWith: expect.stringContaining('/api/v1/products') })
    expect(captured.args.placeholderData).toBeDefined()
  })
})
