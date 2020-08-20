import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Filiere from './filiere';
import Classe from './classe';
import Etudiant from './etudiant';
import Absence from './absence';
import Horaire from './horaire';
import Matiere from './matiere';
import Prof from './prof';
import Admin from './admin';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}filiere`} component={Filiere} />
      <ErrorBoundaryRoute path={`${match.url}classe`} component={Classe} />
      <ErrorBoundaryRoute path={`${match.url}etudiant`} component={Etudiant} />
      <ErrorBoundaryRoute path={`${match.url}absence`} component={Absence} />
      <ErrorBoundaryRoute path={`${match.url}horaire`} component={Horaire} />
      <ErrorBoundaryRoute path={`${match.url}matiere`} component={Matiere} />
      <ErrorBoundaryRoute path={`${match.url}prof`} component={Prof} />
      <ErrorBoundaryRoute path={`${match.url}admin`} component={Admin} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
