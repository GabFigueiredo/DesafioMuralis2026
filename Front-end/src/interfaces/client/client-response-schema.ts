import { z } from "zod";

export const ClientResponseSchema = z.object({
  content: z.array(z.object({
    id: z.number(),
    nome: z.string(),
    cpf: z.string(),
    dataNascimento: z.string(),
    endereco: z.string(),
  })),
  page: z.number(),
  size: z.number(),
  totalElements: z.number(),
  totalPages: z.number(),
});

export const singleClientResponseSchema = z.object({
  id: z.number(),
  nome: z.string(),
  cpf: z.string(),
  dataNascimento: z.string(),
  endereco: z.string(),
});


export type clientResponse = z.infer<typeof ClientResponseSchema>;
export type singleClientResponse = z.infer<typeof singleClientResponseSchema>;

