import { clientResponse } from "@/interfaces/client/client-response-schema";
import { useSearchParams } from "next/navigation";

export function useFiltrarClientes(clientes: clientResponse[] | undefined) {
  const searchParams = useSearchParams();

  const nomeParaFiltrar = searchParams.get("nome");
  if (nomeParaFiltrar) {
    return clientes?.filter((cliente) =>
      cliente.nome.includes(nomeParaFiltrar)
    );
  }

  const cpfParaFiltrar = searchParams.get("CPF");
  if (cpfParaFiltrar) {
    return clientes?.filter((cliente) => cliente.cpf.includes(cpfParaFiltrar));
  }

  return clientes;
}
