import control from 'app/entities/control/control.reducer';
import producto from 'app/entities/producto/producto.reducer';
import productoControles from 'app/entities/producto-controles/producto-controles.reducer';
import pais from 'app/entities/pais/pais.reducer';
import divisa from 'app/entities/divisa/divisa.reducer';
import idioma from 'app/entities/idioma/idioma.reducer';
import tipoCliente from 'app/entities/tipo-cliente/tipo-cliente.reducer';
import cliente from 'app/entities/cliente/cliente.reducer';
import envio from 'app/entities/envio/envio.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  control,
  producto,
  productoControles,
  pais,
  divisa,
  idioma,
  tipoCliente,
  cliente,
  envio,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
