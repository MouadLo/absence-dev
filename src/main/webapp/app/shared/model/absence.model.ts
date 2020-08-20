import { IHoraire } from 'app/shared/model/horaire.model';
import { IEtudiant } from 'app/shared/model/etudiant.model';

export interface IAbsence {
  id?: number;
  idAbsence?: number;
  horaire?: IHoraire;
  etudiant?: IEtudiant;
}

export const defaultValue: Readonly<IAbsence> = {};
