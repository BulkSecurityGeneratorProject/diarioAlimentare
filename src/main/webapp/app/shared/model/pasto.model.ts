import { Moment } from 'moment';

export interface IPasto {
  id?: number;
  data?: Moment;
  quantita?: number;
  alimentoNome?: string;
  alimentoId?: number;
}

export class Pasto implements IPasto {
  constructor(
    public id?: number,
    public data?: Moment,
    public quantita?: number,
    public alimentoNome?: string,
    public alimentoId?: number
  ) {}
}
