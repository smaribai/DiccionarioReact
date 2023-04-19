import { IProducto } from 'app/shared/model/producto.model';
import { IControl } from 'app/shared/model/control.model';

export interface IProductoControles {
  id?: number;
  descripcion?: string | null;
  codigoArancelario?: IProducto | null;
  idControl?: IControl | null;
}

export const defaultValue: Readonly<IProductoControles> = {};
