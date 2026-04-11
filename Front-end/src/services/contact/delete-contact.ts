import api from "@/lib/axios";

export async function deleteContact(clientId: string, id: string) {
  const response = api.delete(`/clientes/${clientId}/contatos/${id}`);

  return response;
}
