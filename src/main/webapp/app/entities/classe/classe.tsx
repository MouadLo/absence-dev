import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './classe.reducer';
import { IClasse } from 'app/shared/model/classe.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IClasseProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Classe = (props: IClasseProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { classeList, match, loading } = props;
  return (
    <div>
      <h2 id="classe-heading">
        <Translate contentKey="intranetApp.classe.home.title">Classes</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="intranetApp.classe.home.createLabel">Create new Classe</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {classeList && classeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.classe.idClasse">Id Classe</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.classe.nomClasse">Nom Classe</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.classe.niveau">Niveau</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.classe.filiere">Filiere</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {classeList.map((classe, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${classe.id}`} color="link" size="sm">
                      {classe.id}
                    </Button>
                  </td>
                  <td>{classe.idClasse}</td>
                  <td>{classe.nomClasse}</td>
                  <td>{classe.niveau}</td>
                  <td>{classe.filiere ? <Link to={`filiere/${classe.filiere.id}`}>{classe.filiere.idFiliere}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${classe.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${classe.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${classe.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="intranetApp.classe.home.notFound">No Classes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ classe }: IRootState) => ({
  classeList: classe.entities,
  loading: classe.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Classe);
