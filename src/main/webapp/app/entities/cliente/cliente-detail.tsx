import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './cliente.reducer';

export const ClienteDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const clienteEntity = useAppSelector(state => state.cliente.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clienteDetailsHeading">
          <Translate contentKey="diccionarioReactApp.cliente.detail.title">Cliente</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="idCliente">
              <Translate contentKey="diccionarioReactApp.cliente.idCliente">Id Cliente</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.idCliente}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="diccionarioReactApp.cliente.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.nombre}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="diccionarioReactApp.cliente.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.descripcion}</dd>
          <dt>
            <span id="direccion">
              <Translate contentKey="diccionarioReactApp.cliente.direccion">Direccion</Translate>
            </span>
          </dt>
          <dd>{clienteEntity.direccion}</dd>
          <dt>
            <Translate contentKey="diccionarioReactApp.cliente.tipoCliente">Tipo Cliente</Translate>
          </dt>
          <dd>{clienteEntity.tipoCliente ? clienteEntity.tipoCliente.codigo : ''}</dd>
          <dt>
            <Translate contentKey="diccionarioReactApp.cliente.pais">Pais</Translate>
          </dt>
          <dd>{clienteEntity.pais ? clienteEntity.pais.codigoPais : ''}</dd>
        </dl>
        <Button tag={Link} to="/cliente" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/cliente/${clienteEntity.idCliente}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClienteDetail;
