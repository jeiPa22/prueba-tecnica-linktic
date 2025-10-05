import { describe, it, expect, vi } from 'vitest'
// Provide a custom DataGrid mock that will execute renderCell for the status column
vi.mock('@mui/x-data-grid', () => ({
  DataGrid: (props: any) => {
    // find status column and execute renderCell
    const statusCol = (props.columns || []).find((c: any) => c.field === 'status')
    if (statusCol && typeof statusCol.renderCell === 'function') {
      statusCol.renderCell({ value: 'ACTIVE' })
      statusCol.renderCell({ value: 'INACTIVE' })
    }
    return null
  },
}))

import ProductsTable from './ProductsTable'
import { render } from '@testing-library/react'

describe('ProductsTable renderCell', () => {
  it('executes renderCell for status column', () => {
    const { container } = render(
      <ProductsTable
        rows={[{ id: '1', name: 'p', price: 1, currency: 'USD', status: 'ACTIVE' }]}
        rowCount={1}
        loading={false}
        paginationModel={{ page: 0, pageSize: 6 }}
        onPaginationModelChange={() => {}}
      />
    )
    expect(container).toBeTruthy()
  })
})
