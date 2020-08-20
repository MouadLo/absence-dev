import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import filiere, {
  FiliereState
} from 'app/entities/filiere/filiere.reducer';
// prettier-ignore
import classe, {
  ClasseState
} from 'app/entities/classe/classe.reducer';
// prettier-ignore
import etudiant, {
  EtudiantState
} from 'app/entities/etudiant/etudiant.reducer';
// prettier-ignore
import absence, {
  AbsenceState
} from 'app/entities/absence/absence.reducer';
// prettier-ignore
import horaire, {
  HoraireState
} from 'app/entities/horaire/horaire.reducer';
// prettier-ignore
import matiere, {
  MatiereState
} from 'app/entities/matiere/matiere.reducer';
// prettier-ignore
import prof, {
  ProfState
} from 'app/entities/prof/prof.reducer';
// prettier-ignore
import admin, {
  AdminState
} from 'app/entities/admin/admin.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly filiere: FiliereState;
  readonly classe: ClasseState;
  readonly etudiant: EtudiantState;
  readonly absence: AbsenceState;
  readonly horaire: HoraireState;
  readonly matiere: MatiereState;
  readonly prof: ProfState;
  readonly admin: AdminState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  filiere,
  classe,
  etudiant,
  absence,
  horaire,
  matiere,
  prof,
  admin,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
