"use client";

import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Separator } from "@/components/ui/separator";
import { User, MapPin, Calendar, Search, ExternalLink } from "lucide-react";
import { useState } from "react";
import { useGetClientsByCPF } from "@/hooks/clients/useGetClientsByCPF";
import Link from "next/link";

export default function BuscarCpfPage() {
  const [input, setInput] = useState("");
  const [submittedCpf, setSubmittedCpf] = useState("");

  const { data: cliente, status } = useGetClientsByCPF(submittedCpf);

  function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setSubmittedCpf(input.replace(/\D/g, ""));
  }

  return (
    <main className="w-full max-w-md mx-auto pt-8 p-10 flex flex-col gap-4">
      <h1 className="text-2xl font-semibold tracking-tight">Buscar por CPF</h1>

      <form onSubmit={handleSubmit} className="flex gap-2">
        <Input
          placeholder="000.000.000-00"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          autoFocus
        />
        <Button type="submit" size="sm">
          <Search className="w-4 h-4" />
        </Button>
      </form>

      {submittedCpf && (
        <div className="mt-2">
          {status === "pending" ? (
            <p className="text-sm text-muted-foreground text-center py-4">Buscando...</p>
          ) : status === "error" || !cliente ? (
            <p className="text-sm text-muted-foreground text-center py-4">
              Nenhum cliente encontrado para esse CPF.
            </p>
          ) : (
            <div className="rounded-md border p-4 flex flex-col gap-3">
              <div className="flex items-center justify-between">
                <span className="font-semibold text-base">{cliente.nome}</span>
                <div className="flex items-center gap-2">
                  <Badge variant="secondary">Cliente #{cliente.id}</Badge>
                  <Button variant="outline" size="sm" asChild>
                    <Link href={`/clientes/${cliente.id}`}>
                      <ExternalLink className="w-4 h-4 mr-2" />
                      Ver
                    </Link>
                  </Button>
                </div>
              </div>

              <Separator />

              <div className="grid grid-cols-2 gap-3">
                <div className="flex flex-col gap-1">
                  <span className="text-xs text-muted-foreground flex items-center gap-1">
                    <User className="w-3 h-3" /> CPF
                  </span>
                  <span className="text-sm font-medium">{cliente.cpf}</span>
                </div>
                <div className="flex flex-col gap-1">
                  <span className="text-xs text-muted-foreground flex items-center gap-1">
                    <Calendar className="w-3 h-3" /> Nascimento
                  </span>
                  <span className="text-sm font-medium">{cliente.dataNascimento}</span>
                </div>
                <div className="flex flex-col gap-1 col-span-2">
                  <span className="text-xs text-muted-foreground flex items-center gap-1">
                    <MapPin className="w-3 h-3" /> Endereço
                  </span>
                  <span className="text-sm font-medium">{cliente.endereco ?? "—"}</span>
                </div>
              </div>
            </div>
          )}
        </div>
      )}
    </main>
  );
}