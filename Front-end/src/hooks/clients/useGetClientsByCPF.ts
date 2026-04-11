import { getClientByCPF } from "@/services/client/get-client-by-cpf";
import { useQuery } from "@tanstack/react-query";

export function useGetClientsByCPF(cpf: string) {
  return useQuery({
    queryKey: [`clientes`, cpf],
    queryFn: () => getClientByCPF(cpf),
    enabled: cpf.length > 0,
  });
}
