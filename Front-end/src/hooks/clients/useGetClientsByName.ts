import { getClientsByName } from "@/services/client/get-client-by-name";
import { useQuery } from "@tanstack/react-query";

export function useGetClientsByName({nome, page, size}: {nome: string, page: number, size: number}) {
  return useQuery({
    queryKey: [`/clientes/nome?nome=${nome}&page=${page}&size=${size}`],
    queryFn: () => getClientsByName({nome, page, size}),
    staleTime: 1000 * 60 * 5,
    refetchOnMount: false,
    refetchOnWindowFocus: false,
  });
}
