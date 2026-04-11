export interface IContato {
  id: number,
  clienteId: number,
  contatoValor: IContatoValor,
  observacao: string,
}

interface IContatoValor {
  tipo: string,
  value: string
}