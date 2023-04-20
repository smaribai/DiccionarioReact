import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITipoCliente } from 'app/shared/model/tipo-cliente.model';
import { getEntities as getTipoClientes } from 'app/entities/tipo-cliente/tipo-cliente.reducer';
import { IPais } from 'app/shared/model/pais.model';
import { getEntities as getPais } from 'app/entities/pais/pais.reducer';
import { ICliente } from 'app/shared/model/cliente.model';
import { getEntity, updateEntity, createEntity, reset } from './cliente.reducer';

export const ClienteUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const tipoClientes = useAppSelector(state => state.tipoCliente.entities);
  const pais = useAppSelector(state => state.pais.entities);
  const clienteEntity = useAppSelector(state => state.cliente.entity);
  const loading = useAppSelector(state => state.cliente.loading);
  const updating = useAppSelector(state => state.cliente.updating);
  const updateSuccess = useAppSelector(state => state.cliente.updateSuccess);
  const handleClose = () => {
    props.history.push('/cliente' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getTipoClientes({}));
    dispatch(getPais({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...clienteEntity,
      ...values,
      tipoCliente: tipoClientes.find(it => it.codigo.toString() === values.tipoCliente.toString()),
      pais: pais.find(it => it.codigoPais.toString() === values.pais.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...clienteEntity,
          tipoCliente: clienteEntity?.tipoCliente?.codigo,
          pais: clienteEntity?.pais?.codigoPais,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="diccionarioReactApp.cliente.home.createOrEditLabel" data-cy="ClienteCreateUpdateHeading">
            <Translate contentKey="diccionarioReactApp.cliente.home.createOrEditLabel">Create or edit a Cliente</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="idCliente"
                  required
                  readOnly
                  id="cliente-idCliente"
                  label={translate('diccionarioReactApp.cliente.idCliente')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('diccionarioReactApp.cliente.nombre')}
                id="cliente-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  maxLength: { value: 70, message: translate('entity.validation.maxlength', { max: 70 }) },
                }}
              />
              <ValidatedField
                label={translate('diccionarioReactApp.cliente.descripcion')}
                id="cliente-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
                validate={{
                  maxLength: { value: 200, message: translate('entity.validation.maxlength', { max: 200 }) },
                }}
              />
              <ValidatedField
                label={translate('diccionarioReactApp.cliente.direccion')}
                id="cliente-direccion"
                name="direccion"
                data-cy="direccion"
                type="text"
                validate={{
                  maxLength: { value: 200, message: translate('entity.validation.maxlength', { max: 200 }) },
                }}
              />
              <ValidatedField
                id="cliente-tipoCliente"
                name="tipoCliente"
                data-cy="tipoCliente"
                label={translate('diccionarioReactApp.cliente.tipoCliente')}
                type="select"
              >
                <option value="" key="0" />
                {tipoClientes
                  ? tipoClientes.map(otherEntity => (
                      <option value={otherEntity.codigo} key={otherEntity.codigo}>
                        {otherEntity.codigo}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="cliente-pais"
                name="pais"
                data-cy="pais"
                label={translate('diccionarioReactApp.cliente.pais')}
                type="select"
              >
                <option value="" key="0" />
                {pais
                  ? pais.map(otherEntity => (
                      <option value={otherEntity.codigoPais} key={otherEntity.codigoPais}>
                        {otherEntity.codigoPais}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/cliente" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ClienteUpdate;
