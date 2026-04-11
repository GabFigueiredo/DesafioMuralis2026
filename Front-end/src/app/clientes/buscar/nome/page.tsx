"use client";

import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { DataTable } from "@/components/table/cliente/data-table";
import { clienteColumns } from "@/components/table/cliente/columns";
import { Skeleton } from "@/components/ui/skeleton";
import { useState } from "react";
import { Search } from "lucide-react";
import { useGetClientsByName } from "@/hooks/clients/useGetClientsByName";

export default function BuscarNomePage() {
  const [input, setInput] = useState("");
  const [submittedNome, setSubmittedNome] = useState("");
  const [page, setPage] = useState(0);

  const { data: clientes, status } = useGetClientsByName({ nome: submittedNome, page, size: 10 });

  function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setPage(0);
    setSubmittedNome(input.trim());
  }

  return (
    <main className="w-full max-w-4xl mx-auto pt-8 p-10 flex flex-col gap-4">
      <h1 className="text-2xl font-semibold tracking-tight">Buscar por nome</h1>

      <form onSubmit={handleSubmit} className="flex gap-2">
        <Input
          placeholder="Digite o nome..."
          value={input}
          onChange={(e) => setInput(e.target.value)}
          autoFocus
        />
        <Button type="submit" size="sm">
          <Search className="w-4 h-4" />
        </Button>
      </form>

      {submittedNome && (
        <div className="mt-2">
          {status === "pending" ? (
            <div className="flex flex-col gap-2">
              {Array.from({ length: 5 }).map((_, i) => (
                <Skeleton key={i} className="h-10 w-full rounded-sm bg-zinc-200" />
              ))}
            </div>
          ) : status === "error" ? (
            <p className="text-sm text-destructive text-center py-4">Erro ao buscar clientes.</p>
          ) : (
            <DataTable
              columns={clienteColumns}
              data={clientes.content}
              paginationProps={{
                page,
                totalPages: clientes.totalPages,
                onPreviousPage: () => setPage((p) => p - 1),
                onNextPage: () => setPage((p) => p + 1),
              }}
            />
          )}
        </div>
      )}
    </main>
  );
}