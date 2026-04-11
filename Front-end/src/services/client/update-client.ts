import { clientRequest } from "@/interfaces/client/cliente-request-schema";
import api from "@/lib/axios";

interface updateClientProps {
  id: number;
  dadosAtualizados: clientRequest;
}

export function updateClient({ id, dadosAtualizados }: updateClientProps) {
  const response = api.put(`/clientes/${id}`, dadosAtualizados);

  return response;
}
