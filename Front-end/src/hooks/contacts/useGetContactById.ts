import { getClientsById } from "@/services/client/get-clients-by-id";
import { getContactByItsId } from "@/services/contact/get-contact-by-its-id";
import { useQuery } from "@tanstack/react-query";

export function useGetContactById(id: string) {
  return useQuery({
    queryKey: ["contatos/" + id],
    queryFn: () => getContactByItsId(id),
    staleTime: 1000 * 60 * 5,
    refetchOnMount: false,
    refetchOnWindowFocus: false,
  });
}
