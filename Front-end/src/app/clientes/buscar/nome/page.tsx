"use client";

import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { DataTable } from "@/components/table/data-table";
import { clienteColumns } from "@/components/table/cliente/columns";
import { Skeleton } from "@/components/ui/skeleton";
import { useState } from "react";
import { Search } from "lucide-react";
import { useGetClientsByName } from "@/hooks/clients/useGetClientsByName";
import BuscarNomePage from "@/templates/busca/search-name-page";

export default function Page() {
  return <BuscarNomePage />; 
}