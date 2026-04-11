import {
  contactResponse,
  ContactResponseSchema,
} from "@/interfaces/contact/contact-response-schema";
import api from "@/lib/axios";

export async function getContactsByClientId(
  client_id: number
): Promise<contactResponse> {
  const response = await api.get(`/clientes/${client_id}/contatos`);
  const parsed = ContactResponseSchema.safeParse(response.data);
  
  if (!parsed.success) {
    throw new Error("Erro de validação nos contatos");
  }

  return parsed.data;
}
