import { getContactsByClientId } from "@/services/contact/get-contacts-by-client-id";
import { useQuery } from "@tanstack/react-query";

export function useGetContactsByClientIdQuery(clientId: number, page: number) {
  return useQuery({
    queryKey: ["clientes", clientId, "contatos", page],
    queryFn: () => getContactsByClientId(clientId, page),
    staleTime: 1000 * 60 * 5,
    refetchOnMount: false,
    refetchOnWindowFocus: false,
  });
}
