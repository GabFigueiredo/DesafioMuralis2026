'use client'

import { useState } from "react";
import { useRouter } from "next/navigation";
import { RadioGroup, RadioGroupItem } from "../ui/radio-group";
import { Label } from "../ui/label";

export function SearchBar() {
    const router = useRouter()
    const [filterOption, setFilterOption] = useState<'nome' | 'CPF'>('nome');
  
    const handleChangeFilterOption = (value: 'nome' | 'CPF') => {
      setFilterOption(value);
    };

    function handleChangeSearchParameters(value: string) {
      router.replace(`/clientes?${filterOption}=${value}`);
    } 
  
    return (
      <div className="w-full flex items-center rounded-md border border-zinc-300 bg-zinc-100 p-0">
 
        <form className="flex flex-1 items-center h-full bg-zinc-100 rounded-md gap-2">
          <input
            placeholder={`Pesquisa por ${filterOption}`}
            onChange={e => handleChangeSearchParameters(e.target.value)}
            required
            className="flex-1 p-4 rounded-l-md border-0 bg-zinc-100 placeholder-zinc-500 focus:outline-none"
          />
        </form>
  
        <RadioGroup 
            className="flex items-center justify-center h-full bg-zinc-100 rounded-md border border-gray-300"
            value={filterOption}
            onValueChange={(value) => handleChangeFilterOption(value as 'nome' | 'CPF')}
        >
            <div
                className={`max-w-[77px] h-full w-full flex items-center justify-center border-0 bg-transparent cursor-pointer p-4 rounded-md ${
                filterOption === 'nome' ? 'bg-zinc-300' : ''
            }`}>
                <RadioGroupItem value="nome" id="nome" className="hidden" />
                <Label htmlFor="nome">Nome</Label>
            </div>
  
            <div
                className={`max-w-[77px] w-full flex items-center justify-center border-0 bg-transparent cursor-pointer p-4 rounded-md ${
                filterOption === 'CPF' ? 'bg-zinc-300' : ''
            }`}>
                <RadioGroupItem value="CPF" id="cpf" className="hidden" />
                <Label htmlFor="cpf">CPF</Label>
            </div>
        </RadioGroup>

      </div>
    );
  }