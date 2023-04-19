export interface IProducto {
  id?: number;
  codigoArancelario?: string | null;
  descripcion?: string | null;
}

export const defaultValue: Readonly<IProducto> = {};
