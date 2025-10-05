// src/pages/ProductsPage.tsx
import { useMemo, useState } from "react";
import { Container, Alert, CircularProgress } from "@mui/material";
import type { GridPaginationModel, GridRowParams } from "@mui/x-data-grid";
import { useProducts } from "../entities/product/api/products";
import ProductsTable from "../entities/product/ui/ProductsTable";
import type { ProductResource } from "../entities/product/model/types";
import { ProductDetailDrawer } from "../entities/product/ui/ProductDetailDrawer";

type JsonApiDocMany<T> = { data: T[]; links?: any; meta?: any };

export default function ProductsPage() {
  const [paginationModel, setPaginationModel] = useState<GridPaginationModel>({
    page: 0,
    pageSize: 12,
  });
  const [selected, setSelected] = useState<string | null>(null);

  const apiPage = paginationModel.page + 1;
  const apiSize = paginationModel.pageSize;

  const { data, isLoading, isError, isFetching } = useProducts({
    page: apiPage,
    size: apiSize,
  });

  const rows = useMemo(() => {
    const list =
      (data as JsonApiDocMany<ProductResource> | undefined)?.data ?? [];
    return list.map((r) => ({
      id: r.id,
      name: r.attributes.name,
      price: r.attributes.price,
      currency: r.attributes.currency,
      status: r.attributes.status,
    }));
  }, [data]);

  const rowCount =
    (data as JsonApiDocMany<ProductResource> | undefined)?.meta?.total ??
    rows.length;

  if (isLoading) return <CircularProgress />;
  if (isError)
    return <Alert severity="error">No fue posible cargar productos</Alert>;

  return (
    <Container maxWidth="lg" sx={{ py: 3 }}>
      <h1>Listado de Productos</h1>
      <ProductsTable
        rows={rows}
        rowCount={rowCount}
        loading={isLoading || isFetching}
        paginationModel={paginationModel}
        onPaginationModelChange={setPaginationModel}
        onRowClick={(p: GridRowParams) => setSelected(String(p.id))}
      />
      <ProductDetailDrawer
        open={!!selected}
        productId={selected}
        onClose={() => setSelected(null)}
      />
    </Container>
  );
}
