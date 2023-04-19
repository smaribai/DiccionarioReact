import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/control">
        <Translate contentKey="global.menu.entities.control" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/producto">
        <Translate contentKey="global.menu.entities.producto" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/producto-controles">
        <Translate contentKey="global.menu.entities.productoControles" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
