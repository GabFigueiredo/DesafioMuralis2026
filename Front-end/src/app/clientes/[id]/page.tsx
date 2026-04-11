"use client"

import ClientDetails from "@/templates/clientes/client-detail";

export default function Page({ params }: { params: Promise<{ id: string }> }) {
  return <ClientDetails params={params} />;
}