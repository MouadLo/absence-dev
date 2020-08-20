import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './prof.reducer';
import { IProf } from 'app/shared/model/prof.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProfProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Prof = (props: IProfProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { profList, match, loading } = props;
  return (
    <div>
      <h2 id="prof-heading">
        <Translate contentKey="intranetApp.prof.home.title">Profs</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="intranetApp.prof.home.createLabel">Create new Prof</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {profList && profList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.prof.login">Login</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.prof.mdp">Mdp</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.prof.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.prof.nom">Nom</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.prof.prenom">Prenom</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.prof.type">Type</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.prof.tel">Tel</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {profList.map((prof, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${prof.id}`} color="link" size="sm">
                      {prof.id}
                    </Button>
                  </td>
                  <td>{prof.login}</td>
                  <td>{prof.mdp}</td>
                  <td>{prof.email}</td>
                  <td>{prof.nom}</td>
                  <td>{prof.prenom}</td>
                  <td>{prof.type}</td>
                  <td>{prof.tel}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${prof.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${prof.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${prof.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="intranetApp.prof.home.notFound">No Profs found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ prof }: IRootState) => ({
  profList: prof.entities,
  loading: prof.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Prof);
