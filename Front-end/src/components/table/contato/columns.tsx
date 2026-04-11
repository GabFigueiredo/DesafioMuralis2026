"use client"

import { Button } from "@/components/ui/button"
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuTrigger } from "@/components/ui/dropdown-menu"
import { IContato } from "@/interfaces/contact/IContato"
import { ColumnDef } from "@tanstack/react-table"
import { MoreHorizontal } from "lucide-react"
import { useRouter } from "next/navigation"

export const contatoColumns: ColumnDef<IContato>[] = [
  {
    accessorKey: "id",
    header: "ID",
  },
  {
    accessorKey: "contatoValor.tipo",
    header: "Tipo",
  },
  {
    accessorKey: "contatoValor.value",
    header: "Valor",
  },
  {
    accessorKey: "observacao",
    header: "Observação",
  },
  {
    id: "actions",
    cell: ({ row }) => {
      const contato = row.original
 
      return (
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button variant="ghost" className="h-8 w-8 p-0">
              <span className="sr-only">Open menu</span>
              <MoreHorizontal className="h-4 w-4" />
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent align="end">
            <DropdownMenuLabel>Ações</DropdownMenuLabel>
            <SeeContatoItem contatoId={contato.id} clienteId={contato.clienteId} />
          </DropdownMenuContent>
        </DropdownMenu>
      )
    },
  },
]

export default function SeeContatoItem({ contatoId, clienteId }: { contatoId: number, clienteId: number }) {
  const router = useRouter();

  return (
    <DropdownMenuItem
      onClick={() => router.push(`/clientes/${clienteId}/contatos/${contatoId}`)}
    >
      Ver Contato
    </DropdownMenuItem>
  );
}