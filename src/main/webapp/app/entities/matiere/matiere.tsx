import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './matiere.reducer';
import { IMatiere } from 'app/shared/model/matiere.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMatiereProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Matiere = (props: IMatiereProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { matiereList, match, loading } = props;
  return (
    <div>
      <h2 id="matiere-heading">
        <Translate contentKey="intranetApp.matiere.home.title">Matieres</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="intranetApp.matiere.home.createLabel">Create new Matiere</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {matiereList && matiereList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.matiere.idMatiere">Id Matiere</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.matiere.nomMatiere">Nom Matiere</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.matiere.abreviation">Abreviation</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.matiere.horaire">Horaire</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.matiere.prof">Prof</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {matiereList.map((matiere, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${matiere.id}`} color="link" size="sm">
                      {matiere.id}
                    </Button>
                  </td>
                  <td>{matiere.idMatiere}</td>
                  <td>{matiere.nomMatiere}</td>
                  <td>{matiere.abreviation}</td>
                  <td>
                    {matiere.horaires
                      ? matiere.horaires.map((val, j) => (
                          <span key={j}>
                            <Link to={`horaire/${val.id}`}>{val.idHoraire}</Link>
                            {j === matiere.horaires.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {matiere.profs
                      ? matiere.profs.map((val, j) => (
                          <span key={j}>
                            <Link to={`prof/${val.id}`}>{val.login}</Link>
                            {j === matiere.profs.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${matiere.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${matiere.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${matiere.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="intranetApp.matiere.home.notFound">No Matieres found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ matiere }: IRootState) => ({
  matiereList: matiere.entities,
  loading: matiere.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Matiere);
