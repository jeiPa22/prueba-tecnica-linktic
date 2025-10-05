import { Container, Grid, Alert, CircularProgress } from "@mui/material";
import { useProducts } from "../entities/product/api/products";
import { ProductCard } from "../entities/product/ui/ProductCard";

/**
 * Página para mostrar productos.
 * @returns Página de productos.
 */
export default function ProductsPage() {
  /**
   * Uso del hook para obtener productos
   */
  const { data, isLoading, isError } = useProducts({ page: 1, size: 12 });

  /**
   * Renderizado del componente
   */
  if (isLoading) return <CircularProgress />;
  /**
   * Manejo de error en la carga de productos
   */
  if (isError)
    return <Alert severity="error">No fue posible cargar productos</Alert>;

  /**
   * Lista de productos
   */
  const items = Array.isArray(data?.data) ? data!.data : [];

  /**
   * Renderizado del componente
   */
  return (
    <Container sx={{ py: 3 }}>
      <Grid container spacing={{ xs: 2, md: 3 }}>
        {items.map((it) => (
          <Grid key={it.id} size={{ xs: 12, sm: 6, md: 4, lg: 3 }}>
            <ProductCard item={it} />
          </Grid>
        ))}
      </Grid>
    </Container>
  );
}
