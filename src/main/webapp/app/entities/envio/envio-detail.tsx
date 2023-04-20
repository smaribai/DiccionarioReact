import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './envio.reducer';

export const EnvioDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const envioEntity = useAppSelector(state => state.envio.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="envioDetailsHeading">
          <Translate contentKey="diccionarioReactApp.envio.detail.title">Envio</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{envioEntity.id}</dd>
          <dt>
            <span id="cliente">
              <Translate contentKey="diccionarioReactApp.envio.cliente">Cliente</Translate>
            </span>
          </dt>
          <dd>{envioEntity.cliente}</dd>
          <dt>
            <span id="remitente">
              <Translate contentKey="diccionarioReactApp.envio.remitente">Remitente</Translate>
            </span>
          </dt>
          <dd>{envioEntity.remitente}</dd>
          <dt>
            <span id="destinatario">
              <Translate contentKey="diccionarioReactApp.envio.destinatario">Destinatario</Translate>
            </span>
          </dt>
          <dd>{envioEntity.destinatario}</dd>
          <dt>
            <span id="proveedor">
              <Translate contentKey="diccionarioReactApp.envio.proveedor">Proveedor</Translate>
            </span>
          </dt>
          <dd>{envioEntity.proveedor}</dd>
          <dt>
            <span id="refRemitente">
              <Translate contentKey="diccionarioReactApp.envio.refRemitente">Ref Remitente</Translate>
            </span>
          </dt>
          <dd>{envioEntity.refRemitente}</dd>
          <dt>
            <span id="refDestinatario">
              <Translate contentKey="diccionarioReactApp.envio.refDestinatario">Ref Destinatario</Translate>
            </span>
          </dt>
          <dd>{envioEntity.refDestinatario}</dd>
          <dt>
            <span id="codigoArancelarioOrigen">
              <Translate contentKey="diccionarioReactApp.envio.codigoArancelarioOrigen">Codigo Arancelario Origen</Translate>
            </span>
          </dt>
          <dd>{envioEntity.codigoArancelarioOrigen}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="diccionarioReactApp.envio.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{envioEntity.descripcion}</dd>
          <dt>
            <span id="importe">
              <Translate contentKey="diccionarioReactApp.envio.importe">Importe</Translate>
            </span>
          </dt>
          <dd>{envioEntity.importe}</dd>
          <dt>
            <span id="provinciaDestino">
              <Translate contentKey="diccionarioReactApp.envio.provinciaDestino">Provincia Destino</Translate>
            </span>
          </dt>
          <dd>{envioEntity.provinciaDestino}</dd>
          <dt>
            <span id="uds">
              <Translate contentKey="diccionarioReactApp.envio.uds">Uds</Translate>
            </span>
          </dt>
          <dd>{envioEntity.uds}</dd>
          <dt>
            <span id="peso">
              <Translate contentKey="diccionarioReactApp.envio.peso">Peso</Translate>
            </span>
          </dt>
          <dd>{envioEntity.peso}</dd>
          <dt>
            <Translate contentKey="diccionarioReactApp.envio.paisOrigen">Pais Origen</Translate>
          </dt>
          <dd>{envioEntity.paisOrigen ? envioEntity.paisOrigen.codigoPais : ''}</dd>
          <dt>
            <Translate contentKey="diccionarioReactApp.envio.paisDestino">Pais Destino</Translate>
          </dt>
          <dd>{envioEntity.paisDestino ? envioEntity.paisDestino.codigoPais : ''}</dd>
          <dt>
            <Translate contentKey="diccionarioReactApp.envio.divisa">Divisa</Translate>
          </dt>
          <dd>{envioEntity.divisa ? envioEntity.divisa.codigoDivisa : ''}</dd>
          <dt>
            <Translate contentKey="diccionarioReactApp.envio.idioma">Idioma</Translate>
          </dt>
          <dd>{envioEntity.idioma ? envioEntity.idioma.codigo : ''}</dd>
          <dt>
            <Translate contentKey="diccionarioReactApp.envio.refCliente">Ref Cliente</Translate>
          </dt>
          <dd>{envioEntity.refCliente ? envioEntity.refCliente.idCliente : ''}</dd>
        </dl>
        <Button tag={Link} to="/envio" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/envio/${envioEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EnvioDetail;
