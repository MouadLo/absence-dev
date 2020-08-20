import { IFiliere } from 'app/shared/model/filiere.model';
import { IEtudiant } from 'app/shared/model/etudiant.model';

export interface IClasse {
  id?: number;
  idClasse?: number;
  nomClasse?: string;
  niveau?: string;
  filiere?: IFiliere;
  etudiants?: IEtudiant[];
}

export const defaultValue: Readonly<IClasse> = {};
