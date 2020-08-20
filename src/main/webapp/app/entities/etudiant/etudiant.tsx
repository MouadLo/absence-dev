import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './etudiant.reducer';
import { IEtudiant } from 'app/shared/model/etudiant.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEtudiantProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Etudiant = (props: IEtudiantProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { etudiantList, match, loading } = props;
  return (
    <div>
      <h2 id="etudiant-heading">
        <Translate contentKey="intranetApp.etudiant.home.title">Etudiants</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="intranetApp.etudiant.home.createLabel">Create new Etudiant</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {etudiantList && etudiantList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.etudiant.login">Login</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.etudiant.mdp">Mdp</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.etudiant.cne">Cne</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.etudiant.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.etudiant.nom">Nom</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.etudiant.prenom">Prenom</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.etudiant.tel">Tel</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.etudiant.classe">Classe</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {etudiantList.map((etudiant, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${etudiant.id}`} color="link" size="sm">
                      {etudiant.id}
                    </Button>
                  </td>
                  <td>{etudiant.login}</td>
                  <td>{etudiant.mdp}</td>
                  <td>{etudiant.cne}</td>
                  <td>{etudiant.email}</td>
                  <td>{etudiant.nom}</td>
                  <td>{etudiant.prenom}</td>
                  <td>{etudiant.tel}</td>
                  <td>{etudiant.classe ? <Link to={`classe/${etudiant.classe.id}`}>{etudiant.classe.idClasse}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${etudiant.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${etudiant.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${etudiant.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="intranetApp.etudiant.home.notFound">No Etudiants found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ etudiant }: IRootState) => ({
  etudiantList: etudiant.entities,
  loading: etudiant.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Etudiant);
