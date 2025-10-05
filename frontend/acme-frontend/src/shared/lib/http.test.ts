import { beforeEach, describe, expect, it, vi } from 'vitest'

// Import after mocking import.meta.env is provided by vitest config
import { httpProducts, httpInventory } from './http'
import { env } from '../config/env'

describe('http utilities', () => {
  beforeEach(() => {
    vi.restoreAllMocks()
  })

  it('calls fetch with correct headers and url for products', async () => {
    const fakeResp = { data: 'ok' }
    const mockFetch = vi.fn().mockResolvedValue({ ok: true, json: async () => fakeResp })
    // @ts-ignore
    global.fetch = mockFetch

    const res = await httpProducts('/path')
    expect(res).toEqual(fakeResp)

    expect(mockFetch).toHaveBeenCalled()
    const [url, opts] = mockFetch.mock.calls[0]
    expect(String(url)).toContain(env.PRODUCTS_URL)
    expect(opts.headers[env.API_KEY_HEADER]).toBe(env.PRODUCTS_API_KEY)
  })

  it('throws on non-ok responses', async () => {
    const mockFetch = vi.fn().mockResolvedValue({ ok: false, status: 500 })
    // @ts-ignore
    global.fetch = mockFetch

    await expect(httpInventory('/x')).rejects.toThrow('HTTP 500')
  })
})
