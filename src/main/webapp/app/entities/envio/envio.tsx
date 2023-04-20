import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IEnvio } from 'app/shared/model/envio.model';
import { getEntities, reset } from './envio.reducer';

export const Envio = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const envioList = useAppSelector(state => state.envio.entities);
  const loading = useAppSelector(state => state.envio.loading);
  const totalItems = useAppSelector(state => state.envio.totalItems);
  const links = useAppSelector(state => state.envio.links);
  const entity = useAppSelector(state => state.envio.entity);
  const updateSuccess = useAppSelector(state => state.envio.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="envio-heading" data-cy="EnvioHeading">
        <Translate contentKey="diccionarioReactApp.envio.home.title">Envios</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="diccionarioReactApp.envio.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/envio/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="diccionarioReactApp.envio.home.createLabel">Create new Envio</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={envioList ? envioList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {envioList && envioList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="diccionarioReactApp.envio.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('cliente')}>
                    <Translate contentKey="diccionarioReactApp.envio.cliente">Cliente</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('remitente')}>
                    <Translate contentKey="diccionarioReactApp.envio.remitente">Remitente</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('destinatario')}>
                    <Translate contentKey="diccionarioReactApp.envio.destinatario">Destinatario</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('proveedor')}>
                    <Translate contentKey="diccionarioReactApp.envio.proveedor">Proveedor</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('refRemitente')}>
                    <Translate contentKey="diccionarioReactApp.envio.refRemitente">Ref Remitente</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('refDestinatario')}>
                    <Translate contentKey="diccionarioReactApp.envio.refDestinatario">Ref Destinatario</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('codigoArancelarioOrigen')}>
                    <Translate contentKey="diccionarioReactApp.envio.codigoArancelarioOrigen">Codigo Arancelario Origen</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('descripcion')}>
                    <Translate contentKey="diccionarioReactApp.envio.descripcion">Descripcion</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('importe')}>
                    <Translate contentKey="diccionarioReactApp.envio.importe">Importe</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('provinciaDestino')}>
                    <Translate contentKey="diccionarioReactApp.envio.provinciaDestino">Provincia Destino</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('uds')}>
                    <Translate contentKey="diccionarioReactApp.envio.uds">Uds</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('peso')}>
                    <Translate contentKey="diccionarioReactApp.envio.peso">Peso</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="diccionarioReactApp.envio.paisOrigen">Pais Origen</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="diccionarioReactApp.envio.paisDestino">Pais Destino</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="diccionarioReactApp.envio.divisa">Divisa</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="diccionarioReactApp.envio.idioma">Idioma</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="diccionarioReactApp.envio.refCliente">Ref Cliente</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {envioList.map((envio, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/envio/${envio.id}`} color="link" size="sm">
                        {envio.id}
                      </Button>
                    </td>
                    <td>{envio.cliente}</td>
                    <td>{envio.remitente}</td>
                    <td>{envio.destinatario}</td>
                    <td>{envio.proveedor}</td>
                    <td>{envio.refRemitente}</td>
                    <td>{envio.refDestinatario}</td>
                    <td>{envio.codigoArancelarioOrigen}</td>
                    <td>{envio.descripcion}</td>
                    <td>{envio.importe}</td>
                    <td>{envio.provinciaDestino}</td>
                    <td>{envio.uds}</td>
                    <td>{envio.peso}</td>
                    <td>
                      {envio.paisOrigen ? <Link to={`/pais/${envio.paisOrigen.codigoPais}`}>{envio.paisOrigen.codigoPais}</Link> : ''}
                    </td>
                    <td>
                      {envio.paisDestino ? <Link to={`/pais/${envio.paisDestino.codigoPais}`}>{envio.paisDestino.codigoPais}</Link> : ''}
                    </td>
                    <td>{envio.divisa ? <Link to={`/divisa/${envio.divisa.codigoDivisa}`}>{envio.divisa.codigoDivisa}</Link> : ''}</td>
                    <td>{envio.idioma ? <Link to={`/idioma/${envio.idioma.codigo}`}>{envio.idioma.codigo}</Link> : ''}</td>
                    <td>
                      {envio.refCliente ? <Link to={`/cliente/${envio.refCliente.idCliente}`}>{envio.refCliente.idCliente}</Link> : ''}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/envio/${envio.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/envio/${envio.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/envio/${envio.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="diccionarioReactApp.envio.home.notFound">No Envios found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Envio;
