import { deleteContact } from "@/services/contact/delete-contact";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { toast } from "sonner";

export function useDeleteContact() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: (id: string) => deleteContact(id),
    onSuccess: () => {
      toast.success("Contato excluído com sucesso!");
      queryClient.invalidateQueries({ queryKey: ["contatos"] });
    },
    onError: () => toast.error("Não foi possível excluir o contato"),
  });
}
