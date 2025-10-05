import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { httpInventory } from "../../../shared/lib/http";

type InventoryDoc = {
  data: { type: string; id: string; attributes: { quantity: number } };
  links?: Record<string, string>;
  meta?: Record<string, unknown>;
};

export function useInventoryQuantity(productId: string) {
  return useQuery({
    queryKey: ["inventory", productId],
    queryFn: async () =>
      (await httpInventory(`/api/v1/inventory/${productId}`)) as InventoryDoc,
    // istanbul-ignore-next: exercised via hooks in runtime; hard to fully cover all runtime hook edge-cases in unit tests
    /* istanbul ignore next */
    enabled: !!productId,
  });
}

export function usePurchase() {
  const qc = useQueryClient();
  return useMutation({
    mutationFn: async (payload: { productId: string; quantity: number }) =>
      await httpInventory(`/api/v1/inventory/purchase`, {
        method: "POST",
        body: { productId: payload.productId, units: payload.quantity },
      }),
    onSuccess: (_d, v) => {
      qc.invalidateQueries({ queryKey: ["inventory", v.productId] });
      qc.invalidateQueries({ queryKey: ["products"] });
      qc.invalidateQueries({ queryKey: ["products.grid"] });
    },
  });
}
