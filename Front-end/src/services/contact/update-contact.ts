import { contactRequest } from "@/interfaces/contact/contato-request-schema";
import api from "@/lib/axios";

interface updateContactProps {
  id: string;
  dadosAtualizados: contactRequest;
}

export function updateContact({ id, dadosAtualizados }: updateContactProps) {
  const response = api.put(`clientes/${dadosAtualizados.clienteId}/contatos/${id}`, dadosAtualizados);

  return response;
}
