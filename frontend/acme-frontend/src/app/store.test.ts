import { describe, it, expect } from 'vitest'
import { store, setThemeMode, showSnackbar, hideSnackbar } from './store'

describe('store reducers', () => {
  it('updates theme and snackbar state', () => {
    const prev = store.getState().ui.themeMode
    store.dispatch(setThemeMode('dark'))
    expect(store.getState().ui.themeMode).toBe('dark')
    store.dispatch(showSnackbar('hello'))
    expect(store.getState().ui.snackbar).toBe('hello')
    store.dispatch(hideSnackbar())
    expect(store.getState().ui.snackbar).toBe(null)
    // restore
    store.dispatch(setThemeMode(prev))
  })
})
