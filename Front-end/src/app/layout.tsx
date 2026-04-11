import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import { SideBar } from "@/components/sidebar";
import { ClientToaster } from "@/components/client-toaster";
import { ReactQueryProvider } from "@/providers/react-query-provider";
import { ReactNode } from "react";

const inter = Inter({ variable: "--font-inter", subsets: ["latin"] });

export const metadata: Metadata = {
  title: "Desafio Muralis",
  description: "Feito com Next JS",
};

export default function RootLayout({
  children,
  modal,
}: {
  children: ReactNode;
  modal: ReactNode;
}) {
  return (
    <html lang="en">
      <ReactQueryProvider>
        <body suppressHydrationWarning className={`${inter.variable} antialiased bg-zinc-50 flex flex-col`}>
          <SideBar />
          <div className="w-full max-w-[1440px] mx-auto">
            {children}
          </div>
          {modal}
          <ClientToaster />
        </body>
      </ReactQueryProvider>
    </html>
  );
}