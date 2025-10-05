import { describe, it, expect, vi } from 'vitest'
import { render } from '@testing-library/react'

vi.mock('./pages/ProductsPage', () => ({ default: () => <div>MockPage</div> }))

import App from './App'

describe('App root', () => {
  it('renders App with mocked ProductsPage', () => {
    const { getByText } = render(<App />)
    expect(getByText(/MockPage/)).toBeTruthy()
  })
})
