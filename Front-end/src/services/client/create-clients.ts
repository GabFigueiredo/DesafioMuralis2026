import {
  clientRequest,
  singleClientRequestSchema,
} from "@/interfaces/client/cliente-request-schema";
import api from "@/lib/axios";

export async function createClient(
  data: clientRequest
): Promise<clientRequest> {
  const parsed = singleClientRequestSchema.safeParse(data);

  if (!parsed.success) {
    throw new Error("Dados inválidos para criação de cliente");
  }

  const response = await api.post("/clientes", parsed.data);

  return response.data;
}
