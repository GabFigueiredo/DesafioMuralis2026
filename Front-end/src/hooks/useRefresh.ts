"use client";

import { useQueryClient } from "@tanstack/react-query";
import { toast } from "sonner";

export function useRefreshData() {
  const queryClient = useQueryClient();

  function handleRefresh(queryKey: string) {
    queryClient.refetchQueries({
      queryKey: [queryKey],
      type: "active",
    });
    toast("Carregando...");
  }

  return { handleRefresh };
}
