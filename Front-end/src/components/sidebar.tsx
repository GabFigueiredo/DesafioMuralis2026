// components/sidebar.tsx
"use client";

import { NavLink } from "./navlink";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { Search, CreditCard } from "lucide-react";
import { Button } from "@/components/ui/button";

export function SideBar() {
  const router = useRouter();

  return (
    <div className="w-full flex justify-between p-4 px-8 bg-BlueMuralis text-white items-center">
      <div className="flex items-center gap-6">
        <NavLink href="/clientes">Clientes</NavLink>

        <Button
          variant="ghost"
          size="sm"
          className="text-white hover:text-white hover:bg-white/20 flex items-center gap-2"
        >
          <Search className="w-4 h-4" />
          <Link href="/clientes/buscar/nome">Buscar por nome</Link>
        </Button>

        <Button
          variant="ghost"
          size="sm"
          className="text-white hover:text-white hover:bg-white/20 flex items-center gap-2"
        >
          <CreditCard className="w-4 h-4" />
          <Link href="/clientes/buscar/cpf">Buscar por CPF</Link>
        </Button>
      </div>

      <div className="flex justify-center">
        <Link href="https://www.linkedin.com/in/gabrielfigueiredodeandrade/" target="_blank">
          Gabriel Figueiredo | Linkedin
        </Link>
      </div>
    </div>
  );
}