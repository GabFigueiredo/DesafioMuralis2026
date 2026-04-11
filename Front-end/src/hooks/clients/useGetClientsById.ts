import { getClientsById } from "@/services/client/get-clients-by-id";
import { useQuery } from "@tanstack/react-query";

export function useGetClientById(id: string) {
  return useQuery({
    queryKey: ["clientes", id],
    queryFn: () => getClientsById(id),
    staleTime: 1000 * 60 * 5,
    refetchOnMount: false,
    refetchOnWindowFocus: false,
  });
}
