'use client'

import { RefreshCcw } from "lucide-react";
import { Dialog, DialogContent, DialogTrigger } from "../ui/dialog";
import { Button } from "../ui/button";
import { useRefreshData } from "@/hooks/useRefresh";
import { ContactForm } from "./contact-form";

export function ContactHeader() {
    const { handleRefresh } = useRefreshData();

    return (
        <div className="w-full flex items-center justify-between">
            <div className="flex items-center gap-3">
            <strong className="text-4xl font-bold">Contatos</strong>
            <RefreshCcw
                className="cursor-pointer"
                onClick={() => handleRefresh("contatos")}
            />
            </div>
            <Dialog>
                <DialogTrigger asChild>
                    <Button className="cursor-pointer">Criar</Button>
                </DialogTrigger>
                <DialogContent className="max-w-[500px] w-full p-0">
                    <ContactForm />
                </DialogContent>
            </Dialog>
        </div>
    )
}