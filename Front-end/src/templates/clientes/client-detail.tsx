"use client"

import React, { useEffect, useState } from "react";
import { useGetContactsByClientIdQuery } from "@/hooks/contacts/useGetContactsByClientId";
import { useGetClientById } from "@/hooks/clients/useGetClientsById";
import { useDeleteClient } from "@/hooks/clients/useDeleteClient";
import { ClientForm } from "@/components/client/client-form";
import { DataTable } from "@/components/table/data-table";
import { contatoColumns } from "@/components/table/contato/columns";
import { Dialog, DialogContent, DialogTrigger } from "@/components/ui/dialog";
import { Skeleton } from "@/components/ui/skeleton";
import { Button } from "@/components/ui/button";
import { toast } from "sonner";
import { Pencil, Trash2, User, CreditCard, Calendar, MapPin, Plus } from "lucide-react";
import { Separator } from "@/components/ui/separator";
import { Badge } from "@/components/ui/badge";
import { ContactForm } from "@/components/contact/contact-form";

export default function ClientDetails({ params }: { params: Promise<{ id: string }> }) {
  const [page, setPage] = useState(0);
  const { id } = React.use(params);

  const {
    data: contatos,
    isSuccess,
    isFetchedAfterMount,
    error,
    status,
  } = useGetContactsByClientIdQuery(Number(id), page);

  const { data: cliente, status: clienteStatus } = useGetClientById(id);
  const { mutate: deleteClient } = useDeleteClient();

  useEffect(() => {
    if (isSuccess && isFetchedAfterMount) {
      toast.success("Contatos do cliente buscados com sucesso!");
    }
  }, [isSuccess, isFetchedAfterMount]);

  function handleDeleteClient() {
    if (confirm("Tem certeza que deseja excluir este cliente?")) {
      deleteClient(Number(id));
    }
  }

  if (clienteStatus === "pending") {
    return (
      <main className="w-full max-w-4xl mx-auto pt-8 p-10 flex flex-col gap-6">
        <Skeleton className="h-10 w-48 rounded-md bg-zinc-200" />
        <Skeleton className="h-6 w-32 rounded-md bg-zinc-200" />
        <Skeleton className="h-[200px] w-full rounded-md bg-zinc-200" />
      </main>
    );
  }

  if (!cliente) return null;

  return (
    <main className="w-full max-w-4xl mx-auto pt-8 p-10 flex flex-col gap-6">

      {/* Header */}
      <div className="flex items-start justify-between">
        <div className="flex flex-col gap-1">
          <h1 className="text-2xl font-semibold tracking-tight">{cliente.nome}</h1>
          <Badge variant="secondary" className="w-fit">Cliente #{cliente.id}</Badge>
        </div>

        <div className="flex items-center gap-2">
          <Dialog>
            <DialogTrigger asChild>
              <Button variant="outline" size="sm">
                <Pencil className="w-4 h-4 mr-2" />
                Editar
              </Button>
            </DialogTrigger>
            <DialogContent className="p-0">
              <ClientForm client={cliente} />
            </DialogContent>
          </Dialog>

          <Button variant="destructive" size="sm" onClick={handleDeleteClient}>
            <Trash2 className="w-4 h-4 mr-2" />
            Excluir
          </Button>
        </div>
      </div>

      <Separator />

      {/* Client info */}
      <div className="grid grid-cols-2 gap-4 sm:grid-cols-4">
        <div className="flex flex-col gap-1">
          <span className="text-xs text-muted-foreground flex items-center gap-1">
            <User className="w-3 h-3" /> Nome
          </span>
          <span className="text-sm font-medium">{cliente.nome}</span>
        </div>
        <div className="flex flex-col gap-1">
          <span className="text-xs text-muted-foreground flex items-center gap-1">
            <CreditCard className="w-3 h-3" /> CPF
          </span>
          <span className="text-sm font-medium">{cliente.cpf}</span>
        </div>
        <div className="flex flex-col gap-1">
          <span className="text-xs text-muted-foreground flex items-center gap-1">
            <Calendar className="w-3 h-3" /> Nascimento
          </span>
          <span className="text-sm font-medium">{cliente.dataNascimento}</span>
        </div>
        <div className="flex flex-col gap-1">
          <span className="text-xs text-muted-foreground flex items-center gap-1">
            <MapPin className="w-3 h-3" /> Endereço
          </span>
          <span className="text-sm font-medium">{cliente.endereco}</span>
        </div>
      </div>

      <Separator />

      {/* Contacts table */}
      <div className="flex flex-col gap-3">
        <h2 className="text-base font-semibold">Contatos</h2>
        <Dialog>
          <DialogTrigger asChild>
            <Button variant="outline" size="sm">
              <Plus className="w-4 h-4 mr-2" />
              Novo Contato
            </Button>
          </DialogTrigger>
          <DialogContent className="p-0">
            <ContactForm defaultClienteId={Number(id)} />
          </DialogContent>
        </Dialog>
        {status === "pending" ? (
          <div className="flex flex-col gap-2">
            {Array.from({ length: 4 }).map((_, i) => (
              <Skeleton key={i} className="h-10 w-full rounded-sm bg-zinc-200" />
            ))}
          </div>
        ) : status === "error" ? (
          <div className="w-full flex justify-center items-center flex-col gap-1 py-8 text-destructive">
            <h2 className="font-semibold">Ocorreu um erro :/</h2>
            <p className="text-sm">{error.message}</p>
          </div>
        ) : (
            <DataTable
              columns={contatoColumns}
              data={contatos.content}
              paginationProps={{
                page,
                totalPages: contatos.totalPages,
                onPreviousPage: () => setPage((p) => p - 1),
                onNextPage: () => setPage((p) => p + 1),
              }}
            />
        )}
      </div>

    </main>
  );
}