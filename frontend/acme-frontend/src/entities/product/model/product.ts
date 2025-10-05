// Columnas basadas en JSON:API de products

export type ProductRow = {
  id: string;
  name: string;
  price: number;
  currency: string;
  status: "ACTIVE" | "INACTIVE";
};

export type JsonApiProduct = {
  id: string;
  type: string;
  attributes: {
    name: string;
    price: number;
    currency: string;
    status: "ACTIVE" | "INACTIVE";
    createdAt: string;
    updatedAt: string;
  };
};

export type ProductsResponse = {
  data: JsonApiProduct[];
  links?: { next?: string; prev?: string; self?: string };
  meta?: { total?: number };
};
