import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPais } from 'app/shared/model/pais.model';
import { getEntities as getPais } from 'app/entities/pais/pais.reducer';
import { IDivisa } from 'app/shared/model/divisa.model';
import { getEntities as getDivisas } from 'app/entities/divisa/divisa.reducer';
import { IIdioma } from 'app/shared/model/idioma.model';
import { getEntities as getIdiomas } from 'app/entities/idioma/idioma.reducer';
import { ICliente } from 'app/shared/model/cliente.model';
import { getEntities as getClientes } from 'app/entities/cliente/cliente.reducer';
import { IEnvio } from 'app/shared/model/envio.model';
//import { getEntity, updateEntity, createEntity, reset } from 'app/entities/envio/envio.reducer';

import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';
import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
const apiUrl = 'api/envios';

/*export const getEntity = createAsyncThunk(
    'envio/fetch_entity',
    async (id: string | number) => {
      const requestUrl = `${apiUrl}/${id}`;
      return axios.get<IEnvio>(requestUrl);
    },
    { serializeError: serializeAxiosError }
  );*/

export const getEntity = createAsyncThunk(
  'envio/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IEnvio>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const Clasificacion = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const pais = useAppSelector(state => state.pais.entities);
  const divisas = useAppSelector(state => state.divisa.entities);
  const idiomas = useAppSelector(state => state.idioma.entities);
  const clientes = useAppSelector(state => state.cliente.entities);
  const envioEntity = useAppSelector(state => state.envio.entity);
  const loading = useAppSelector(state => state.envio.loading);
  const updating = useAppSelector(state => state.envio.updating);
  const updateSuccess = useAppSelector(state => state.envio.updateSuccess);
  const handleClose = () => {
    props.history.push('/');
  };

  useEffect(() => {
    /* if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }*/

    dispatch(getPais({}));
    dispatch(getDivisas({}));
    dispatch(getIdiomas({}));
    dispatch(getClientes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...envioEntity,
      ...values,
      paisOrigen: pais.find(it => it.codigoPais.toString() === values.paisOrigen.toString()),
      paisDestino: pais.find(it => it.codigoPais.toString() === values.paisDestino.toString()),
      divisa: divisas.find(it => it.codigoDivisa.toString() === values.divisa.toString()),
      idioma: idiomas.find(it => it.codigo.toString() === values.idioma.toString()),
      refCliente: clientes.find(it => it.idCliente.toString() === values.refCliente.toString()),
    };

    /*if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }*/
    dispatch(getEntity(entity));
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...envioEntity,
          paisOrigen: envioEntity?.paisOrigen?.codigoPais,
          paisDestino: envioEntity?.paisDestino?.codigoPais,
          divisa: envioEntity?.divisa?.codigoDivisa,
          idioma: envioEntity?.idioma?.codigo,
          refCliente: envioEntity?.refCliente?.idCliente,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="diccionarioReactApp.envio.home.createOrEditLabel" data-cy="EnvioCreateUpdateHeading">
            <Translate contentKey="diccionarioReactApp.envio.home.createOrEditLabel">Create or edit a Envio</Translate>
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
                  name="id"
                  required
                  readOnly
                  id="envio-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                id="envio-refCliente"
                name="refCliente"
                data-cy="refCliente"
                label={translate('diccionarioReactApp.envio.refCliente')}
                type="select"
              >
                <option value="" key="0" />
                {clientes
                  ? clientes.map(otherEntity => (
                      <option value={otherEntity.idCliente} key={otherEntity.idCliente}>
                        {otherEntity.idCliente}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('diccionarioReactApp.envio.cliente')}
                id="envio-cliente"
                name="cliente"
                data-cy="cliente"
                type="text"
              />
              <ValidatedField
                label={translate('diccionarioReactApp.envio.remitente')}
                id="envio-remitente"
                name="remitente"
                data-cy="remitente"
                type="text"
              />
              <ValidatedField
                label={translate('diccionarioReactApp.envio.destinatario')}
                id="envio-destinatario"
                name="destinatario"
                data-cy="destinatario"
                type="text"
              />
              <ValidatedField
                label={translate('diccionarioReactApp.envio.proveedor')}
                id="envio-proveedor"
                name="proveedor"
                data-cy="proveedor"
                type="text"
              />
              <ValidatedField
                label={translate('diccionarioReactApp.envio.refRemitente')}
                id="envio-refRemitente"
                name="refRemitente"
                data-cy="refRemitente"
                type="text"
              />
              <ValidatedField
                label={translate('diccionarioReactApp.envio.refDestinatario')}
                id="envio-refDestinatario"
                name="refDestinatario"
                data-cy="refDestinatario"
                type="text"
              />
              <ValidatedField
                label={translate('diccionarioReactApp.envio.codigoArancelarioOrigen')}
                id="envio-codigoArancelarioOrigen"
                name="codigoArancelarioOrigen"
                data-cy="codigoArancelarioOrigen"
                type="text"
              />
              <ValidatedField
                label={translate('diccionarioReactApp.envio.descripcion')}
                id="envio-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
              />
              <ValidatedField
                label={translate('diccionarioReactApp.envio.importe')}
                id="envio-importe"
                name="importe"
                data-cy="importe"
                type="text"
              />
              <ValidatedField
                label={translate('diccionarioReactApp.envio.provinciaDestino')}
                id="envio-provinciaDestino"
                name="provinciaDestino"
                data-cy="provinciaDestino"
                type="text"
              />
              <ValidatedField label={translate('diccionarioReactApp.envio.uds')} id="envio-uds" name="uds" data-cy="uds" type="text" />
              <ValidatedField label={translate('diccionarioReactApp.envio.peso')} id="envio-peso" name="peso" data-cy="peso" type="text" />
              <ValidatedField
                id="envio-paisOrigen"
                name="paisOrigen"
                data-cy="paisOrigen"
                label={translate('diccionarioReactApp.envio.paisOrigen')}
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
              <ValidatedField
                id="envio-paisDestino"
                name="paisDestino"
                data-cy="paisDestino"
                label={translate('diccionarioReactApp.envio.paisDestino')}
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
              <ValidatedField
                id="envio-divisa"
                name="divisa"
                data-cy="divisa"
                label={translate('diccionarioReactApp.envio.divisa')}
                type="select"
              >
                <option value="" key="0" />
                {divisas
                  ? divisas.map(otherEntity => (
                      <option value={otherEntity.codigoDivisa} key={otherEntity.codigoDivisa}>
                        {otherEntity.codigoDivisa}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="envio-idioma"
                name="idioma"
                data-cy="idioma"
                label={translate('diccionarioReactApp.envio.idioma')}
                type="select"
              >
                <option value="" key="0" />
                {idiomas
                  ? idiomas.map(otherEntity => (
                      <option value={otherEntity.codigo} key={otherEntity.codigo}>
                        {otherEntity.codigo}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/envio" replace color="info">
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

export default Clasificacion;
