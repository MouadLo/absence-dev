import { IClasse } from 'app/shared/model/classe.model';

export interface IFiliere {
  id?: number;
  idFiliere?: number;
  nomFiliere?: string;
  abreviation?: string;
  classes?: IClasse[];
}

export const defaultValue: Readonly<IFiliere> = {};
