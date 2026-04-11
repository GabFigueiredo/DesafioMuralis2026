import { contactRequest } from "@/interfaces/contact/contato-request-schema";
import { ValidationError } from "@/interfaces/validation-error";
import { createContact } from "@/services/contact/create-contact";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { AxiosError } from "axios";
import { toast } from "sonner";

export function useCreateContact() {
  const queryClient = useQueryClient();

  const mutation = useMutation({
    mutationFn: (data: contactRequest) => createContact(data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["contatos"] });
      toast.success("Contato registrado com sucesso!");
    },
    onError: (error: AxiosError<ValidationError[]>) => {
      if (error?.response?.data) {
        const errors = error.response.data;
        errors.forEach((err) => {
          toast.error(`Erro: ${err.message}`);
        });
      } else {
        toast.error("Erro ao criar contato. Tente novamente.");
      }
    },
  });

  return {
    mutate: mutation.mutate,
    status: mutation.status,
  };
}
