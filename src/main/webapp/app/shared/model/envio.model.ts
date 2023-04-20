import { IPais } from 'app/shared/model/pais.model';
import { IDivisa } from 'app/shared/model/divisa.model';
import { IIdioma } from 'app/shared/model/idioma.model';
import { ICliente } from 'app/shared/model/cliente.model';

export interface IEnvio {
  id?: number;
  cliente?: string | null;
  remitente?: string | null;
  destinatario?: string | null;
  proveedor?: string | null;
  refRemitente?: string | null;
  refDestinatario?: string | null;
  codigoArancelarioOrigen?: string | null;
  descripcion?: string | null;
  importe?: number | null;
  provinciaDestino?: string | null;
  uds?: number | null;
  peso?: number | null;
  paisOrigen?: IPais | null;
  paisDestino?: IPais | null;
  divisa?: IDivisa | null;
  idioma?: IIdioma | null;
  refCliente?: ICliente | null;
}

export const defaultValue: Readonly<IEnvio> = {};
