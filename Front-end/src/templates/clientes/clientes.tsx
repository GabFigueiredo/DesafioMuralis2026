"use client";

import { Skeleton } from "@/components/ui/skeleton";
import { useClientsQuery } from "@/hooks/clients/useGetClients";
import { useEffect, useState } from "react";
import { toast } from "sonner";
import { ClientHeader } from "@/components/client/client-header";
import { SearchBar } from "@/components/client/searchbar";
import { clienteColumns } from "@/components/table/cliente/columns";
import { DataTable } from "@/components/table/cliente/data-table";

export function ClientePage() {
  const [page, setPage] = useState(0);
  const {
    data: clientes,
    error,
    isFetchedAfterMount,
    status,
  } = useClientsQuery({page, size: 10});

  useEffect(() => {
    if (status === "pending" ) {
      toast.loading("Carregando...");
    }
  
    if (status === "success" && isFetchedAfterMount) {
      toast.success("Clientes buscados com sucesso!");
    }
  
    if (status === "error" && isFetchedAfterMount) {
      toast.error("Erro ao buscar clientes: " + error.message);
    }

  }, [status, isFetchedAfterMount, error]);

  return (
    <main className="w-full flex flex-col pt-8 p-10 gap-4">
      <ClientHeader />
      <SearchBar />

      {status === "pending" ? (
        <>
          {Array.from({ length: 10 }).map((_, index) => (
            <Skeleton
              key={index}
              className="h-[50px] w-full rounded-sm bg-zinc-300"
            />
          ))}
        </>
      ) : status === "error" ? (
        <div className="w-full flex justify-center items-center flex-col">
          <h2>Ocorreu um erro :/</h2>
          <p>{error.message}</p>
        </div>
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
    </main>
  );
}