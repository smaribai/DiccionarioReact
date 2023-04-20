import React from 'react';
import { translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';
import ClasificacionMenuItems from 'app/modules/clasificacion/menu';

export const ClasificacionMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.clasificacion.main')}
    id="clasificacion-menu"
    data-cy="clasificacion"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <ClasificacionMenuItems />
  </NavDropdown>
);
