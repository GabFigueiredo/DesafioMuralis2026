import { getContactsByClientId } from "@/services/contact/get-contacts-by-client-id";
import { useQuery } from "@tanstack/react-query";

export function useGetContactsByClientIdQuery(clientId: number) {
  return useQuery({
    queryKey: ["clientes/" + clientId + "/contatos"],
    queryFn: () => getContactsByClientId(clientId),
    staleTime: 1000 * 60 * 5,
    refetchOnMount: false,
    refetchOnWindowFocus: false,
  });
}
