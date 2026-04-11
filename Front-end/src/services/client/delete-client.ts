import api from "@/lib/axios";

export async function deleteClient(id: number) {
  const response = api.delete(`/clientes/${id}`);

  return response;
}
