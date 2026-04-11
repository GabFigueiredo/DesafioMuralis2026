import { clientRequest } from "@/interfaces/client/cliente-request-schema";
import { ValidationError } from "@/interfaces/validation-error";
import { updateClient } from "@/services/client/update-client";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { AxiosError } from "axios";
import { toast } from "sonner";

type UpdateClientParams = {
  id: number;
  dadosAtualizados: clientRequest;
};

export function useUpdateClient() {
  const queryClient = useQueryClient();

  const mutation = useMutation({
    mutationFn: ({ id, dadosAtualizados }: UpdateClientParams) =>
      updateClient({ id, dadosAtualizados }),
    onSuccess: () => {
      toast.success("Cliente alterado com sucesso!");
      queryClient.invalidateQueries({ queryKey: ["clientes"] });
    },
    onError: (error: AxiosError<ValidationError[]>) => {
      if (error?.response?.data) {
        const errors = error.response.data;
        errors.forEach((err) => {
          toast.error(`Erro: ${err.message}`);
        });
      } else {
        toast.error("Erro ao criar cliente. Tente novamente.");
        console.error("Erro ao criar cliente:", error);
      }
    },
  });

  return {
    mutate: mutation.mutate,
    status: mutation.status,
  };
}
