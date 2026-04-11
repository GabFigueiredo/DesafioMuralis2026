import {
  clientResponse,
  ClientResponseSchema,
} from "@/interfaces/client/client-response-schema";
import api from "@/lib/axios";

export async function getClientsByName({nome, page, size}: {nome: string, page: number, size: number}): Promise<clientResponse> {
  const response = await api.get(`/clientes/nome?nome=${nome}&page=${page}&size=${size}`);
  const parsed = ClientResponseSchema.safeParse(response.data);

  if (!parsed.success) {
    throw new Error("Erro de validação nos clientes");
  }

  return parsed.data;
}
