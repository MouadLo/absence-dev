import { Moment } from 'moment';
import { IMatiere } from 'app/shared/model/matiere.model';
import { IAbsence } from 'app/shared/model/absence.model';

export interface IHoraire {
  id?: number;
  idHoraire?: number;
  heureDepart?: string;
  heureF?: string;
  date?: string;
  matieres?: IMatiere[];
  absences?: IAbsence[];
}

export const defaultValue: Readonly<IHoraire> = {};
