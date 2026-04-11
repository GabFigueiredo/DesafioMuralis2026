import { DialogContent, DialogDescription, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { useDeleteClient } from "@/hooks/clients/useDeleteClient";
import { Pencil, Trash } from "lucide-react";
import React, { ButtonHTMLAttributes, ReactNode } from "react";


export function PersonaCard({children} : {children: ReactNode}) {
  return (
    <DialogContent className="bg-BlueMuralis rounded-t-md p-6 text-zinc-100">
        {children}
    </DialogContent>
  )
}

PersonaCard.Header = function Header({ children }: { children: ReactNode }) {
  return (
    <DialogHeader className="w-[700px] flex gap-2 flex-row items-center">
      {children}
    </DialogHeader>
  )
}

PersonaCard.Title = function Title({ children }: { children: ReactNode }) {
  return (
      <DialogTitle className="text-xl font-bold w-fit">{children}</DialogTitle>
  )
}

type DeleteButtonProps = ButtonHTMLAttributes<HTMLButtonElement>;

PersonaCard.DeleteButton = function DeleteButton(props: DeleteButtonProps) {

  return (
    <button {...props}>
        <Trash
          size={19}
          className="cursor-pointer hover:text-orange-500"
        />
    </button>
  )
}

type EditButtonProps = ButtonHTMLAttributes<HTMLButtonElement>;

PersonaCard.EditButton = function EditButton(props: EditButtonProps) {
  return (
    <button {...props}>
      <Pencil
        size={19}
        className="cursor-pointer hover:text-orange-500"
      />
    </button>
  )
}

PersonaCard.SubTitle = function SubTitle({ children }: { children: ReactNode }) {
  return <DialogDescription className="text-[#BABABA] text-base">{children}</DialogDescription>
}