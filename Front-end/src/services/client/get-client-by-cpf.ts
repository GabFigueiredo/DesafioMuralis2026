import {
  singleClientResponse,
  singleClientResponseSchema,
} from "@/interfaces/client/client-response-schema";
import api from "@/lib/axios";

export async function getClientByCPF(cpf: string): Promise<singleClientResponse> {
  const response = await api.get(`/clientes/cpf/${cpf}`);
  const parsed = singleClientResponseSchema.safeParse(response.data);

  if (!parsed.success) {
    throw new Error("Erro de validação nos clientes");
  }

  return parsed.data;
}
