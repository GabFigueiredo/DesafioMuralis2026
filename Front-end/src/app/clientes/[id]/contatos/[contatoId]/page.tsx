"use client"

import React, { useEffect } from "react";
import { useGetClientById } from "@/hooks/clients/useGetClientsById";
import { useGetContactById } from "@/hooks/contacts/useGetContactById";
import { useDeleteContact } from "@/hooks/contacts/useDeleteContact";
import { ContactForm } from "@/components/contact/contact-form";
import { Dialog, DialogContent, DialogTrigger } from "@/components/ui/dialog";
import { Skeleton } from "@/components/ui/skeleton";
import { Button } from "@/components/ui/button";
import { Separator } from "@/components/ui/separator";
import { Badge } from "@/components/ui/badge";
import { toast } from "sonner";
import { Pencil, Trash2, Phone, Mail, MessageSquare, User, CreditCard, MapPin, Calendar } from "lucide-react";

const tipoIconMap: Record<string, React.ReactNode> = {
  TELEFONE: <Phone className="w-3 h-3" />,
  EMAIL: <Mail className="w-3 h-3" />,
  DEFAULT: <MessageSquare className="w-3 h-3" />,
};

const tipoLabelMap: Record<string, string> = {
  TELEFONE: "Telefone",
  EMAIL: "E-mail",
};

export default function Page({ params }: { params: Promise<{ id: string; contatoId: string }> }) {
  const { id, contatoId } = React.use(params);

  const { data: cliente } = useGetClientById(id);
  const {
    data: contato,
    status: contatoStatus,
    isSuccess,
    isFetchedAfterMount,
    error,
  } = useGetContactById(contatoId);

  const { mutate: deleteContact } = useDeleteContact();

  useEffect(() => {
    if (isSuccess && isFetchedAfterMount) {
      toast.success("Contato buscado com sucesso!");
    }
  }, [isSuccess, isFetchedAfterMount]);

  function handleDeleteContact() {
    if (confirm("Tem certeza que deseja excluir este contato?")) {
      deleteContact(contatoId);
    }
  }

  if (contatoStatus === "pending") {
    return (
      <main className="w-full max-w-3xl mx-auto pt-8 p-10 flex flex-col gap-6">
        <Skeleton className="h-10 w-48 rounded-md bg-zinc-200" />
        <Skeleton className="h-6 w-32 rounded-md bg-zinc-200" />
        <Skeleton className="h-[200px] w-full rounded-md bg-zinc-200" />
      </main>
    );
  }

  if (contatoStatus === "error") {
    return (
      <main className="w-full max-w-3xl mx-auto pt-8 p-10 flex flex-col gap-6">
        <div className="w-full flex justify-center items-center flex-col gap-1 py-8 text-destructive">
          <h2 className="font-semibold">Ocorreu um erro :/</h2>
          <p className="text-sm">{error.message}</p>
        </div>
      </main>
    );
  }

  if (!contato) return null;

  const tipo = contato.contatoValor.tipo;
  const tipoIcon = tipoIconMap[tipo] ?? tipoIconMap.DEFAULT;
  const tipoLabel = tipoLabelMap[tipo] ?? tipo;

  return (
    <main className="w-full max-w-3xl mx-auto pt-8 p-10 flex flex-col gap-6">

      {/* Header */}
      <div className="flex items-start justify-between">
        <div className="flex flex-col gap-1">
          <div className="flex items-center gap-2">
            {tipoIcon && <span className="text-muted-foreground">{tipoIcon}</span>}
            <h1 className="text-2xl font-semibold tracking-tight">{contato.contatoValor.value}</h1>
          </div>
          <div className="flex items-center gap-2">
            <Badge variant="secondary">{tipoLabel}</Badge>
            <Badge variant="outline">Contato #{contato.id}</Badge>
          </div>
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
              <ContactForm Contact={contato} />
            </DialogContent>
          </Dialog>

          <Button variant="destructive" size="sm" onClick={handleDeleteContact}>
            <Trash2 className="w-4 h-4 mr-2" />
            Excluir
          </Button>
        </div>
      </div>

      <Separator />

      {/* Contato info */}
      <div className="grid grid-cols-2 gap-4">
        <div className="flex flex-col gap-1">
          <span className="text-xs text-muted-foreground flex items-center gap-1">
            {tipoIcon} Tipo
          </span>
          <span className="text-sm font-medium">{tipoLabel}</span>
        </div>
        <div className="flex flex-col gap-1">
          <span className="text-xs text-muted-foreground flex items-center gap-1">
            <MessageSquare className="w-3 h-3" /> Observação
          </span>
          <span className="text-sm font-medium">{contato.observacao ?? "—"}</span>
        </div>
      </div>

      <Separator />

      {/* Cliente vinculado */}
      <div className="flex flex-col gap-3">
        <h2 className="text-base font-semibold">Cliente vinculado</h2>

        {!cliente ? (
          <Skeleton className="h-10 w-full rounded-sm bg-zinc-200" />
        ) : (
          <div className="rounded-md border p-4 grid grid-cols-2 gap-4 sm:grid-cols-4">
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
        )}
      </div>

    </main>
  );
}