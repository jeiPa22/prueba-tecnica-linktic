import { Card, CardContent, Typography } from "@mui/material";
import type { ProductResource } from "../model/types";

/**
 * Componente para mostrar un producto
 * @param param0 Props del componente
 * @returns Componente de tarjeta de producto
 */
export function ProductCard({ item }: { item: ProductResource }) {
  /**
   * Atributos del producto
   */
  const a = item.attributes;

  /**
   * Renderizado del componente
   */
  return (
    <Card sx={{ height: "100%", display: "flex", flexDirection: "column" }}>
      <CardContent sx={{ flexGrow: 1 }}>
        <Typography variant="subtitle1">{a.name}</Typography>
        <Typography variant="body2">
          {a.currency} {a.price.toLocaleString()}
        </Typography>
        <Typography variant="caption">Estado: {a.status}</Typography>
      </CardContent>
    </Card>
  );
}
