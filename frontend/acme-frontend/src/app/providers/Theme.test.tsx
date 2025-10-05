import { describe, it, expect } from 'vitest'
import { render } from '@testing-library/react'
import { AppThemeProvider } from './Theme'

describe('AppThemeProvider', () => {
  it('renders children', () => {
    const { getByText } = render(
      <AppThemeProvider>
        <div>Hi</div>
      </AppThemeProvider>
    )
    expect(getByText(/Hi/)).toBeTruthy()
  })
})
