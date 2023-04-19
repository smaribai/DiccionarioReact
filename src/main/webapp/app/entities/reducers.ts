import control from 'app/entities/control/control.reducer';
import producto from 'app/entities/producto/producto.reducer';
import productoControles from 'app/entities/producto-controles/producto-controles.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  control,
  producto,
  productoControles,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
