import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Classe from './classe';
import ClasseDetail from './classe-detail';
import ClasseUpdate from './classe-update';
import ClasseDeleteDialog from './classe-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ClasseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ClasseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ClasseDetail} />
      <ErrorBoundaryRoute path={match.url} component={Classe} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ClasseDeleteDialog} />
  </>
);

export default Routes;
