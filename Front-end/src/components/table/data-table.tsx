"use client"

import {
  ColumnDef,
  flexRender,
  getCoreRowModel,
  useReactTable,
} from "@tanstack/react-table"

import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"
import { Button } from "@/components/ui/button"

interface DataTableProps<TData, TValue> {
  columns: ColumnDef<TData, TValue>[]
  data: TData[]
  paginationProps: {
    page: number
    totalPages: number
    onPreviousPage: () => void
    onNextPage: () => void
  }
}

export function DataTable<TData, TValue>({
  columns,
  data,
  paginationProps,
}: DataTableProps<TData, TValue>) {
  const { page, totalPages, onPreviousPage, onNextPage } = paginationProps;

  const table = useReactTable({
    data,
    columns,
    getCoreRowModel: getCoreRowModel(),
  })

  return (
    <div>
      <div className="overflow-hidden rounded-md border">
        <Table>
          <TableHeader>
            {table.getHeaderGroups().map((headerGroup) => (
              <TableRow key={headerGroup.id}>
                {headerGroup.headers.map((header) => (
                  <TableHead key={header.id}>
                    {header.isPlaceholder
                      ? null
                      : flexRender(header.column.columnDef.header, header.getContext())}
                  </TableHead>
                ))}
              </TableRow>
            ))}
          </TableHeader>
          <TableBody>
            {table.getRowModel().rows?.length ? (
              table.getRowModel().rows.map((row) => (
                <TableRow key={row.id} data-state={row.getIsSelected() && "selected"}>
                  {row.getVisibleCells().map((cell) => (
                    <TableCell key={cell.id}>
                      {flexRender(cell.column.columnDef.cell, cell.getContext())}
                    </TableCell>
                  ))}
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell colSpan={columns.length} className="h-24 text-center">
                  No results.
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </div>

      <div className="flex items-center justify-end space-x-2 py-4">
        <span className="text-sm text-muted-foreground mr-auto">
          Página {page + 1} de {totalPages}
        </span>
        <Button
          variant="outline"
          size="sm"
          onClick={onPreviousPage}
          disabled={page <= 0}
        >
          Anterior
        </Button>
        <Button
          variant="outline"
          size="sm"
          onClick={onNextPage}
          disabled={page >= totalPages - 1}
        >
          Próximo
        </Button>
      </div>
    </div>
  )
}