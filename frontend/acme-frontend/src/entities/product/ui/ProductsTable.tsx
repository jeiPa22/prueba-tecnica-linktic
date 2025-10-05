// src/entities/product/ui/ProductsTable.tsx
import { Box, Paper } from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";
import type {
  GridColDef,
  GridPaginationModel,
  GridRowParams,
} from "@mui/x-data-grid";
import Chip from "@mui/material/Chip";

type ProductRow = {
  id: string;
  name: string;
  price: number;
  currency: string;
  status: string;
};

const columns: GridColDef<ProductRow>[] = [
  { field: "id", headerName: "ID", width: 220, minWidth: 160 },
  { field: "name", headerName: "Nombre", flex: 1, minWidth: 180 },
  {
    field: "price",
    headerName: "Precio",
    type: "number",
    width: 140,
    minWidth: 110,
  },
  { field: "currency", headerName: "Moneda", width: 100, minWidth: 90 },
  {
    field: "status",
    headerName: "Estado",
    width: 140,
    renderCell: (params) => {
      const v = String(params.value ?? "");
      const ok = v.toUpperCase() === "ACTIVE";
      return (
        <Chip
          size="small"
          label={ok ? "Activo" : "Inactivo"}
          color={ok ? "success" : "error"}
        />
      );
    },
  },
];

export default function ProductsTable(props: {
  rows: ProductRow[];
  rowCount: number;
  loading: boolean;
  paginationModel: GridPaginationModel;
  onPaginationModelChange: (m: GridPaginationModel) => void;
  onRowClick?: (p: GridRowParams) => void;
}) {
  const {
    rows,
    rowCount,
    loading,
    paginationModel,
    onPaginationModelChange,
    onRowClick,
  } = props;

  return (
    <Box sx={{ width: "100%" }}>
      <Paper
        sx={{
          width: "100%",
          height: { xs: "60vh", md: "70vh" },
          p: { xs: 1, sm: 2 },
        }}
      >
        <DataGrid
          rows={rows}
          rowCount={rowCount}
          columns={columns}
          loading={loading}
          pagination
          paginationMode="server"
          paginationModel={paginationModel}
          onPaginationModelChange={onPaginationModelChange}
          pageSizeOptions={[6, 12, 24, 48]}
          disableRowSelectionOnClick
          onRowClick={onRowClick}
          sx={{
            border: 0,
            "& .MuiDataGrid-cell": {
              py: { xs: 0.5, sm: 1 },
              fontSize: { xs: "0.8rem", sm: "0.9rem" },
            },
            "& .MuiDataGrid-columnHeaders": {
              fontSize: { xs: "0.8rem", sm: "0.9rem" },
            },
          }}
        />
      </Paper>
    </Box>
  );
}
