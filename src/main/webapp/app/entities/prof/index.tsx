import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Prof from './prof';
import ProfDetail from './prof-detail';
import ProfUpdate from './prof-update';
import ProfDeleteDialog from './prof-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProfUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProfUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProfDetail} />
      <ErrorBoundaryRoute path={match.url} component={Prof} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProfDeleteDialog} />
  </>
);

export default Routes;
