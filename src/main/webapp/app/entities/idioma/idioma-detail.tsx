import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './idioma.reducer';

export const IdiomaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const idiomaEntity = useAppSelector(state => state.idioma.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="idiomaDetailsHeading">
          <Translate contentKey="diccionarioReactApp.idioma.detail.title">Idioma</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="codigo">
              <Translate contentKey="diccionarioReactApp.idioma.codigo">Codigo</Translate>
            </span>
          </dt>
          <dd>{idiomaEntity.codigo}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="diccionarioReactApp.idioma.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{idiomaEntity.nombre}</dd>
        </dl>
        <Button tag={Link} to="/idioma" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/idioma/${idiomaEntity.codigo}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default IdiomaDetail;
