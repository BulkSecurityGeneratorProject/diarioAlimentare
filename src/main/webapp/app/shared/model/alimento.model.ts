export interface IAlimento {
  id?: number;
  nome?: string;
  descrizione?: string;
}

export class Alimento implements IAlimento {
  constructor(public id?: number, public nome?: string, public descrizione?: string) {}
}
