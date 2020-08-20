import { IMatiere } from 'app/shared/model/matiere.model';

export interface IProf {
  id?: number;
  login?: string;
  mdp?: string;
  email?: string;
  nom?: string;
  prenom?: string;
  type?: string;
  tel?: string;
  matieres?: IMatiere[];
}

export const defaultValue: Readonly<IProf> = {};
