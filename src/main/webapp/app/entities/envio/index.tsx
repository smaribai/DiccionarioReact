import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Envio from './envio';
import EnvioDetail from './envio-detail';
import EnvioUpdate from './envio-update';
import EnvioDeleteDialog from './envio-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EnvioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EnvioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EnvioDetail} />
      <ErrorBoundaryRoute path={match.url} component={Envio} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EnvioDeleteDialog} />
  </>
);

export default Routes;
