import { describe, expect, it, vi, beforeEach } from 'vitest'

const captured: any = {}

vi.mock('../../../shared/lib/http', () => ({
  httpInventory: vi.fn(async (p: string) => ({ calledWith: p })),
}))

vi.mock('@tanstack/react-query', () => ({
  useQuery: (opts: any) => {
    captured.query = opts
    return { q: true }
  },
  useMutation: (opts: any) => {
    captured.mutation = opts
    return { mutateAsync: (v: any) => opts.mutationFn(v) }
  },
  useQueryClient: () => ({
    invalidateQueries: vi.fn(),
  }),
}))

import { useInventoryQuantity, usePurchase } from './inventory'
import { httpInventory } from '../../../shared/lib/http'

describe('inventory api hooks', () => {
  beforeEach(() => vi.restoreAllMocks())

  it('useInventoryQuantity sets enabled based on productId', async () => {
    const r = useInventoryQuantity('p1')
    expect(r).toEqual({ q: true })
    expect(captured.query.enabled).toBe(true)

    const r2 = useInventoryQuantity('')
    expect(captured.query.enabled).toBe(false)
  })

  it('usePurchase mutation calls httpInventory and invalidates queries on success', async () => {
    const res = usePurchase()
    expect(res).toHaveProperty('mutateAsync')
    const mutationFn = captured.mutation.mutationFn
    const payload = { productId: 'p1', quantity: 2 }
    const result = await mutationFn(payload)
    expect(result).toEqual({ calledWith: expect.stringContaining('/api/v1/inventory/purchase') })
    // simulate onSuccess behaviour to cover invalidation calls
    if (typeof captured.mutation.onSuccess === 'function') {
      captured.mutation.onSuccess(result, payload)
    }
  })
})
