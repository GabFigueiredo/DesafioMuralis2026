import { RefreshCcw } from "lucide-react";
import { Dialog, DialogContent, DialogTrigger } from "../ui/dialog";
import { Button } from "../ui/button";
import { useRefreshData } from "@/hooks/useRefresh";
import { ClientForm } from "./client-form";

export function ClientHeader() {
    const { handleRefresh } = useRefreshData();

    return (
        <div className="w-full flex items-center justify-between">
            <div className="flex items-center gap-3">
            <strong className="text-4xl font-bold">Clientes</strong>
            <RefreshCcw
                className="cursor-pointer"
                onClick={() => handleRefresh("clientes")}
            />
            </div>
            <Dialog>
            <DialogTrigger asChild>
                <Button className="cursor-pointer">Criar</Button>
            </DialogTrigger>
            <DialogContent className="max-w-[500px] w-full p-0">
                <ClientForm />
            </DialogContent>
            </Dialog>
        </div>
    )
}