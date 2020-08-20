import { IClasse } from 'app/shared/model/classe.model';
import { IAbsence } from 'app/shared/model/absence.model';

export interface IEtudiant {
  id?: number;
  login?: string;
  mdp?: string;
  cne?: string;
  email?: string;
  nom?: string;
  prenom?: string;
  tel?: string;
  classe?: IClasse;
  absences?: IAbsence[];
}

export const defaultValue: Readonly<IEtudiant> = {};
