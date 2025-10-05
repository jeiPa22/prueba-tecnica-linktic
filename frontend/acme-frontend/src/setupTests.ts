// Minimal test setup: provide a fallback global.fetch and reset helper.
// Avoid importing matcher libraries that expect a test runner global 'expect'.
if (!globalThis.fetch) {
  // eslint-disable-next-line @typescript-eslint/ban-ts-comment
  // @ts-ignore
  globalThis.fetch = async () => ({ ok: true, json: async () => ({}) })
}

// Provide a lightweight reset between tests. Vitest exposes afterEach globally
// so we only attach the hook if it's available in the runtime.
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
if (typeof afterEach !== 'undefined') {
  // eslint-disable-next-line @typescript-eslint/ban-ts-comment
  // @ts-ignore
  afterEach(() => {
    // @ts-ignore
    if (globalThis.fetch?.mockReset) globalThis.fetch.mockReset()
  })
}
