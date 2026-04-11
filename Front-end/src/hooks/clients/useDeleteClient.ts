import { deleteClient } from "@/services/client/delete-client";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { toast } from "sonner";

export function useDeleteClient() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (id: number) => deleteClient(id),
    onSuccess: () => {
      toast.success("Cliente excluído com sucesso!");
      queryClient.invalidateQueries({ queryKey: ["clientes"] });
    },
    onError: () => toast.error("Não foi possível excluir o cliente"),
  });
}
