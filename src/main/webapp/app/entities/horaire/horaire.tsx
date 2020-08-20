import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './horaire.reducer';
import { IHoraire } from 'app/shared/model/horaire.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHoraireProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Horaire = (props: IHoraireProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { horaireList, match, loading } = props;
  return (
    <div>
      <h2 id="horaire-heading">
        <Translate contentKey="intranetApp.horaire.home.title">Horaires</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="intranetApp.horaire.home.createLabel">Create new Horaire</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {horaireList && horaireList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.horaire.idHoraire">Id Horaire</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.horaire.heureDepart">Heure Depart</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.horaire.heureF">Heure F</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.horaire.date">Date</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {horaireList.map((horaire, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${horaire.id}`} color="link" size="sm">
                      {horaire.id}
                    </Button>
                  </td>
                  <td>{horaire.idHoraire}</td>
                  <td>{horaire.heureDepart}</td>
                  <td>{horaire.heureF}</td>
                  <td>{horaire.date ? <TextFormat type="date" value={horaire.date} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${horaire.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${horaire.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${horaire.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="intranetApp.horaire.home.notFound">No Horaires found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ horaire }: IRootState) => ({
  horaireList: horaire.entities,
  loading: horaire.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Horaire);
