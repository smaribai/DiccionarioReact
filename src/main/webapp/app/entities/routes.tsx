import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Control from './control';
import Producto from './producto';
import ProductoControles from './producto-controles';
import Pais from './pais';
import Divisa from './divisa';
import Idioma from './idioma';
import TipoCliente from './tipo-cliente';
import Cliente from './cliente';
import Envio from './envio';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}control`} component={Control} />
        <ErrorBoundaryRoute path={`${match.url}producto`} component={Producto} />
        <ErrorBoundaryRoute path={`${match.url}producto-controles`} component={ProductoControles} />
        <ErrorBoundaryRoute path={`${match.url}pais`} component={Pais} />
        <ErrorBoundaryRoute path={`${match.url}divisa`} component={Divisa} />
        <ErrorBoundaryRoute path={`${match.url}idioma`} component={Idioma} />
        <ErrorBoundaryRoute path={`${match.url}tipo-cliente`} component={TipoCliente} />
        <ErrorBoundaryRoute path={`${match.url}cliente`} component={Cliente} />
        <ErrorBoundaryRoute path={`${match.url}envio`} component={Envio} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
