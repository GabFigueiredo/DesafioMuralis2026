'use client'

import { zodResolver } from '@hookform/resolvers/zod'
import { Controller, useForm } from "react-hook-form";
import { useEffect } from "react";
import { DialogTitle } from '../ui/dialog';
import { Input } from '../ui/input';
import { useCreateContact } from '@/hooks/contacts/useCreateContact';
import { contactRequest, singleContactRequestSchema } from '@/interfaces/contact/contato-request-schema';
import { useUpdateContact } from '@/hooks/contacts/useUpdateContact';
import { Label } from '../ui/label';
import { RadioGroup, RadioGroupItem } from '../ui/radio-group';
import { PhoneInput } from '../phone-input';
import { Textarea } from '../ui/textarea';
import { IContato } from '@/interfaces/contact/IContato';

export function ContactForm({ Contact }: { Contact?: IContato }) {
    const { mutate: createContact, status: createStatus } = useCreateContact()
    const { mutate: updateContact, status: updateStatus } = useUpdateContact()

    const {register,
        handleSubmit,
        watch,
        reset,
        control
    }
        = useForm<contactRequest>({
        resolver: zodResolver(singleContactRequestSchema),
        mode: "onChange",
        defaultValues: {
            clienteId: Contact?.clienteId.toString() ?? "",
            tipo: Contact?.contatoValor.tipo ?? "",
            valor: Contact?.contatoValor.value ?? "",
            observacao: Contact?.observacao?? ""
        }
    })

    useEffect(() => {
        if (Contact) {
          reset({
            clienteId: Contact.clienteId.toString(),
            tipo: Contact.contatoValor.tipo,
            valor: Contact.contatoValor.value,
            observacao: Contact.observacao 
          });
        }
      }, [Contact, reset]);


    async function handleSubmitAction(formData: contactRequest) {
        if (!!Contact) {
            updateContact({id: Contact.id.toString(), dadosAtualizados: formData})
        }
        
        if (!Contact) {
            createContact(formData)
        }

    }

    // Visibilidade do Botão Submit
    const nomeInput = watch("clienteId");
    const tipoInput = watch("tipo");
    const valorInput = watch("valor");

    const isSubmitNotValid = !(nomeInput && ( tipoInput === "email" ||  (tipoInput === "telefone" && valorInput?.length === 11) ));
    const isPending = createStatus === "pending" || updateStatus === "pending";

    return (
        <>
            <div className="flex items-center justify-between min-w-[500px] bg-blue-800 text-white px-6 py-4 rounded-t-md">
                <DialogTitle className="text-lg font-semibold">{ Contact? "Editar Contato" : "Novo Contato" }</DialogTitle>
            </div>

            <form onSubmit={handleSubmit(handleSubmitAction)} className="flex flex-col gap-4 px-6 py-4">
                <Input
                    placeholder="ID do cliente"
                    required
                    {...register("clienteId")}
                />

                <div className='flex gap-3'>
                    <Controller
                        name='tipo'
                        control={control}
                        render={({field}) => (

                        <RadioGroup
                            defaultValue="telefone"
                            className='flex'
                            onValueChange={field.onChange}
                            value={field.value}
                            >
                            <div className="flex items-center gap-3">
                                <RadioGroupItem value="email" id="r1" />
                                <Label htmlFor="r1">Email</Label>
                            </div>
                            <div className="flex items-center gap-3">
                                <RadioGroupItem value="telefone" id="r2" />
                                <Label htmlFor="r2">Telefone</Label>
                            </div>
                        </RadioGroup>
                        )}
                    />

                    {tipoInput === "email" ?
                        <Input
                            type='email'
                            placeholder="Email"
                            required
                            {...register("valor")}   
                        /> 
                    : 
                        <Controller
                            name='valor'
                            control={control}
                            render={({ field }) => (
                                <PhoneInput
                                    value={field.value}
                                    onChange={field.onChange} />
                                )}
                        />
                    }
                </div>

                <Controller
                    name='observacao'
                    control={control}
                    render={({field}) => (
                        <Textarea 
                            value={field.value}
                            onChange={field.onChange}
                        />        
                    )}
                />

                <button
                    type="submit"
                    disabled={isSubmitNotValid || isPending}
                    className={`not-disabled:cursor-pointer h-14 px-5 font-bold rounded-md transition ${
                        isSubmitNotValid || isPending
                        ? "border border-orange-500 text-orange-500 bg-transparent cursor-not-allowed"
                        : "bg-orange-500 text-white hover:bg-orange-600"
                    }`}
                >
                    {Contact ? "Editar" : "Cadastrar"}
                </button>
            </form> 
        </>
    )
}