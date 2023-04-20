import { ITipoCliente } from 'app/shared/model/tipo-cliente.model';
import { IPais } from 'app/shared/model/pais.model';

export interface ICliente {
  idCliente?: string;
  nombre?: string;
  descripcion?: string | null;
  direccion?: string | null;
  tipoCliente?: ITipoCliente | null;
  pais?: IPais | null;
}

export const defaultValue: Readonly<ICliente> = {};
