import {
  clientResponse,
  ClientResponseSchema,
} from "@/interfaces/client/client-response-schema";
import api from "@/lib/axios";

export async function getClients(page: number, size: number): Promise<clientResponse> {
  const response = await api.get("/clientes?page=" + page + "&size=" + size);
  const parsed = ClientResponseSchema.safeParse(response.data);

  if (!parsed.success) {
    throw new Error("Erro de validação nos clientes");
  }

  return parsed.data;
}
