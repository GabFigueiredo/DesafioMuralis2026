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
    onError: (error: any) => {
      if (error?.response?.data) {
        console.log(error.response.status)
        if (error.response.status === 409) {
          toast.error("CPF já cadastrado. Por favor, utilize outro CPF.");
          return;
        }
        if (error.response.status === 400) {
          toast.error(error.response.data);
          return;
        }
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
