"use client";

import { useQueryClient } from "@tanstack/react-query";

export function useRefreshData() {
  const queryClient = useQueryClient();

  function handleRefresh(queryKey: string) {
    queryClient.refetchQueries({
      queryKey: [queryKey],
      type: "active",
    });
  }

  return { handleRefresh };
}
