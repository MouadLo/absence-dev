import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './admin.reducer';
import { IAdmin } from 'app/shared/model/admin.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAdminProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Admin = (props: IAdminProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { adminList, match, loading } = props;
  return (
    <div>
      <h2 id="admin-heading">
        <Translate contentKey="intranetApp.admin.home.title">Admins</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="intranetApp.admin.home.createLabel">Create new Admin</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {adminList && adminList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.admin.login">Login</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.admin.mdp">Mdp</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.admin.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.admin.nom">Nom</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.admin.prenom">Prenom</Translate>
                </th>
                <th>
                  <Translate contentKey="intranetApp.admin.tel">Tel</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {adminList.map((admin, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${admin.id}`} color="link" size="sm">
                      {admin.id}
                    </Button>
                  </td>
                  <td>{admin.login}</td>
                  <td>{admin.mdp}</td>
                  <td>{admin.email}</td>
                  <td>{admin.nom}</td>
                  <td>{admin.prenom}</td>
                  <td>{admin.tel}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${admin.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${admin.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${admin.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="intranetApp.admin.home.notFound">No Admins found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ admin }: IRootState) => ({
  adminList: admin.entities,
  loading: admin.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Admin);
