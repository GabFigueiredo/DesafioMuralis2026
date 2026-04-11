import * as z from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import { Controller, useForm } from "react-hook-form";
import { useUpdateClient } from "@/hooks/clients/useUpdateClient";
import { useCreateClient } from "@/hooks/clients/useCreateClient";
import { useEffect } from "react";
import { Input } from "../ui/input";
import { CPFInput } from "../cpf-input";
import { DialogTitle } from '../ui/dialog';
import { ICliente } from '@/interfaces/client/ICliente';

const NewClientSchema = z.object({
    nome: z.string(),
    cpf: z.string(),
    dataNascimento: z.string(),
    endereco: z.string(),
})

type newClientFormInputs = z.infer<typeof NewClientSchema>

export function ClientForm({ client }: { client?: ICliente }) {
    const { mutate: createClient, status: createStatus } = useCreateClient();
    const { mutate: updateClient, status: updateStatus } = useUpdateClient();

    const {
        register,
        handleSubmit,
        watch,
        control,
        reset
    }
        = useForm<newClientFormInputs>({
        resolver: zodResolver(NewClientSchema),
        mode: "onChange",
        defaultValues: {
            nome: client?.nome ?? "",
            cpf: client?.cpf ?? "",
            dataNascimento: client?.dataNascimento ?? "",
            endereco: client?.endereco ?? "",
        }
    })

    useEffect(() => {
        if (client) {
          reset({
            nome: client.nome,
            cpf: client.cpf,
            dataNascimento: client.dataNascimento,
            endereco: client.endereco,
          });
        }
      }, [client, reset]);

    async function handleSubmitAction(formData: newClientFormInputs) {
        if (!!client) {
            updateClient({id: client.id, dadosAtualizados: formData});
        }
        
        if (!client) {
            createClient(formData);
        }

    }

    // Visibilidade do Botão Submit
    const nome = watch("nome");
    const cpf = watch("cpf");

    const isSubmitNotValid = !(nome && cpf?.length === 11);
    const isPending = createStatus === "pending" || updateStatus === "pending";

    return (
        <>
            <div className="flex items-center justify-between min-w-[500px] bg-blue-800 text-white px-6 py-4 rounded-t-md">
                <DialogTitle className="text-lg font-semibold">{ client? "Editar cliente" : "Novo cliente" }</DialogTitle>
            </div>

            <form onSubmit={handleSubmit(handleSubmitAction)} className="flex flex-col gap-4 px-6 py-4">
                <Input
                    placeholder="Nome"
                    required
                    {...register("nome")}
                />

                <Input
                    placeholder="Endereço"
                    required
                    {...register("endereco")}   
                />

                <Controller
                    name="cpf"
                    control={control}
                    render={({ field }) => (
                    <CPFInput
                        value={field.value}
                        onChange={field.onChange} />
                    )}
                />

                <Input
                    type="date"
                    placeholder="Data de nascimento"
                    className="text-gray-600"
                    {...register("dataNascimento")}
                />

                <button
                    type="submit"
                    disabled={isSubmitNotValid || isPending}
                    className={`cursor-pointer h-14 px-5 font-bold rounded-md transition ${
                    isSubmitNotValid || isPending
                        ? "border border-orange-500 text-orange-500 bg-transparent cursor-not-allowed"
                        : "bg-orange-500 text-white hover:bg-orange-600"
                    }`}
                >
                    {client ? "Editar" : "Cadastrar"}
                </button>
            </form> 
        </>
    )
}