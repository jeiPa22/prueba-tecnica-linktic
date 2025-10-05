import { Container, Grid, Alert, CircularProgress, Box } from "@mui/material";
import { useProducts } from "../entities/product/api/products";
import { ProductCard } from "../entities/product/ui/ProductCard";

/**
 * Página para mostrar productos.
 * @returns Página de productos.
 */
export default function ProductsPage() {
  const { data, isLoading, isError } = useProducts({ page: 1, size: 12 });

  if (isLoading) return <CircularProgress />;
  if (isError)
    return <Alert severity="error">No fue posible cargar productos</Alert>;

  const items = Array.isArray(data?.data) ? data!.data : [];

  return (
    <Container maxWidth="lg" sx={{ py: 3 }}>
      <Grid container rowSpacing={1} columnSpacing={{ xs: 1, sm: 2, md: 3 }}>
        {items.map((it) => (
          <Grid key={it.id} size={{ xs: 12, sm: 6, md: 8, lg: 8 }}>
            <ProductCard item={it} />
          </Grid>
        ))}
      </Grid>
    </Container>
  );
}
