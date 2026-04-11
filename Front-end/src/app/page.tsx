import Link from "next/link";
import { ArrowRight, Users } from "lucide-react";

export default function Page() {
  return (
    <main className="min-h-[calc(100vh-64px)] flex flex-col items-center justify-center p-8 text-center gap-8">

      <div className="flex flex-col items-center gap-4 max-w-lg">
        <div className="w-14 h-14 rounded-xl border bg-zinc-100 flex items-center justify-center">
          <Users className="w-7 h-7 text-zinc-700" />
        </div>

        <h1 className="text-3xl font-semibold tracking-tight">Desafio Muralis</h1>

        <p className="text-muted-foreground text-base leading-relaxed max-w-sm">
          Sistema de gerenciamento de clientes e contatos. Cadastre, edite, visualize e organize tudo em um só lugar.
        </p>

        <Link
          href="/clientes"
          className="inline-flex items-center gap-2 mt-2 px-5 py-2.5 rounded-md border text-sm font-medium hover:bg-zinc-100 transition-colors"
        >
          Acessar clientes
          <ArrowRight className="w-4 h-4" />
        </Link>
      </div>

      <div className="grid grid-cols-3 gap-3 max-w-lg w-full">
        {[
          "Cadastro de clientes",
          "Gestão de contatos",
          "Busca por CPF e nome",
        ].map((feat) => (
          <div key={feat} className="bg-zinc-100 rounded-lg p-4 text-left">
            <p className="text-xs text-muted-foreground mb-1">Funcionalidade</p>
            <p className="text-sm font-medium">{feat}</p>
          </div>
        ))}
      </div>

      <p className="text-xs text-muted-foreground">
        Feito por Gabriel Figueiredo · Next.js + Spring Boot
      </p>

    </main>
  );
}