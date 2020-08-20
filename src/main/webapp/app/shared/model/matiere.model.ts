import { IHoraire } from 'app/shared/model/horaire.model';
import { IProf } from 'app/shared/model/prof.model';

export interface IMatiere {
  id?: number;
  idMatiere?: number;
  nomMatiere?: string;
  abreviation?: string;
  horaires?: IHoraire[];
  profs?: IProf[];
}

export const defaultValue: Readonly<IMatiere> = {};
