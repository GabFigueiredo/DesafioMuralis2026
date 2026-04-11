import { contactRequest } from "@/interfaces/contact/contato-request-schema";
import { ValidationError } from "@/interfaces/validation-error";
import { updateContact } from "@/services/contact/update-contact";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { AxiosError } from "axios";
import { toast } from "sonner";

type UpdateContactParams = {
  id: string;
  dadosAtualizados: contactRequest;
};

export function useUpdateContact() {
  const queryClient = useQueryClient();

  const mutation = useMutation({
    mutationFn: ({ id, dadosAtualizados }: UpdateContactParams) =>
      updateContact({ id, dadosAtualizados }),
    onSuccess: () => {
      toast.success("Contato alterado com sucesso!");
      queryClient.invalidateQueries({ queryKey: ["contatos"] });
    },
    onError: (error: AxiosError<ValidationError[]>) => {
      if (error?.response?.data) {
        const errors = error.response.data;
        errors.forEach((err) => {
          toast.error(`Erro: ${err.message}`);
        });
      } else {
        toast.error("Erro ao criar contato. Tente novamente.");
        console.error("Erro ao criar contato:", error);
      }
    },
  });

  return {
    mutate: mutation.mutate,
    status: mutation.status,
  };
}
