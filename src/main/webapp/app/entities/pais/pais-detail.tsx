import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pais.reducer';

export const PaisDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const paisEntity = useAppSelector(state => state.pais.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="paisDetailsHeading">
          <Translate contentKey="diccionarioReactApp.pais.detail.title">Pais</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="codigoPais">
              <Translate contentKey="diccionarioReactApp.pais.codigoPais">Codigo Pais</Translate>
            </span>
          </dt>
          <dd>{paisEntity.codigoPais}</dd>
          <dt>
            <span id="nombrePais">
              <Translate contentKey="diccionarioReactApp.pais.nombrePais">Nombre Pais</Translate>
            </span>
          </dt>
          <dd>{paisEntity.nombrePais}</dd>
        </dl>
        <Button tag={Link} to="/pais" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pais/${paisEntity.codigoPais}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PaisDetail;
