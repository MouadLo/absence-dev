export interface IAdmin {
  id?: number;
  login?: string;
  mdp?: string;
  email?: string;
  nom?: string;
  prenom?: string;
  tel?: string;
}

export const defaultValue: Readonly<IAdmin> = {};
