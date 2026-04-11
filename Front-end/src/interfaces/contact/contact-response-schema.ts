import { z } from "zod";

export const ContactResponseSchema = z.object({
  content: z.array(z.object({
    id: z.number(),
    clienteId: z.number(),
    contatoValor: z.object({
      tipo: z.string(),
      value: z.string(),
    }),
    observacao: z.string(),
  })),
  page: z.number(),
  size: z.number(),
  totalElements: z.number(),
  totalPages: z.number(),
});

export const singleContactResponseSchema = z.object({
    id: z.number(),
    clienteId: z.number(),
    contatoValor: z.object({
      tipo: z.string(),
      value: z.string(),
    }),
    observacao: z.string(),
})

export type contactResponse = z.infer<typeof ContactResponseSchema>;
export type singleContactResponse = z.infer<typeof singleContactResponseSchema>;