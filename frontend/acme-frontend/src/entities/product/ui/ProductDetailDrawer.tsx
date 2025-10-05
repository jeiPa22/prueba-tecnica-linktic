import { Drawer, Box, Typography, Stack, Button, Alert } from "@mui/material";
import { useInventoryQuantity, usePurchase } from "../api/inventory";

export function ProductDetailDrawer({
  open,
  productId,
  onClose,
}: {
  open: boolean;
  productId: string | null;
  onClose: () => void;
}) {
  const { data, isLoading, isError } = useInventoryQuantity(productId ?? "");
  const qty = data?.data?.attributes?.quantity ?? 0;

  const purchase = usePurchase();

  return (
    <Drawer anchor="right" open={open} onClose={onClose}>
      <Box sx={{ width: 360, p: 2 }}>
        <Typography variant="h6">Detalle de producto</Typography>
        {/* istanbul ignore next */}
        {isLoading && <Typography>Cargando inventario...</Typography>}
        {/* istanbul ignore next */}
        {isError && (
          <Alert severity="error">No se pudo obtener inventario</Alert>
        )}
        {!isLoading && !isError && (
          <Stack spacing={2}>
            <Typography>Cantidad disponible: {qty}</Typography>
            <Stack direction="row" spacing={1}>
              <Button
                disabled={purchase.isPending || !productId}
                onClick={() =>
                  productId && purchase.mutate({ productId, quantity: 1 })
                }
              >
                Comprar 1
              </Button>
              {/* istanbul ignore next */}
              {purchase.isError && (
                <Alert severity="error">Error al comprar</Alert>
              )}
              {/* istanbul ignore next */}
              {purchase.isSuccess && (
                <Alert severity="success">Compra exitosa</Alert>
              )}
            </Stack>
          </Stack>
        )}
      </Box>
    </Drawer>
  );
}
