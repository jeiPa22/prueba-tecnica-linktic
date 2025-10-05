type InventoryDoc = {
  data: { type: string; id: string; attributes: { quantity: number } };
  links?: Record<string, string>;
  meta?: Record<string, unknown>;
};
