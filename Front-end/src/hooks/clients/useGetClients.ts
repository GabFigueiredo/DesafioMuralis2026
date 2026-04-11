import { getClients } from "@/services/client/get-clients";
import { useQuery } from "@tanstack/react-query";

interface PaginationParams {
  page: number;
  size: number;
}

export function useClientsQuery({ page, size }: PaginationParams) {
  return useQuery({
    queryKey: ["clientes", page],
    queryFn: () => getClients(page, size),
    staleTime: 1000 * 60 * 5,
    refetchOnMount: false,
    refetchOnWindowFocus: false,
  });
}
