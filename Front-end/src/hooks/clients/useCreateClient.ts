import { clientRequest } from "@/interfaces/client/cliente-request-schema";
import { ValidationError } from "@/interfaces/validation-error";
import { createClient } from "@/services/client/create-clients";
import { useMutation } from "@tanstack/react-query";
import { AxiosError } from "axios";
import { toast } from "sonner";

export function useCreateClient() {
  const mutation = useMutation({
    mutationFn: (data: clientRequest) => createClient(data),
    onSuccess: () => {
      toast.success("Cliente registrado com sucesso!");
    },
    onError: (error: AxiosError<ValidationError[]>) => {
      console.log(error);
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
