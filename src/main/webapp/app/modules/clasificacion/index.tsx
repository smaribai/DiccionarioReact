import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Clasificacion from './clasificacion';
/*import EnvioDetail from './envio-detail';
import EnvioUpdate from './envio-update';
import EnvioDeleteDialog from './envio-delete-dialog';*/

const Routes = ({ match }) => (
  <>
    <ErrorBoundaryRoute path={match.url} component={Clasificacion} />
  </>
);

export default Routes;
