"use client"

import { Button } from "@/components/ui/button"
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuTrigger } from "@/components/ui/dropdown-menu"
import { ICliente } from "@/interfaces/client/ICliente"
import { ColumnDef } from "@tanstack/react-table"
import { MoreHorizontal } from "lucide-react"
import { useRouter } from "next/navigation"

export const clienteColumns: ColumnDef<ICliente>[] = [
  {
    accessorKey: "id",
    header: "ID",
  },
  {
    accessorKey: "nome",
    header: "Nome",
  },
  {
    accessorKey: "cpf",
    header: "CPF",
  },
  {
    accessorKey: "dataNascimento",
    header: "Data de Nascimento",
  },
  {
    accessorKey: "endereco",
    header: "Endereço",
  },
  {
    id: "actions",
    cell: ({ row }) => {
      const cliente = row.original
 
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
              <SeeClienteItem clientId={cliente.id} />
          </DropdownMenuContent>
        </DropdownMenu>
      )
    },
  },
]

export default function SeeClienteItem({ clientId }: { clientId: number }) {
  const router = useRouter();

  return (
    <DropdownMenuItem
      onClick={() => router.push(`/clientes/${clientId}`)}
    >
      Ver Cliente
    </DropdownMenuItem>
  );
}