import {
  singleContactResponse,
  singleContactResponseSchema,
} from "@/interfaces/contact/contact-response-schema";
import api from "@/lib/axios";

export async function getContactByItsId(id: string): Promise<singleContactResponse> {
  const response = await api.get(`/contatos/${id}`);
  const parsed = singleContactResponseSchema.safeParse(response.data);

  if (!parsed.success) {
    throw new Error("Erro de validação nos contatos");
  }

  return parsed.data;
}
