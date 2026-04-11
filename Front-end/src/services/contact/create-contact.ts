import {
  contactRequest,
  singleContactRequestSchema,
} from "@/interfaces/contact/contato-request-schema";
import api from "@/lib/axios";

export async function createContact(
  data: contactRequest
): Promise<contactRequest> {
  const parsed = singleContactRequestSchema.safeParse(data);

  if (!parsed.success) {
    throw new Error("Dados inválidos para criação de contacte");
  }

  const response = await api.post(`/clientes/${parsed.data.client_id}/contatos`, parsed.data);

  return response.data;
}
