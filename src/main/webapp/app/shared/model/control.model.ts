export interface IControl {
  id?: number;
  idControl?: string | null;
  nombre?: string | null;
  descripcion?: string | null;
}

export const defaultValue: Readonly<IControl> = {};
